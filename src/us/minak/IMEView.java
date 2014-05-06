/*
 ********************************************************************************
 * Copyright (c) 2012 Samsung Electronics, Inc.
 * All rights reserved.
 *
 * This software is a confidential and proprietary information of Samsung
 * Electronics, Inc. ("Confidential Information"). You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms
 * of the license agreement you entered into with Samsung Electronics.
 ********************************************************************************
 */

package us.minak;

import java.util.LinkedList;
import java.util.Locale;
import java.util.Queue;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

/**
 * Represents the container for the drawing space and the two side panels.
 */
public class IMEView extends RelativeLayout {
	private final Context mContext;
	private OnCharacterEnteredListener mOnCharacterEnteredListener;
	private OnBackspacePressedListener mOnBackspacePressedListener;
	private Button mShiftButton;
	private ShiftState mShiftState = ShiftState.OFF;
	private final Queue<Character> mSymbolsQueue = new LinkedList<Character>();

	private enum ShiftState {
		OFF, ON, CAPS_LOCK
	};

	public IMEView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		LocalBroadcastManager.getInstance(mContext).registerReceiver(mBroadcastReceiver,
				new IntentFilter(IMESymbolsActivity.INTENT_ACTION));
	}

	@Override
	protected void onFinishInflate() {
		IMEGestureOverlayView drawingSpaceView = (IMEGestureOverlayView) findViewById(R.id.drawing_space);
		drawingSpaceView.setOnGestureRecognizedListener(new OnGestureRecognizedListener() {
			@Override
			public void gestureRecognized(String character) {
				enterCharacter(character);
			}
		});

		final Button symbolsButton = (Button) findViewById(R.id.symbols_btn);
		symbolsButton.setOnClickListener(mButtonClickListener);
		symbolsButton.setOnLongClickListener(mButtonLongClickListener);

		mShiftButton = (Button) findViewById(R.id.shift_btn);
		mShiftButton.setOnClickListener(mButtonClickListener);
		mShiftButton.setOnLongClickListener(mButtonLongClickListener);

		final Button backspaceButton = (Button) findViewById(R.id.backspace_btn);
		backspaceButton.setOnClickListener(mButtonClickListener);
		backspaceButton.setOnLongClickListener(mButtonLongClickListener);

		final Button spaceButton = (Button) findViewById(R.id.space_btn);
		spaceButton.setOnClickListener(mButtonClickListener);
		spaceButton.setOnLongClickListener(mButtonLongClickListener);
	}

	public void setOnCharacterEnteredListener(OnCharacterEnteredListener onCharacterEnteredListener) {
		mOnCharacterEnteredListener = onCharacterEnteredListener;
	}

	public void setOnBackspacePressedListener(OnBackspacePressedListener onBackspacePressedListener) {
		mOnBackspacePressedListener = onBackspacePressedListener;
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
			if (IMESymbolsActivity.INTENT_ACTION.equals(intent.getAction())) {
				mSymbolsQueue.add(intent.getCharExtra(IMESymbolsActivity.INTENT_EXTRA_NAME, '?'));
			}
		}
	};

	/**
	 * Listener handling pressing all buttons.
	 */
	private final OnClickListener mButtonClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.symbols_btn:
				final Intent intent = new Intent(mContext, IMESymbolsActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				mContext.startActivity(intent);
				break;
			case R.id.shift_btn:
				shift();
				break;
			case R.id.backspace_btn:
				mOnBackspacePressedListener.backspacePressed(false);
				break;
			case R.id.space_btn:
				mOnCharacterEnteredListener.characterEntered(" ");
				break;
			default:
				throw new IllegalArgumentException();
			}
		}
	};

	/**
	 * Listener handling long pressing all buttons.
	 */
	private final OnLongClickListener mButtonLongClickListener = new OnLongClickListener() {
		@Override
		public boolean onLongClick(View v) {
			switch (v.getId()) {
			case R.id.symbols_btn:
			case R.id.shift_btn:
				break;
			case R.id.backspace_btn:
				mOnBackspacePressedListener.backspacePressed(true);
				return true;
			case R.id.space_btn:
				break;
			default:
				throw new IllegalArgumentException();
			}

			return false;
		}
	};

	/**
	 * Changes shift state to the next one (OFF -> ON -> CAPS LOCK).
	 */
	private void shift() {
		switch (mShiftState) {
		case OFF:
			mShiftState = ShiftState.ON;
			//mShiftButton.setBackgroundResource(R.drawable.shift_on);
			break;
		case ON:
			mShiftState = ShiftState.CAPS_LOCK;
			//mShiftButton.setBackgroundResource(R.drawable.shift_caps_lock);
			break;
		case CAPS_LOCK:
			mShiftState = ShiftState.OFF;
			//mShiftButton.setBackgroundResource(R.drawable.shift_off);
			break;
		default:
			throw new IllegalArgumentException();
		}
	}

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
			shift();
			break;
		case CAPS_LOCK:
			mOnCharacterEnteredListener.characterEntered(character.toUpperCase(Locale.ENGLISH));
			break;
		default:
			throw new IllegalArgumentException();
		}
	}
}
