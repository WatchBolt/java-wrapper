package watch.bolt.api.core;

import org.apache.http.client.HttpClient;
import org.json.simple.parser.JSONParser;

import watch.bolt.api.BoltAPI;

public abstract class BoltEndpoint {

	public static enum Type {

		CHANNELS("channels"), GAMES("games"), STREAMS("streams"), USERS(
				"users");

		private String path;

		private Type(String path) {
			this.path = path;
		}

		public String getPath() {
			return path;
		}

	}

	protected HttpClient client;
	protected JSONParser parser;
	private static final String k = "hampao",
			URL = "https://api.bolt.watch/api/" + k + "/";

	protected BoltEndpoint(BoltAPI api) {
		this.client = api.getClient();
		this.parser = api.getParser();
	}

	public abstract Type getType();

	public final String getPath() {
		return URL + getType().getPath();
	}

}