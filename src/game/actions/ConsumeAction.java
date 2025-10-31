package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.GameMap;
import game.items.Consumable;

/**
 * The IncreaseStaminaAction class represents an action where an actor can increase their maximum stamina.
 * It allows one actor to increase their stamina by a certain amount.
 */
public class ConsumeAction extends Action {


    private Consumable consumable;


    public ConsumeAction(Consumable consumable){
        this.consumable = consumable;

    }

    /**
     * Executes the IncreaseStaminaAction, increasing the maximum stamina of the target actor by the specified amount.
     *
     * @param owner The actor performing the action.
     * @param map   The GameMap where the action is being executed.
     * @return A message indicating the increase in stamina.
     */
    @Override
    public String execute(Actor owner, GameMap map) {

        consumable.consumedBy(owner);
        return owner + " consumes " + consumable;
    }

    /**
     * Provides a description of the stamina-increasing action for display in the menu.
     *
     * @param actor The actor performing the action.
     * @return A description of the stamina-increasing action.
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " " + consumable.consumeToString(actor);
    }
}
