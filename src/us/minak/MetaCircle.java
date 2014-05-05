package us.minak;

import android.graphics.Color;

/*
 *	Not sure if this should be drawable or what.
 *
 */
public class MetaCircle {
	public MetaExpression metaExpr;
	public int x;
	public int y;
	public int radius;
	public Color color;
	public boolean expanded;
	public int expansion; //the level of expansion (if multiple circles are expanded, this decides precidence)
}
