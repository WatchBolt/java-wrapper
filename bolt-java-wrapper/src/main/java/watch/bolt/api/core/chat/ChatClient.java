package watch.bolt.api.core.chat;

import java.net.URISyntaxException;
import java.util.Arrays;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter.Listener;
import watch.bolt.api.BoltAPI;
import watch.bolt.api.core.channel.Channel;
import watch.bolt.api.core.channel.ChannelList;
import watch.bolt.api.core.events.ChatMessageEvent;

public class ChatClient {

	private final String WS_URL = "wss://chats-atlanta1.bolt.watch";
	private Socket socket;
	private ChannelList channels, authed;

	public ChatClient() {
		this.channels = new ChannelList();
		this.authed = new ChannelList();
	}

	public void addChannels(Channel... channels) {
		this.channels.addAll(Arrays.asList(channels));
		if (socket.connected())
			auth();
	}

	public void init() {
		try {
			this.socket = IO.socket(WS_URL);
			this.socket.on(Socket.EVENT_CONNECT, new Listener() {

				@Override
				public void call(Object... args) {
					auth();
				}

			}).on("message", new Listener() {

				@Override
				public void call(Object... args) {
					try {
						JSONObject obj = (JSONObject) args[0];
						ChatMessage cm = new ChatMessage();
						cm.id = obj.getString("id");
						cm.channelId = obj.getInt("channel");
						cm.senderId = obj.getInt("sender");
						cm.htmlFormattedMessage = obj.getString("message");
						cm.rawMessage = obj.getString("rawMessage");
						cm.userMeta = obj.getJSONObject("userMeta");
						cm.messageMeta = obj.getJSONObject("meta");
						ChatMessageEvent event = new ChatMessageEvent(cm);
						BoltAPI.getInstance().callEvent(event);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}

			});
			this.socket.connect();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		if (this.socket != null && this.socket.connected()) {
			this.socket.disconnect();
			this.socket.close();
		}
	}

	private void auth() {
		for (Channel ch : channels) {
			if (authed.contains(ch))
				continue;
			try {
				socket.emit("auth",
						new JSONObject().put("key", "CONN_ANONYMOUS")
								.put("channel", ch.getID()));
				authed.add(ch);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

}