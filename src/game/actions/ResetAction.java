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
 * The {@code ResetAction} class represents an action that triggers the reset operation (global reset)
 */
public class ResetAction extends Action {

    /**
     * Executes the reset action, triggering the reset operation
     *
     * @param actor the actor initiating the action
     * @param map the game map on which the action occurs
     * @return always returns null, as the reset action may not have a meaningful result
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        ResetManager.getInstance().run();
        return null;
    }

    /**
     * Provides a menu description for the reset action.
     *
     * @param actor the actor for which the menu description is generated
     * @return always returns null, as reset actions may not have a specific menu representation
     */
    @Override
    public String menuDescription(Actor actor) {
        return null;
    }
}
