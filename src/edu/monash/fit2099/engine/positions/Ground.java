package edu.monash.fit2099.engine.positions;

import edu.monash.fit2099.engine.GameEntity;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.displays.Printable;

/**
 * Class representing terrain type
 */
public abstract class Ground extends GameEntity implements Printable {

    private char displayChar;
    private String displayString;
    private boolean hasCustomDisplay;

	/**
	 * Constructor.
	 *
	 * @param displayChar character to display for this type of terrain
	 */
	public Ground(char displayChar) {
		this.displayChar = displayChar;
		this.hasCustomDisplay = false;
	}

	@Override
	public char getDisplayChar() {
		return displayChar;
	}

	protected final void setDisplayChar(char displayChar){
		this.displayChar = displayChar;
	}
	
	/**
	 * Returns the Ground's display string, so that it can be displayed in the UI.
	 * @return the Ground's display string
	 */
	@Override
	public String getDisplayString() {
		return displayString != null ? displayString : Character.toString(displayChar);
	}
	
	/**
	 * Set a custom display string (like an emoji) for this Ground.
	 * @param displayString the new display string
	 */
	public final void setDisplayString(String displayString) {
		this.displayString = displayString;
		this.hasCustomDisplay = true;
	}
	
	/**
	 * Check if this Ground has a custom display.
	 * @return true if the Ground has a custom display string
	 */
	@Override
	public boolean hasCustomDisplay() {
		return hasCustomDisplay;
	}

	/**
	 * Returns an empty Action list.
	 *
	 * @param actor the Actor acting
	 * @param location the current Location
	 * @param direction the direction of the Ground from the Actor
	 * @return a new, empty collection of Actions
	 */
	public ActionList allowableActions(Actor actor, Location location, String direction){
		return new ActionList();
	}

	/**
	 * Override this to implement impassable terrain, or terrain that is only passable if conditions are met.
	 *
	 * @param actor the Actor to check
	 * @return true
	 */
	public boolean canActorEnter(Actor actor) {
		return true;
	}

	/**
	 * Ground can also experience the joy of time.
	 * @param location The location of the Ground 
	 */
	public void tick(Location location) {
	}
	
	/**
	 * Override this to implement terrain that blocks thrown objects but not movement, or vice versa
	 * @return true
	 */
	public boolean blocksThrownObjects() {
		return false;
	}
}
