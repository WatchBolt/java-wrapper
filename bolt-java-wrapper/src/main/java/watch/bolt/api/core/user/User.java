package watch.bolt.api.core.user;

import watch.bolt.api.core.BoltObject;

public class User implements BoltObject {

	protected long id, channelId, socialId;
	protected String username, displayName;

	protected User() {
	}

	public long getChannelID() {
		return channelId;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getUsername() {
		return username;
	}

	public long getID() {
		return id;
	}

	public long getSocialID() {
		return socialId;
	}

}