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
import android.view.KeyCharacterMap;
import android.view.MotionEvent;
import android.view.inputmethod.InputConnection;

import java.util.List;

public class IMEGestureOverlayView extends GestureOverlayView implements OnGesturePerformedListener {
	private static final double SCORE_TRESHOLD = 3.0;
	private final GestureLibrary mGestureLibrary;
	private InputConnectionGetter icGetter = new InputConnectionGetter.NullGetter();
	private final IMEModifiers modifiers = new IMEModifiers();
	private final KeyCharacterMap charMap = KeyCharacterMap.load(KeyCharacterMap.VIRTUAL_KEYBOARD);
	float x = -1, y = -1;
	int meta = 0;

	// cache for repeated calls
	InputConnection ic = null;

	public IMEGestureOverlayView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mGestureLibrary = SettingsUtil.getGestureLibrary(context);
		mGestureLibrary.load();
		addOnGesturePerformedListener(this);
	}

	public void setInputConnectionGetter(InputConnectionGetter icGetter) {
		this.icGetter = icGetter;
	}

	private void sendKeyEvent(KeyEvent keyEvent) {
		if (ic != null) {
			ic.sendKeyEvent(new KeyEvent(
					keyEvent.getDownTime(),
					keyEvent.getEventTime(),
					keyEvent.getAction(),
					keyEvent.getKeyCode(),
					keyEvent.getRepeatCount(),
					keyEvent.getMetaState() | meta));
		}
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

		ic = icGetter.getCurrentInputConnection();
		if (ic != null) {
			if (bestPrediction != null) {
				if (bestPrediction.score > SCORE_TRESHOLD) {
					for (KeyEvent keyEvent : charMap.getEvents(bestPrediction.name.toCharArray()))
						sendKeyEvent(keyEvent);
				} else {
					clear(false);
				}
			}
			for (IMEModifier modifier : modifiers.getSelection()) {
				sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, modifier.keycode));
			}
		}
		modifiers.clearSelection();
		meta = 0;
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

			ic = icGetter.getCurrentInputConnection();
			if (ic != null) {
				for (IMEModifier modifier : modifiers.getSelection()) {
					sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, modifier.keycode));
					meta |= modifier.metamask;
				}
				return true;
			}
		}
		return false;
	}
}
