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

import java.util.List;

import android.content.Context;
import android.gesture.Gesture;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;
import android.util.AttributeSet;

/**
 * Represent a space where drawing gestures are performed.
 */
public class IMEGestureOverlayView extends GestureOverlayView implements OnGesturePerformedListener {
	private static final double SCORE_TRESHOLD = 3.0;
	private final GestureLibrary mGestureLibrary;
	private StringReciever mOutput;

	public IMEGestureOverlayView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mGestureLibrary = SettingsUtil.getGestureLibrary(context);
		mGestureLibrary.load();
		addOnGesturePerformedListener(this);
	}

	public void setOutput(StringReciever output) {
		mOutput = output;
	}

	@Override
	public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
		final List<Prediction> predictions = mGestureLibrary.recognize(gesture);
		Prediction bestPrediction = null;
		if (!predictions.isEmpty()) {
			bestPrediction = predictions.get(0);
		}
		if (mOutput != null && bestPrediction != null) {
			if (bestPrediction.score > SCORE_TRESHOLD) {
				mOutput.putString(bestPrediction.name);
			} else {
				clear(false);
			}
		}
	}
}
