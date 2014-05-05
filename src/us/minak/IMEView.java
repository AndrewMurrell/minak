package us.minak;

import java.util.LinkedList;
import java.util.Queue;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class IMEView extends RelativeLayout{
	private StringReciever mOutput;
	private final Context mContext;
	private final Queue<Character> mSymbolsQueue = new LinkedList<Character>();
	private OnCharacterEnteredListener mOnCharacterEnteredListener;

	public IMEView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onFinishInflate() {
		IMEGestureOverlayView drawingSpaceView = (IMEGestureOverlayView) findViewById(R.id.drawing_space);
		drawingSpaceView.setOutput(new StringReciever() {
			@Override
			public void putString(String character) { enterCharacter(character); }
		});
	}

	public void setOutput(StringReciever output) {
		mOutput = output;
	}

	private void enterCharacter(String character) {
		if (mOutput != null)
			mOutput.putString(character);
	}

	public Queue<Character> getSymbolsQueue() {
		return mSymbolsQueue;
	}

}
