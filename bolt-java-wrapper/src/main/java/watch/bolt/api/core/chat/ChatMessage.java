package watch.bolt.api.core.chat;

import org.json.JSONObject;

public class ChatMessage {

	protected long channelId, senderId;
	protected String id, htmlFormattedMessage, rawMessage;
	protected JSONObject userMeta, messageMeta;

	protected ChatMessage() {
	}

	public String getID() {
		return id;
	}

	public long getChannelID() {
		return channelId;
	}

	public String getHTMLFormattedMessage() {
		return htmlFormattedMessage;
	}

	public String getRawMessage() {
		return rawMessage;
	}

	public JSONObject getMessageMeta() {
		return messageMeta;
	}

	public JSONObject getUserMeta() {
		return userMeta;
	}

	public long getSenderID() {
		return senderId;
	}

}