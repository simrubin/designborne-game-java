package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.items.Consumable;

/**
 * Created by:
 * @author Leo Zhang
 * Modified by:
 */

/**
 * The DrinkPuddleAction class represents an action which can be performed while an actor is on a puddle tile
 */
public class DrinkPuddleAction extends Action {


    private Consumable consumable;


    public DrinkPuddleAction(Consumable consumable){
        this.consumable = consumable;

    }

    /**
     * Executes the DrinkPuddleAction, restoring 1 health to the actor and restoring 1% of their maximum stamina
     *
     * @param owner The actor performing the action.
     * @param map   The GameMap where the action is being executed.
     * @return A message indicating that the action has been performed
     */
    @Override
    public String execute(Actor owner, GameMap map) {

        consumable.consumedBy(owner);
        return owner + " drinks from " + consumable;
    }

    /**
     * Provides a description of the action which can be performed with the puddle
     *
     * @param actor The actor performing the action.
     * @return A description of the action.
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " drinks from " + consumable;
    }
}
