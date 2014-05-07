package us.minak;

public class MetaExpression {
	public static enum State {
		ON, OFF, LOCK
	}

	public State state;
	private String value;

	public String getValue() {
		//however we want to do return this
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	MetaExpression(String value) {
		this.value = value;
		this.state = State.OFF;
	}
}
