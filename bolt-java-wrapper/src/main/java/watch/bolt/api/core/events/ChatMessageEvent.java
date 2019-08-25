package watch.bolt.api.core.events;

import watch.bolt.api.core.chat.ChatMessage;

public class ChatMessageEvent implements BoltEvent {

	private ChatMessage message;

	public ChatMessageEvent(ChatMessage message) {
		this.message = message;
	}

	public ChatMessage getMessage() {
		return message;
	}

}