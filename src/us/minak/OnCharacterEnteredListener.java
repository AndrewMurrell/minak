package us.minak;
/**
 * A simple interface for handling entering a character.
 */
public interface OnCharacterEnteredListener {
	/**
	 * Invoked when a character is entered.
	 *
	 * @param character
	 *            The entered character
	 */
	void characterEntered(String character);
}
