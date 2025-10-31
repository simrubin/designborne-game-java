package game.items;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import game.enums.TradeType;

/**
 * Created by:
 * @author Shannon Jian Hong Chia
 * Modified by:
 *
 */

/**
 * The Tradeable interface defines methods that should be implemented by Tradeable items and weapons
 * that can be traded between actors in the game.
 */
public interface Tradeable {

    /**
     * Gets the base price of the tradeable item.
     *
     * @return The base price of the item.
     */
    int getPrice();

    /**
     * Checks if the price of the tradeable item is affected by some condition.
     *
     * @param seller The actor who is selling the item.
     * @return {@code true} if the price is affected, {@code false} otherwise.
     */
    boolean isPriceAffected(Actor seller);

    /**
     * Calculates the affected price of the tradeable item based on some condition.
     *
     * @param seller The actor who is selling the item.
     * @return The affected price of the item.
     */
    int affectedPrice(Actor seller);

    /**
     * Gets the trade type of the item, indicating the nature of the trade (e.g., normal, discounted, increased price).
     *
     * @param seller The actor who is selling the item.
     * @return The trade type of the item.
     */
    Enum<TradeType> getTradeType(Actor seller);

    /**
     * Sets the base price of the tradeable item.
     *
     * @param price The new base price of the item.
     * @return The tradeable item with the updated price.
     */
    Item setPrice(int price);

    /**
     * Creates a new instance of the tradeable item.
     *
     * @return A new instance of the tradeable item.
     */
    Item newItemInstance();
}
