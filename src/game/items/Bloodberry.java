package game.items;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.items.Item;
import game.actions.ConsumeAction;


/**
 * Created by:
 * @author Leo Zhang
 * Modified by:
 */

/**
 * The bloodberry class represents the bloodberry item, which is a consumable item which can be used in order to increase the player's maximum health by 5
 */

public class Bloodberry extends TradeableItem implements Consumable {


    /**
     * The actor which consumes and is affected by the bloodberry
     */
    private Actor target;

    /**
     * Constructor for the bloodberry
     */
    public Bloodberry() {
        super("Bloodberry", '*', true, 10);
    }

    /**
     * Adds the consume action to menu if the item is unused
     * @param owner the actor that owns the item
     * @return a list of allowable actions, which if unused is the option to consume the item
     */
    public ActionList allowableActions(Actor owner) {
        ActionList actions = new ActionList();

        actions.add(new ConsumeAction(this));


        return actions;
    }

    /**
     * Determines if the price of a bloodberry is affected in trade
     * @param seller The actor who is selling the item.
     * @return
     */
    @Override
    public boolean isPriceAffected(Actor seller) {
        return false;
    }

    /**
     * Creates a new instance of a bloodberry
     * @return a new instance of the bloodberry item
     */
    @Override
    public Item newItemInstance(){
        return new Bloodberry();
    }

    /**
     * Returns the affected price of a bloodberry in trade
     * @param seller The actor who is selling the item.
     * @return The affected price of the item
     */
    @Override
    public int affectedPrice(Actor seller) {
        return getPrice();
    }

    /**
     * Performs the action of consumption on the user of the bloodberry, which increases maximum health by 5
     * @param target The actor who is consuming the item.
     */
    @Override
    public void consumedBy(Actor target) {
        this.target = target;
        target.modifyAttributeMaximum(BaseActorAttributes.HEALTH, ActorAttributeOperations.INCREASE, 5);
        target.removeItemFromInventory(this); // Remove item after used
    }

    @Override
    public String consumeToString(Actor target) {
        return String.format("Consume %s to increase maximum health by 5", this);
    }

}