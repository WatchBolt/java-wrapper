package watch.bolt.api.core.game;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import watch.bolt.api.BoltAPI;
import watch.bolt.api.core.BoltCallback;
import watch.bolt.api.core.BoltEndpoint;

public class GamesEndpoint extends BoltEndpoint {

	public GamesEndpoint(BoltAPI api) {
		super(api);
	}

	public boolean searchGame(String query, BoltCallback<GameList> out) {
		if (out == null || query == null || query.isEmpty())
			return false;
		HttpGet get = new HttpGet(getPath() + "/search/" + query);
		return executeList(get, out);
	}

	public boolean getGame(long id, BoltCallback<Game> out) {
		if (out == null)
			return false;
		HttpGet get = new HttpGet(getPath() + "/" + id);
		return execute(get, out);
	}

	private boolean executeList(HttpGet get, BoltCallback<GameList> out) {
		get.addHeader("User-Agent", "Mozilla/5.0");
		try {
			HttpResponse response = client.execute(get);
			JSONArray a = (JSONArray) parser.parse(new BufferedReader(
					new InputStreamReader(response.getEntity().getContent())));
			GameList gl = parse(a);
			out.callback(gl);
			return !out.failed();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private boolean execute(HttpGet get, BoltCallback<Game> out) {
		get.addHeader("User-Agent", "Mozilla/5.0");
		try {
			HttpResponse response = client.execute(get);
			JSONObject o = (JSONObject) parser.parse(new BufferedReader(
					new InputStreamReader(response.getEntity().getContent())));
			if (o.containsKey("status"))
				return false;
			Game g = parse(o);
			out.callback(g);
			return !out.failed();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private GameList parse(JSONArray a) {
		GameList gl = new GameList();
		for (Object o : a) {
			if (o instanceof JSONObject)
				gl.add(parse((JSONObject) o));
		}
		return gl;
	}

	private Game parse(JSONObject o) {
		return new Game((int) o.get("id"), String.valueOf(o.get("name")));
	}

	@Override
	public Type getType() {
		return Type.GAMES;
	}

}
