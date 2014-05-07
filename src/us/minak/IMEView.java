package us.minak;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.view.View;
import android.view.inputmethod.InputConnection;

/**
 * Represents the container for the drawing space and the two side panels.
 */
public class IMEView extends RelativeLayout implements InputConnectionGetter {
	public IMEView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onFinishInflate() {
		IMEGestureOverlayView gestureOverlayView = (IMEGestureOverlayView) findViewById(R.id.drawing_space);
		gestureOverlayView.setInputConnectionGetter(this);

		final Button backspaceButton = (Button) findViewById(R.id.backspace_btn);
		backspaceButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) { backspace(); }
		});
	}

	private InputConnectionGetter icGetter = new InputConnectionGetter.NullGetter();
	public void setInputConnectionGetter(InputConnectionGetter icGetter) {
		this.icGetter = icGetter;
	}
	@Override
	public InputConnection getCurrentInputConnection() {
		return icGetter.getCurrentInputConnection();
	}

	private void backspace() {
		InputConnection ic = getCurrentInputConnection();
		if (ic != null)
			ic.deleteSurroundingText(1, 0);
	}
}
