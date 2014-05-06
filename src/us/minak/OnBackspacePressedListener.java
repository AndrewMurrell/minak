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
 * A simple interface for handling pressing the backspace button.
 */
public interface OnBackspacePressedListener {
	/**
	 * Invoked when the backspace button is pressed.
	 *
	 * @param isLongClick
	 *            if the button is long pressed
	 */
	void backspacePressed(boolean isLongClick);
}
