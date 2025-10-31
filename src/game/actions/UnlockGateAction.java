package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.enums.ItemCapability;
import game.enums.TerrainType;

/**
 * The UnlockGateAction class represents an action where an actor can attempt to unlock a gate.
 * It allows one actor to unlock a gate located at a specified location using an item with a certain capability.
 */
public class UnlockGateAction extends Action {

    /**
     * The actor requires an item with a specific itemCapability to unlock the gate
     */
    private ItemCapability itemCapability;

    /**
     * The location of the gate
     */
    private Location gateLocation;

    /**
     * Constructor for the UnlockGateAction class.
     *
     * @param itemCapability The capability of the item used to unlock the gate.
     * @param gateLocation   The location of the gate to be unlocked.
     */
    public UnlockGateAction(ItemCapability itemCapability, Location gateLocation) {
        this.itemCapability = itemCapability;
        this.gateLocation = gateLocation;
    }

    /**
     * Executes the UnlockGateAction, attempting to unlock the gate at the specified location using the item with the specified capability.
     *
     * @param actor The actor attempting to unlock the gate.
     * @param map   The GameMap where the action is being executed.
     * @return A message indicating the outcome of the unlocking attempt.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        Ground ground = gateLocation.getGround();
        if (ground.hasCapability(TerrainType.LOCKED)) {
            if (actor.hasCapability(itemCapability)) {
                ground.removeCapability(TerrainType.LOCKED);
                return "The gate is now unlocked";
            }
            return "Come back again once you have the key";
        } else {
            return "The gate is already unlocked";
        }
    }

    /**
     * Provides a description of the gate unlocking action for display in the menu.
     *
     * @param actor The actor performing the action.
     * @return A description of the gate unlocking action.
     */
    @Override
    public String menuDescription(Actor actor) {
        return "I wonder where this gate leads to... Try unlocking it?";
    }
}