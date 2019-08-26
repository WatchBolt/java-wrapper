package watch.bolt.api.core.stream;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import watch.bolt.api.BoltAPI;
import watch.bolt.api.core.BoltCallback;
import watch.bolt.api.core.BoltEndpoint;
import watch.bolt.api.core.channel.Channel;
import watch.bolt.api.core.game.Game;
import watch.bolt.api.core.user.User;

public class StreamsEndpoint extends BoltEndpoint {

	public StreamsEndpoint(BoltAPI api) {
		super(api);
	}

	public boolean getStream(Channel channel, BoltCallback<Stream> out) {
		return getStream(channel.getID(), out);
	}

	public boolean getStream(User user, BoltCallback<Stream> out) {
		return getStream(user.getChannelID(), out);
	}

	public boolean getStream(long id, BoltCallback<Stream> out) {
		if (out == null)
			return false;
		HttpGet get = new HttpGet(getPath() + "/stream/" + id);
		return execute(get, out);
	}

	public boolean getSpotlight(BoltCallback<StreamList> out) {
		if (out == null)
			return false;
		HttpGet get = new HttpGet(getPath() + "/spotlight");
		return executeList(get, out);
	}

	private boolean execute(HttpGet get, BoltCallback<Stream> out) {
		get.addHeader("User-Agent", "Mozilla/5.0");
		try {
			HttpResponse response = client.execute(get);
			JSONObject o = (JSONObject) parser.parse(new BufferedReader(
					new InputStreamReader(response.getEntity().getContent())));
			if (o.containsKey("status"))
				return false;
			Stream s = parse(o);
			out.callback(s);
			return !out.failed();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private boolean executeList(HttpGet get, BoltCallback<StreamList> out) {
		get.addHeader("User-Agent", "Mozilla/5.0");
		try {
			HttpResponse response = client.execute(get);
			JSONArray a = (JSONArray) parser.parse(new BufferedReader(
					new InputStreamReader(response.getEntity().getContent())));
			StreamList sl = parse(a);
			out.callback(sl);
			return !out.failed();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private StreamList parse(JSONArray a) {
		StreamList sl = new StreamList();
		for (Object o : a) {
			if (o instanceof JSONObject)
				sl.add(parse((JSONObject) o));
		}
		return sl;
	}

	private Stream parse(JSONObject o) {
		Stream s = new Stream();
		s.id = (long) o.get("id");
		s.userId = (long) o.get("userId");
		s.viewers = (int) o.get("viewers");
		s.username = String.valueOf(o.get("username"));
		s.streamName = String.valueOf(o.get("streamName"));
		s.online = (boolean) o.get("online");
		s.game = new Game((int) o.get("game"),
				String.valueOf(o.get("gameName")));
		return s;
	}

	@Override
	public Type getType() {
		return Type.STREAMS;
	}

}