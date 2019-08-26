package watch.bolt.api.core.user;

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
import watch.bolt.api.core.stream.Stream;

public class UsersEndpoint extends BoltEndpoint {

	public UsersEndpoint(BoltAPI api) {
		super(api);
	}

	public boolean getUser(long id, BoltCallback<User> out) {
		if (out == null)
			return false;
		HttpGet get = new HttpGet(getPath() + "/" + id);
		return execute(get, out);
	}

	public boolean search(String query, BoltCallback<UserList> out) {
		if (out == null || query == null || query.isEmpty())
			return false;
		HttpGet get = new HttpGet(getPath() + "/list/" + query);
		return executeList(get, out);
	}

	public boolean getUser(Stream stream, BoltCallback<User> out) {
		return getUser(stream.getUserID(), out);
	}

	public boolean getUser(Channel channel, BoltCallback<User> out) {
		return getUser(channel.getUserID(), out);
	}

	public boolean getUser(String name, BoltCallback<User> out) {
		if (out == null || name == null || name.isEmpty())
			return false;
		HttpGet get = new HttpGet(getPath() + "/name/" + name);
		return execute(get, out);
	}

	private boolean execute(HttpGet get, BoltCallback<User> out) {
		get.addHeader("User-Agent", "Mozilla/5.0");
		try {
			HttpResponse response = client.execute(get);
			JSONObject o = (JSONObject) parser.parse(new BufferedReader(
					new InputStreamReader(response.getEntity().getContent())));
			if (o.containsKey("status"))
				return false;
			User u = parse(o);
			out.callback(u);
			return !out.failed();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private boolean executeList(HttpGet get, BoltCallback<UserList> out) {
		get.addHeader("User-Agent", "Mozilla/5.0");
		try {
			HttpResponse response = client.execute(get);
			JSONArray a = (JSONArray) parser.parse(new BufferedReader(
					new InputStreamReader(response.getEntity().getContent())));
			UserList ul = parse(a);
			out.callback(ul);
			return !out.failed();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private UserList parse(JSONArray a) {
		UserList ul = new UserList();
		for (Object o : a) {
			if (o instanceof JSONObject)
				ul.add(parse((JSONObject) o));
		}
		return ul;
	}

	private User parse(JSONObject o) {
		User u = new User();
		u.id = (long) o.get("id");
		u.channelId = (long) o.get("channelId");
		u.socialId = (long) o.get("socialId");
		u.username = String.valueOf(o.get("username"));
		u.displayName = String.valueOf(o.get("displayName"));
		return u;
	}

	@Override
	public Type getType() {
		return Type.USERS;
	}

}