package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.reset.ResetManager;

/**
 * Created by:
 * @author Shannon Jian Hong Chia
 * Modified by:
 *
 */

/**
 * The {@code RemoveActorAction} class represents an action that removes an {@code Actor} from the game map.
 * It also unregisters the actor from the {@code ResetManager} to prevent it from being affected by future reset operations.
 */
public class RemoveActorAction extends Action {

    /**
     * Executes the action to remove the specified actor from the game map and unregister it from the ResetManager.
     *
     * @param actor the actor to be removed
     * @param map the game map from which the actor is removed
     * @return a description of the action result
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        map.removeActor(actor);
        ResetManager.getInstance().unregister(actor);
        return menuDescription(actor);
    }

    /**
     * Provides a menu description for the action result, indicating that the actor has been removed from the map.
     *
     * @param actor the actor that was removed
     * @return a description of the action result
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " has been removed from the map";
    }
}
