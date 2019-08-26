package watch.bolt.api;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.parser.JSONParser;

import watch.bolt.api.core.BoltEndpoint;
import watch.bolt.api.core.BoltEndpoint.Type;
import watch.bolt.api.core.BoltEventListener;
import watch.bolt.api.core.channel.ChannelsEndpoint;
import watch.bolt.api.core.events.BoltEvent;
import watch.bolt.api.core.game.GamesEndpoint;
import watch.bolt.api.core.stream.StreamsEndpoint;
import watch.bolt.api.core.user.UsersEndpoint;

public class BoltAPI {

	private static BoltAPI instance;
	private JSONParser parser;
	private HttpClient client;
	private Set<BoltEventListener> listeners;
	private Set<BoltEndpoint> endpoints;

	private BoltAPI() {
		this.client = HttpClientBuilder.create().build();
		this.parser = new JSONParser();
		this.listeners = new HashSet<BoltEventListener>();
		this.endpoints = new HashSet<BoltEndpoint>(
				Arrays.asList(new UsersEndpoint(this), new GamesEndpoint(this),
						new StreamsEndpoint(this), new ChannelsEndpoint(this)));
	}

	public void callEvent(BoltEvent event) {
		for (BoltEventListener listener : this.listeners) {
			listener.callEvent(event);
		}
	}

	public BoltEndpoint getEndpoint(Type type) {
		for (BoltEndpoint endpoint : this.endpoints)
			if (endpoint.getType() == type)
				return endpoint;
		return null;
	}

	public void registerListener(BoltEventListener listener) {
		this.listeners.add(listener);
	}

	public void unregisterListener(BoltEventListener listener) {
		if (this.listeners.contains(listener))
			this.listeners.remove(listener);
	}

	public JSONParser getParser() {
		return parser;
	}

	public HttpClient getClient() {
		return client;
	}

	public static BoltAPI getInstance() {
		if (instance == null) {
			instance = new BoltAPI();
		}
		return instance;
	}

}