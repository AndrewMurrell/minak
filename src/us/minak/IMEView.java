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
import java.util.Queue;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Represents the container for the drawing space and the two side panels.
 */
public class IMEView extends RelativeLayout {
	private StringReciever mOnCharacterEnteredListener;
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

		//dynamic MetaCircle adding stuff here. replace null with Shift or Ctrl or Meta or Alt or Hyper or whatever.
		drawingSpaceView.circles.add(new MetaCircle((float)50.0, (float)50.0, (float)20.0, Color.RED, new MetaExpression(null)));
		drawingSpaceView.circles.add(new MetaCircle((float)70.0, (float)70.0, (float)20.0, Color.RED, new MetaExpression(null)));
		drawingSpaceView.circles.add(new MetaCircle((float)50.0, (float)30.0, (float)20.0, Color.RED, new MetaExpression(null)));
	}

	public void setOnCharacterEnteredListener(StringReciever onCharacterEnteredListener) {
		mOnCharacterEnteredListener = onCharacterEnteredListener;
	}

	public Queue<Character> getSymbolsQueue() {
		return mSymbolsQueue;
	}

	private final OnTouchListener mOnTouchListener = new OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			return setTouchLocation(event.getX(), event.getY());
		}	
	};

	/**
	 * Passes the given character to the input service.
	 *
	 * @param character
	 *            The character to enter
	 */
	private void enterCharacter(String character) {
		for (MetaCircle circle : ((IMEGestureOverlayView) findViewById(R.id.drawing_space)).circles) {
			//go through circles and check if they are applicable
			if (circle.containsPoint(this.x, this.y) && circle.getMetaExpression().state != MetaExpression.State.OFF) {
				//TODO: apply the Meta-key here
				;
			}
		}
		mOnCharacterEnteredListener.putString(character);
	}
}
