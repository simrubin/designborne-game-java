package game.weapon;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.enums.ItemCapability;
import game.enums.TradeType;
import game.items.Tradeable;

/**
 * Created by:
 * @author Shannon Jian Hong Chia
 * Modified by:
 *
 */

/**
 * The TradeableWeapon class represents a weapon item that can be traded between actors in the game.
 * It extends the WeaponItem class and implements the Tradeable interface.
 */

public abstract class TradeableWeapon extends WeaponItem implements Tradeable {

    /**
     * The price of the tradeable weapon.
     */
    private int price;

    /**
     * Constructor
     *
     * @param name        The name of the weapon.
     * @param displayChar The character representing the weapon in the display.
     * @param damage      The damage inflicted by the weapon.
     * @param verb        The verb used to describe the weapon's action.
     * @param hitRate     The hit rate of the weapon.
     * @param price       The price of the weapon.
     */
    public TradeableWeapon(String name, char displayChar, int damage, String verb, int hitRate, int price) {
        super(name,displayChar,damage,verb,hitRate);
        this.price = price;
        this.addCapability(ItemCapability.TRADEABLE);
    }

    /**
     * Gets the price of the tradeable weapon.
     *
     * @return The price of the weapon.
     */
    public int getPrice(){
        return price;
    }

    /**
     * Sets the price of the tradeable weapon.
     *
     * @param price The new base price of the weapon.
     * @return The tradeable weapon with the updated price.
     */
    public Item setPrice(int price){
        this.price = price;
        return this;
    }

    /**
     * Creates a new instance of the tradeable weapon.
     *
     * @return A new instance of the tradeable weapon.
     */
    public abstract Item newItemInstance();

    /**
     * Gets the trade type of the weapon, indicating the nature of the trade (e.g., Scam, no scam etc).
     *
     * @param seller The actor who is selling the weapon.
     * @return The trade type of the weapon.
     */
    public Enum<TradeType> getTradeType(Actor seller){
        return TradeType.NON_SCAMMABLE;     // Default trade type - Non-scammable

    }

}
