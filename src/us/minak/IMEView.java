package us.minak;

import java.util.LinkedList;
import java.util.Queue;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class IMEView extends RelativeLayout{
	private final Context mContext;
	private final Queue<Character> mSymbolsQueue = new LinkedList<Character>();
	private OnCharacterEnteredListener mOnCharacterEnteredListener;

	public IMEView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
	}

	@Override
	protected void onFinishInflate() {
		DrawingSpaceView drawingSpaceView = (DrawingSpaceView) findViewById(R.id.drawing_space);
		drawingSpaceView.setOnGestureRecognizedListener(new OnGestureRecognizedListener() {
			@Override
			public void gestureRecognized(String character) {
				enterCharacter(character);
			}

		});
	}

	public void setOnCharacterEnteredListener(OnCharacterEnteredListener onCharacterEnteredListener) {
		mOnCharacterEnteredListener = onCharacterEnteredListener;
	}

	private void enterCharacter(String character) {
			mOnCharacterEnteredListener.characterEntered(character);
	}

	public Queue<Character> getSymbolsQueue() {
		return mSymbolsQueue;
	}

}
