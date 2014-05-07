package us.minak;

import android.graphics.Color;

/*
 *	Not sure if this should be drawable or what.
 *
 */
public class MetaCircle {
	public MetaExpression metaExpr;
	public float x;
	public float y;
	public float radius;
	public int color;
	public boolean expanded;
	public int expansion; //the level of expansion (if multiple circles are expanded, this decides precidence)

	MetaCircle(float x, float y, float radius, int color, MetaExpression metaExpr) {
		this.metaExpr = metaExpr;
		this.x = x;
		this.y = y;
		this.radius = radius;
		this.color = color;
		this.expanded = false;
		this.expansion = 0;
	}
}
