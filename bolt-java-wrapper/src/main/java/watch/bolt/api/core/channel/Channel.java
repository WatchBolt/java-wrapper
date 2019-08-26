package watch.bolt.api.core.channel;

import watch.bolt.api.core.BoltObject;
import watch.bolt.api.core.game.Game;

public class Channel implements BoltObject {

	protected long id, userId, followers;
	protected String username, streamName;
	protected Game game;
	protected boolean verified, partnered;

	public long getID() {
		return id;
	}

	public Game getGame() {
		return game;
	}

	public long getUserID() {
		return userId;
	}

	public long getFollowers() {
		return followers;
	}

	public String getUsername() {
		return username;
	}

	public String getStreamName() {
		return streamName;
	}

	public boolean isVerified() {
		return verified;
	}

	public boolean isPartnered() {
		return partnered;
	}

}