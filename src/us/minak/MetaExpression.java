package us.minak;

public class MetaExpression {
	public enum state {
		ON, OFF, LOCK
	}

	private String value;

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
