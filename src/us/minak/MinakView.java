package us.minak;

import android.content.Context;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.util.AttributeSet;
import android.support.v4.content.LocalBroadcastManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.BroadcastReceiver;

import java.util.*;

public class MinakView extends RelativeLayout {
	private final Context mContext;
	private OnCharacterEnteredListener mOnCharacterEnteredListener;

	private ShiftState mShiftState = ShiftState.OFF;
	private final Queue<Character> mSymbolsQueue = new LinkedList<Character>();

	private enum ShiftState {
		OFF, ON, CAPS_LOCK
	};

	public MinakView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		LocalBroadcastManager.getInstance(mContext).registerReceiver(mBroadcastReceiver,
				new IntentFilter(SymbolsActivity.INTENT_ACTION));
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



	public Queue<Character> getSymbolsQueue() {
		return mSymbolsQueue;
	}

	/**
	 * Receiver for broadcasts coming from the symbols activity.
	 */
	private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (SymbolsActivity.INTENT_ACTION.equals(intent.getAction())) {
				mSymbolsQueue.add(intent.getCharExtra(SymbolsActivity.INTENT_EXTRA_NAME, '?'));
			}
		}
	};

	/**
	 * Listener handling pressing all buttons.
	 */

	/**
	 * Listener handling long pressing all buttons.
	 */


	/**
	 * Changes shift state to the next one (OFF -> ON -> CAPS LOCK).
	 */


	/**
	 * Passes the given character to the input service.
	 *
	 * @param character
	 *            The character to enter
	 */
	private void enterCharacter(String character) {
		switch (mShiftState) {
		case OFF:
			mOnCharacterEnteredListener.characterEntered(character);
			break;
		case ON:
			mOnCharacterEnteredListener.characterEntered(character.toUpperCase(Locale.ENGLISH));
			break;
		case CAPS_LOCK:
			mOnCharacterEnteredListener.characterEntered(character.toUpperCase(Locale.ENGLISH));
			break;
		default:
			throw new IllegalArgumentException();
		}
	}

}