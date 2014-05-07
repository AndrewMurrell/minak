package us.minak;

/*
 *	Not sure if this should be drawable or what.
 *
 */
public class IMEModifierCircle {
	private IMEModifier metaExpression;
	public float x;
	public float y;
	public float radius;
	public int color;
	public boolean expanded;
	public int expansion; //the level of expansion (if multiple circles are expanded, this decides precidence)

	IMEModifierCircle(float x, float y, float radius, int color, IMEModifier metaExpr) {
		this.setMetaExpression(metaExpr);
		this.x = x;
		this.y = y;
		this.radius = radius;
		this.color = color;
		this.expanded = false;
		this.expansion = 0;
	}

	public boolean containsPoint(float x, float y) {
		return Math.pow(x - this.x, 2) + Math.pow(y - this.y, 2) < Math.pow(this.radius, 2) ? true : false;
	}

	public IMEModifier getMetaExpression() {
		return metaExpression;
	}

	public void setMetaExpression(IMEModifier metaExpr) {
		this.metaExpression = metaExpr;
	}
}
