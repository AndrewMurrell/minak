package us.minak;

import android.content.Context;
import android.gesture.Gesture;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.inputmethod.InputConnection;

import java.util.List;

public class IMEGestureOverlayView extends GestureOverlayView implements OnGesturePerformedListener {
	private static final double SCORE_TRESHOLD = 3.0;
	private final GestureLibrary mGestureLibrary;
	private InputConnectionGetter icGetter = new InputConnectionGetter.NullGetter();
	private final IMEModifiers modifiers = new IMEModifiers();
	float x = -1, y = -1;

	public IMEGestureOverlayView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mGestureLibrary = SettingsUtil.getGestureLibrary(context);
		mGestureLibrary.load();
		addOnGesturePerformedListener(this);
	}

	public void setInputConnectionGetter(InputConnectionGetter icGetter) {
		this.icGetter = icGetter;
	}

	/**
	 * This function is pretty strongly based on the code in
	 * Samsung's "Penboard" whitepaper.
	 */
	@Override
	public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
		final List<Prediction> predictions = mGestureLibrary.recognize(gesture);
		Prediction bestPrediction = null;
		if (!predictions.isEmpty()) {
			bestPrediction = predictions.get(0);
		}

		InputConnection ic = icGetter.getCurrentInputConnection();
		if (ic != null) {
			if (bestPrediction != null) {
				if (bestPrediction.score > SCORE_TRESHOLD) {
					ic.commitText(bestPrediction.name, 1);
				} else {
					clear(false);
				}
			}
			for (IMEModifier modifier : modifiers.getSelection()) {
				ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, modifier.keycode));
			}
		}
		modifiers.clearSelection();
		invalidate();
		x = y = -1;
	}

	@Override
	public void onDraw(Canvas canvas) {
		float d = Math.min(canvas.getWidth(), canvas.getHeight());
		modifiers.draw(canvas, d/2, d/2, d*.47F);
	}

	@Override
	public boolean onTouchEvent (MotionEvent event) {
		if (x < 0 && y < 0 && event.getActionMasked() == MotionEvent.ACTION_DOWN) {
			x = event.getX();
			y = event.getY();
			modifiers.setSelectionPoint(x, y);
			invalidate();

			InputConnection ic = icGetter.getCurrentInputConnection();
			if (ic != null) {
				for (IMEModifier modifier : modifiers.getSelection()) {
					ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, modifier.keycode));
				}
				return true;
			}
		}
		return false;
	}
}
