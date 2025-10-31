package game.items;


import edu.monash.fit2099.engine.items.Item;
import game.enums.ItemCapability;

/**
 * Created by:
 * @author Shannon Jian Hong Chia
 * Modified by:
 *
 */

/**
 * The OldKey class represents an item that can be used as an old key in the game.
 */
public class OldKey extends Item {

    /**
     * Constructor for the OldKey class.
     */
    public OldKey(){
        super("Old Key", '-', true);
        addCapability(ItemCapability.UNLOCKABLE);
    }
}
