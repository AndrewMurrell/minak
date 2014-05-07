package us.minak;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class IMEModifiers {
	// FIXME: hard-coded configuration
	private final IMEModifier[] modifiers = {
		new IMEModifier("Shift", KeyEvent.KEYCODE_SHIFT_LEFT, KeyEvent.META_SHIFT_ON),
		new IMEModifier("Ctrl" , KeyEvent.KEYCODE_CTRL_LEFT , KeyEvent.META_CTRL_ON ),
		new IMEModifier("Alt"  , KeyEvent.KEYCODE_ALT_LEFT  , KeyEvent.META_ALT_ON  )};

	// Static drawing resources
	private final Paint colorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private final Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	
	// Pre-calculated graphics stuff
	private float cx = 0;
	private float cy = 0;
	private double innerR = 0;
	private double outerR = 0;
	private double radEach = 0;
	 
	// The current state
	ArrayList<IMEModifier> selected = new ArrayList<IMEModifier>();
	
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
		
			if (selected.contains(modifiers[i])) {
				canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), colorPaint);
			} else {
				canvas.drawCircle((float)(cx+innerR*Math.cos(rad)),
				                  (float)(cy+innerR*Math.sin(rad)),
				                  (float)outerR,
				                  colorPaint);
			}
			canvas.drawText(modifiers[i].name,
			                (float)(cx+textR*Math.cos(rad)),
			                (float)(cy+textR*Math.sin(rad)),
			                textPaint);
		}
	}
	
	public void setSelectionPoint(float x, float y) {
		selected.clear();
		
		double mx;
		double my;
		double rad = 0;
		for (int i = 0; i < modifiers.length; i++) {
			rad = radEach * i;
			mx = cx+innerR*Math.cos(rad);
			my = cy+innerR*Math.sin(rad);
			
			if (Math.sqrt(Math.pow(mx-x,2)+Math.pow(my-y, 2)) < outerR)
				selected.add(modifiers[i]);
		}
	}

	public List<IMEModifier> getSelection() {
		return selected;
	}

	public void clearSelection() {
		selected.clear();
	}
}
