package watch.bolt.api.core.channel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import watch.bolt.api.BoltAPI;
import watch.bolt.api.core.BoltCallback;
import watch.bolt.api.core.BoltEndpoint;
import watch.bolt.api.core.BoltValue;
import watch.bolt.api.core.game.Game;
import watch.bolt.api.core.stream.Stream;
import watch.bolt.api.core.user.User;

public class ChannelsEndpoint extends BoltEndpoint {

	public ChannelsEndpoint(BoltAPI api) {
		super(api);
	}
	
	public boolean getChannel(long id, BoltCallback<Channel> out) {
		if (out == null)
			return false;
		HttpGet get = new HttpGet(getPath() + "/" + id);
		return execute(get, out);
	}

	public boolean getChannelByUserID(int id, BoltCallback<Channel> out) {
		if (out == null)
			return false;
		HttpGet get = new HttpGet(getPath() + "/id/" + id);
		return execute(get, out);
	}

	public boolean getChannel(Stream stream, BoltCallback<Channel> out) {
		return getChannel(stream.getID(), out);
	}

	public boolean getChannel(User user, BoltCallback<Channel> out) {
		return getChannel(user.getChannelID(), out);
	}

	public boolean getChannel(String name, BoltCallback<Channel> out) {
		if (out == null || name == null || name.isEmpty())
			return false;
		HttpGet get = new HttpGet(getPath() + "/name/" + name);
		return execute(get, out);
	}

	public boolean isFollowing(User follower, Channel target,
			BoltCallback<BoltValue<Boolean>> out) {
		if (follower == null || target == null || out == null)
			return false;
		HttpGet get = new HttpGet(getPath() + "/following/" + follower.getID()
				+ "/" + target.getID());
		get.addHeader("User-Agent", "Mozilla/5.0");
		try {
			HttpResponse response = client.execute(get);
			JSONObject o = (JSONObject) parser.parse(new BufferedReader(
					new InputStreamReader(response.getEntity().getContent())));
			if (o.containsKey("status"))
				return false;
			out.callback(new BoltValue<Boolean>((Boolean) o.get("following")));
			return !out.failed();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean getChannelsByGame(Game g, BoltCallback<ChannelList> out) {
		if (out == null)
			return false;
		HttpGet get = new HttpGet(getPath() + "/games/" + g.getID());
		return executeList(get, out);
	}

	private boolean executeList(HttpGet get, BoltCallback<ChannelList> out) {
		get.addHeader("User-Agent", "Mozilla/5.0");
		try {
			HttpResponse response = client.execute(get);
			JSONArray a = (JSONArray) parser.parse(new BufferedReader(
					new InputStreamReader(response.getEntity().getContent())));
			ChannelList c = parse(a);
			out.callback(c);
			return !out.failed();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private boolean execute(HttpGet get, BoltCallback<Channel> out) {
		get.addHeader("User-Agent", "Mozilla/5.0");
		try {
			HttpResponse response = client.execute(get);
			JSONObject o = (JSONObject) parser.parse(new BufferedReader(
					new InputStreamReader(response.getEntity().getContent())));
			if (o.containsKey("status"))
				return false;
			Channel c = parse(o);
			out.callback(c);
			return !out.failed();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private ChannelList parse(JSONArray a) {
		ChannelList cl = new ChannelList();
		for (Object o : a) {
			if (o instanceof JSONObject)
				cl.add(parse((JSONObject) o));
		}
		return cl;
	}

	private Channel parse(JSONObject o) {
		Channel channel = new Channel();
		channel.id = (long) o.get("id");
		channel.userId = (long) o.get("userId");
		channel.followers = (long) o.get("followers");
		channel.verified = (boolean) o.get("verified");
		channel.partnered = (boolean) o.get("partnered");
		channel.streamName = String.valueOf(o.get("streamName"));
		channel.username = String.valueOf(o.get("username"));
		channel.game = new Game((long) o.get("game"),
				String.valueOf(o.get("gameName")));
		return channel;
	}

	@Override
	public Type getType() {
		return Type.CHANNELS;
	}

}
