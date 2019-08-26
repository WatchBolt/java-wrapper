package watch.bolt.api.core.game;

import watch.bolt.api.core.BoltObject;

public class Game implements BoltObject {

	protected long id;
	protected String name;

	public Game(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public long getID() {
		return id;
	}

	public String getName() {
		return name;
	}

}