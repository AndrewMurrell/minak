package us.minak;

public class MetaExpression {
	public enum state {
		ON, OFF, LOCK
	}

	private String value;

	public getValue() {
		return this.value;
	}

	public setValue(Sitring value) {
		this.value = value;
	}
}
