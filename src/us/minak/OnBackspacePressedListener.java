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
