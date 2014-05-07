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

import android.content.Context;
import android.gesture.Gesture;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

/**
 * Represent a space where drawing gestures are performed.
 */
public class IMEGestureOverlayView extends GestureOverlayView implements OnGesturePerformedListener {
	private static final double SCORE_TRESHOLD = 3.0;
	private final GestureLibrary mGestureLibrary;
	private StringReciever mOnGestureRecognizedListener;
	public List<MetaCircle> circles = new LinkedList<MetaCircle>();
	private final Paint mPaint = new Paint();

	public IMEGestureOverlayView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mGestureLibrary = SettingsUtil.getGestureLibrary(context);
		mGestureLibrary.load();
		addOnGesturePerformedListener(this);
	}

	public void setOnGestureRecognizedListener(StringReciever onGestureRecognizedListener) {
		mOnGestureRecognizedListener = onGestureRecognizedListener;
	}

	@Override
	public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
		final List<Prediction> predictions = mGestureLibrary.recognize(gesture);
		Prediction bestPrediction = null;
		if (!predictions.isEmpty()) {
			bestPrediction = predictions.get(0);
		}
		if (mOnGestureRecognizedListener != null && bestPrediction != null) {
			if (bestPrediction.score > SCORE_TRESHOLD) {
				mOnGestureRecognizedListener.putString(bestPrediction.name);
			} else {
				clear(false);
			}
		}
	}

	public void onDraw(Canvas canvas) {
		for (MetaCircle circle : circles) {
			mPaint.setColor(circle.color);
			canvas.drawCircle(circle.x, circle.y, circle.radius, mPaint);
		}
	}
}
