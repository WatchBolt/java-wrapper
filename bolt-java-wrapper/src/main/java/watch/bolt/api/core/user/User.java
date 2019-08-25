package watch.bolt.api.core.user;

import watch.bolt.api.core.BoltObject;

public class User implements BoltObject {

	protected int id, channelId, socialId;
	protected String username, displayName;

	protected User() {
	}

	public int getChannelID() {
		return channelId;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getUsername() {
		return username;
	}

	public int getID() {
		return id;
	}

	public int getSocialID() {
		return socialId;
	}

}