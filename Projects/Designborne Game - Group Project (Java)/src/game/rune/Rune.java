package game.rune;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import game.actions.ConsumeAction;
import game.items.Consumable;


/**
 * Created by:
 * @author Leo Zhang
 *
 */

/**
 * The rune class represents the rune item which can be consumed by the player to increase their total balance
 */
public class Rune extends Item implements Consumable {

    /**
     * The actor which consumes the rune
     */
    private Actor target;

    /**
     * The amount to increase the actor's balance by once consumed
     */
    private int amount;

    /**
     * Constructor for the rune
     * @param runeValue the value of any given item
     */
    public Rune(int runeValue) {
        super("Rune", '$', true);
        this.amount = runeValue;
        this.setDisplayString("💰");
    }

    /**
     * A list of allowable actions which can be performed with the rune item
     * @param owner the actor that owns the item
     * @return a list of allowable actions for the rune item
     */
    public ActionList allowableActions(Actor owner) {
        ActionList actions = new ActionList();

        actions.add(new ConsumeAction(this));

        return actions;
    }

    /**
     * Handles the consuming of the rune item, which increases the player's balance once consumed
     * @param target The actor who is consuming the item.
     */
    @Override
    public void consumedBy(Actor target) {
        this.target = target;
        target.addBalance(amount);
        target.removeItemFromInventory(this); // Remove item after used
    }

    @Override
    public String consumeToString(Actor target) {
        return String.format("Consume %s to add $%d into wallet", this,amount);
    }

}
