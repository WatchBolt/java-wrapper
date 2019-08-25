# Bolt Java API Wrapper

Java wrapper for the Bolt.watch REST API.

## Endpoints

To access most of the API, you are going to want to know about these.

**There are currently four endpoint types:**
- USERS (UsersEndpoint)
- CHANNELS (ChannelsEndpoint)
- GAMES (GamesEndpoint)
- STREAMS (StreamsEndpoint)
#### Accessing an endpoint:
```java
BoltAPI instance = BoltAPI.getInstance();
UsersEndpoint usersEndpoint = (UsersEndpoint) instance.getEndpoint(Type.USERS);
```

#### Fetching a resource:
```java
BoltCallback<User> callback = new BoltCallback<User>() {
	
    @Override
    public void callback(User user) {
    	// Handle data here.
    }
    
    @Override
    public void onFail(String msg) {
    	// Handle error here.
    }
    
};
usersEndpoint.search("Bolt", callback);
```

## Chat Client

#### *(You must register a listener to receive chat event information.)*
```java
public void initChat(Channel channel) {
  // Define the chat client.
  ChatClient client = new ChatClient();
  // Add a channel.
  client.addChannels(channel);
  // Initialize the chat client.
  client.init();
}
```

## Listeners

#### Creating the listener:
```java
/**
* Make sure your listener class extends BoltEventListener.
**/

public class MyListener extends BoltEventListener {

	@Override
	public void onChatMessage(ChatMessageEvent event) {
    	// Handle event.
    }
    
}
```

#### Registering the listener:
```java
BoltAPI.getInstance().registerListener(new MyListener());
```
