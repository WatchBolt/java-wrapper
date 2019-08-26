package watch.bolt.api.core;

public abstract class BoltCallback<T extends BoltObject> {

	private boolean failed = false;

	public abstract void callback(T x);

	public abstract void onFail(String msg);

	public final void fail(String msg) {
		this.failed = true;
		onFail(msg);
	}

	public final void fail() {
		fail("An error has occurred.");
	}

	public final boolean failed() {
		return failed;
	}

}