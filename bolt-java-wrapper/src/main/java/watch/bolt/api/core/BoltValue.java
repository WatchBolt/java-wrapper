package watch.bolt.api.core;

public class BoltValue<T> implements BoltObject {

	private T value;

	public BoltValue(T value) {
		this.value = value;
	}

	public T getValue() {
		return value;
	}

}