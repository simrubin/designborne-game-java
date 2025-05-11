package game.behaviour;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.AttackAction;

/**
 * Created by:
 * @author Shannon Jian Hong Chia
 * Modified by:
 *
 */

/**
 * The AttackBehaviour class represents a behavior where an actor attempts to attack nearby actors of a specified target type.
 * It searches for adjacent locations and attacks the first actor found with the target type.
 */
public class AttackBehaviour implements Behaviour {

    /**
     * The targeted actor requires a specific targetType
     */
    private final Enum<?> targetType;

    /**
     * Constructor for the AttackBehaviour class.
     *
     * @param targetType The target type of actor to attack.
     */
    public AttackBehaviour(Enum<?> targetType) {
        this.targetType = targetType;
    }

    /**
     * Gets the action for the actor to perform, which is to attack a nearby actor of the specified target type.
     *
     * @param actor The actor performing the attack behavior.
     * @param map   The GameMap where the action is being executed.
     * @return An AttackAction if a valid target is found, otherwise null.
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        Location currentLocation = map.locationOf(actor);

        // Check adjacent locations
        for (Exit exit : currentLocation.getExits()) {
            Location adjacentLocation = exit.getDestination();

            // Check if there's an actor with the target type at the adjacent location
            Actor target = adjacentLocation.getActor();
            if (target != null && target.hasCapability(targetType)) {
                String direction = exit.getName(); // Use the exit direction as the attack direction

                // Return an AttackAction
                return new AttackAction(target, direction);
            }
        }
        return null; // No valid target found, return null
    }
}