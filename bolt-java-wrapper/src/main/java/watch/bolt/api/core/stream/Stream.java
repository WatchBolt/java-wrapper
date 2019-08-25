package watch.bolt.api.core.stream;

import watch.bolt.api.core.BoltObject;
import watch.bolt.api.core.game.Game;

public class Stream implements BoltObject {

	protected int id, userId, viewers;
	protected boolean online;
	protected String username, streamName;
	protected Game game;

	protected Stream() {
	}

	public Game getGame() {
		return game;
	}

	public int getID() {
		return id;
	}

	public String getStreamName() {
		return streamName;
	}

	public int getUserID() {
		return userId;
	}

	public boolean isOnline() {
		return online;
	}

	public String getUsername() {
		return username;
	}

	public int getViewers() {
		return viewers;
	}

}