package us.minak;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import java.util.List;
import java.util.ArrayList;

public class IMEModifiers {
	private final String[] modifiers = { "shift", "ctrl", "alt" };
	private final Paint colorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private final Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	
	private float cx = 0;
	private float cy = 0;
	private double innerR = 0;
	private double outerR = 0;
	private double radEach = 0;
	 
	
	public IMEModifiers() {
		textPaint.setColor(Color.BLACK);
	}
	
	public void draw(Canvas canvas, float cx, float cy, float r) {
		this.cx = cx;
		this.cy = cy;
		innerR = r*(1.2/3.0);
		outerR = r*(1.8/3.0);
		radEach = (Math.PI*2.0)/modifiers.length;

		double textR = r*.8;

		double rad = 0;
		float[] hsv = {0F, 1F, .75F};

		for (int i = 0; i < modifiers.length; i++) {
			rad = radEach * i;
			hsv[0] = (float)Math.toDegrees(rad);
			colorPaint.setColor(Color.HSVToColor(0x80, hsv));
		
			canvas.drawCircle(
					(float)(cx+innerR*Math.cos(rad)),
					(float)(cy+innerR*Math.sin(rad)),
					(float)outerR,
					colorPaint);
			canvas.drawText(
					modifiers[i],
					(float)(cx+textR*Math.cos(rad)),
					(float)(cy+textR*Math.sin(rad)),
					textPaint);
		}		
	}
	
	public List<String> getModifiersAtPoint(float x, float y) {
		ArrayList<String> ret = new ArrayList<String>();
		
		double mx;
		double my;
		double rad = 0;
		for (int i = 0; i < modifiers.length; i++) {
			rad = radEach * i;
			mx = cx+innerR*Math.cos(rad);
			my = cy+innerR*Math.sin(rad);
			
			if (Math.sqrt(Math.pow(mx-x,2)+Math.pow(my-y, 2)) > outerR)
				ret.add(modifiers[i]);
		}
		return ret;
	}
}
