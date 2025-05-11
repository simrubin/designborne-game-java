package game.items;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import game.enums.ItemCapability;
import game.enums.TradeType;

/**
 * Created by:
 * @author Shannon Jian Hong Chia
 * Modified by:
 *
 */

/**
 * The TradeableItem class represents an item that can be traded between actors in the game.
 * It extends the Item class and implements the Tradeable interface.
 */
public abstract class TradeableItem extends Item implements Tradeable{

    /**
     * The price of the tradeable item.
     */
    private int price;

    /**
     * Constructor for the TradeableItem class.
     *
     * @param name        The name of the item.
     * @param displayChar The character representing the item in the display.
     * @param portable    A flag indicating whether the item is portable or not.
     * @param price       The base price of the item.
     */
    public TradeableItem(String name, char displayChar, boolean portable, int price) {
        super(name,displayChar,portable);
        this.price = price;
        this.addCapability(ItemCapability.TRADEABLE);
    }

    /**
     * Gets the base price of the tradeable item.
     *
     * @return The base price of the item.
     */
    public int getPrice(){
        return price;
    }

    /**
     * Sets the price of the tradeable item.
     *
     * @param price The new base price of the item.
     * @return The tradeable item with the updated price.
     */
    public Item setPrice(int price){
        this.price = price;
        return this;
    }

    /**
     * Creates a new instance of the tradeable item.
     *
     * @return A new instance of the tradeable item.
     */
    public abstract Item newItemInstance();

    /**
     * Gets the trade type of the item, indicating the nature of the trade (e.g., scam, no scam etc).
     *
     * @param seller The actor who is selling the item.
     * @return The trade type of the item.
     */
    public Enum<TradeType> getTradeType(Actor seller){
        return TradeType.NON_SCAMMABLE;     // Default all items are "Non-scammable"

    }

}
