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
import java.util.List;
import java.util.Locale;
import java.util.Queue;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

/**
 * Represents the container for the drawing space and the two side panels.
 */
public class IMEView extends RelativeLayout {
	private StringReciever mOnCharacterEnteredListener;
	private OnBackspacePressedListener mOnBackspacePressedListener;
	private Button mShiftButton;
	private ShiftState mShiftState = ShiftState.OFF;
	private final Queue<Character> mSymbolsQueue = new LinkedList<Character>();

	private float x;
	private float y;
	private boolean ongoingGesture = false;

	public boolean setTouchLocation(float x, float y) {
		if (!ongoingGesture) {
			this.x = x;
			this.y = y;
			return true;
		}
		return false;
	}

	public void setState(boolean state) {
		ongoingGesture = state;
	}

	public boolean getState() {
		return ongoingGesture;
	}

	private enum ShiftState {
		OFF, ON, CAPS_LOCK
	};

	public IMEView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onFinishInflate() {
		IMEGestureOverlayView drawingSpaceView = (IMEGestureOverlayView) findViewById(R.id.drawing_space);
		drawingSpaceView.setOnGestureRecognizedListener(new StringReciever() {
			@Override
			public void putString(String character) {
				enterCharacter(character);
			}
		});

		mShiftButton = (Button) findViewById(R.id.shift_btn);
		mShiftButton.setOnClickListener(mButtonClickListener);
		mShiftButton.setOnLongClickListener(mButtonLongClickListener);

		final Button backspaceButton = (Button) findViewById(R.id.backspace_btn);
		backspaceButton.setOnClickListener(mButtonClickListener);
		backspaceButton.setOnLongClickListener(mButtonLongClickListener);

		final Button spaceButton = (Button) findViewById(R.id.space_btn);
		spaceButton.setOnClickListener(mButtonClickListener);
		spaceButton.setOnLongClickListener(mButtonLongClickListener);

		//dynamic MetaCircle adding stuff here. replace null with Shift or Ctrl or Meta or Alt or Hyper or whatever.
		drawingSpaceView.circles.add(new MetaCircle((float)50.0, (float)50.0, (float)20.0, Color.RED, new MetaExpression(null)));
		drawingSpaceView.circles.add(new MetaCircle((float)70.0, (float)70.0, (float)20.0, Color.RED, new MetaExpression(null)));
		drawingSpaceView.circles.add(new MetaCircle((float)50.0, (float)30.0, (float)20.0, Color.RED, new MetaExpression(null)));
	}

	public void setOnCharacterEnteredListener(StringReciever onCharacterEnteredListener) {
		mOnCharacterEnteredListener = onCharacterEnteredListener;
	}

	public void setOnBackspacePressedListener(OnBackspacePressedListener onBackspacePressedListener) {
		mOnBackspacePressedListener = onBackspacePressedListener;
	}

	public Queue<Character> getSymbolsQueue() {
		return mSymbolsQueue;
	}

	/**
	 * Listener handling pressing all buttons.
	 */
	private final OnClickListener mButtonClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.shift_btn:
				shift();
				break;
			case R.id.backspace_btn:
				mOnBackspacePressedListener.backspacePressed(false);
				break;
			case R.id.space_btn:
				mOnCharacterEnteredListener.putString(" ");
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

	private final OnTouchListener mOnTouchListener = new OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			return setTouchLocation(event.getX(), event.getY());
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
		//for each circle in circles check to see if the touch was in the circle and apply the meta-key
		switch (mShiftState) {
		case OFF:
			mOnCharacterEnteredListener.putString(character);
			break;
		case ON:
			mOnCharacterEnteredListener.putString(character.toUpperCase(Locale.ENGLISH));
			shift();
			break;
		case CAPS_LOCK:
			mOnCharacterEnteredListener.putString(character.toUpperCase(Locale.ENGLISH));
			break;
		default:
			throw new IllegalArgumentException();
		}
	}
}
