package watch.bolt.api.core.stream;

import watch.bolt.api.core.BoltObject;
import watch.bolt.api.core.game.Game;

public class Stream implements BoltObject {

	protected long id, userId;
	protected int viewers;
	protected boolean online;
	protected String username, streamName;
	protected Game game;

	protected Stream() {
	}

	public Game getGame() {
		return game;
	}

	public long getID() {
		return id;
	}

	public String getStreamName() {
		return streamName;
	}

	public long getUserID() {
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