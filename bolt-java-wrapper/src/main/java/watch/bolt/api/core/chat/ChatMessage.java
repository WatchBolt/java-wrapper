package watch.bolt.api.core.chat;

import org.json.JSONObject;

public class ChatMessage {

	protected int id, channelId, senderId;
	protected String htmlFormattedMessage, rawMessage;
	protected JSONObject userMeta, messageMeta;

	protected ChatMessage() {
	}

	public int getID() {
		return id;
	}

	public int getChannelID() {
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

	public int getSenderID() {
		return senderId;
	}

}