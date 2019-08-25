package watch.bolt.api.core.channel;

import watch.bolt.api.core.BoltObject;
import watch.bolt.api.core.game.Game;

public class Channel implements BoltObject {

	protected int id, userId, followers;
	protected String username, streamName;
	protected Game game;
	protected boolean verified, partnered;

	public int getID() {
		return id;
	}

	public Game getGame() {
		return game;
	}

	public int getUserID() {
		return userId;
	}

	public int getFollowers() {
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