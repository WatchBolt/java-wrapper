package watch.bolt.api.core;

import watch.bolt.api.core.events.BoltEvent;
import watch.bolt.api.core.events.ChatMessageEvent;

public class BoltEventListener {

	protected BoltEventListener() {}
	
	public void onChatMessage(ChatMessageEvent event) {
	}

	public final void callEvent(BoltEvent event) {
		if (event instanceof ChatMessageEvent)
			onChatMessage((ChatMessageEvent) event);
	}

}