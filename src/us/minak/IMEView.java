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
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Represents the container for the drawing space and the two side panels.
 */
public class IMEView extends RelativeLayout {
	private StringReciever mOnCharacterEnteredListener;
	private final Queue<Character> mSymbolsQueue = new LinkedList<Character>();

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
	}

	public void setOnCharacterEnteredListener(StringReciever onCharacterEnteredListener) {
		mOnCharacterEnteredListener = onCharacterEnteredListener;
	}

	public Queue<Character> getSymbolsQueue() {
		return mSymbolsQueue;
	}

	/**
	 * Passes the given character to the input service.
	 *
	 * @param character
	 *            The character to enter
	 */
	private void enterCharacter(String character) {
		mOnCharacterEnteredListener.putString(character);
	}
}
