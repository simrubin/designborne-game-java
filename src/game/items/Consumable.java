package game.items;

import edu.monash.fit2099.engine.actors.Actor;

/**
 * Created by:
 * @author Shannon Jian Hong Chia
 * Modified by:
 *
 */

/**
 * The Consumable interface represents items that can be consumed by an actor.
 * Implementing classes should define the behavior of consumption.
 */
public interface Consumable {

    /**
     * Defines the action when the item is consumed by a specific actor.
     *
     * @param owner The actor who is consuming the item.
     */
    void consumedBy(Actor owner);

    String consumeToString(Actor target);
}
