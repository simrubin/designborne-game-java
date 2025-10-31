package edu.monash.fit2099.engine.displays;

/**
 * Things that are Printable can appear in the user interface and therefore can be represented
 * by a character in the UI.
 */
public interface Printable {
	/**
	 * @return  display character of an instance
	 */
	char getDisplayChar();
	
	/**
	 * Returns a string representation for display. This allows for multi-character displays like emojis.
	 * By default, it returns a string with the single character from getDisplayChar().
	 * 
	 * @return string representation for display (can be emoji)
	 */
	default String getDisplayString() {
		return Character.toString(getDisplayChar());
	}
	
	/**
	 * Indicates whether this Printable has a custom string representation.
	 * 
	 * @return true if getDisplayString() returns something other than the default character
	 */
	default boolean hasCustomDisplay() {
		return false;
	}
}
