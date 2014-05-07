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

import java.util.Queue;

import android.inputmethodservice.InputMethodService;
import android.view.View;
import android.view.inputmethod.EditorInfo;

/**
 * Represent the application input service.
 */
public class IMEService extends InputMethodService {
	private IMEView mPenboardView;

	@Override
	public View onCreateInputView() {
		final IMEView penboardView = (IMEView) getLayoutInflater().inflate(R.layout.ime, null);

		penboardView.setOnCharacterEnteredListener(new StringReciever() {
			@Override
			public void putString(String character) {
				getCurrentInputConnection().commitText(character, 1);
			}
		});

		mPenboardView = penboardView;
		return penboardView;
	}

	@Override
	public void onStartInput(EditorInfo attribute, boolean restarting) {
		if (mPenboardView != null) {
			final Queue<Character> symbolsQueue = mPenboardView.getSymbolsQueue();
			while (!symbolsQueue.isEmpty()) {
				final Character character = symbolsQueue.poll();
				getCurrentInputConnection().commitText(String.valueOf(character), 1);
			}
		}
	}
}
