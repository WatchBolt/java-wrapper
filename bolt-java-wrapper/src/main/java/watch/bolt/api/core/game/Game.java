package watch.bolt.api.core.game;

import watch.bolt.api.core.BoltObject;

public class Game implements BoltObject {

	protected long id;
	protected String name;

	public Game(long l, String name) {
		this.id = l;
		this.name = name;
	}

	public long getID() {
		return id;
	}

	public String getName() {
		return name;
	}

}