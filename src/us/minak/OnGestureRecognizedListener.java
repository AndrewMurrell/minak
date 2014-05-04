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

/**
 * A simple interface for handling recognizing a gesture.
 */
public interface OnGestureRecognizedListener {
	/**
	 * Invoked when a gesture is recognized.
	 *
	 * @param character
	 *            The character represented by the gesture.
	 */
	void gestureRecognized(String character);
}
