package game.items;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.items.Item;
import game.actions.ConsumeAction;
import game.enums.ActorType;
import game.enums.ItemCapability;
import game.enums.UpgradeType;

/**
 * Created by:
 * @author Shannon Jian Hong Chia
 * Modified by:
 *
 */

/**
 * The HealingVial class represents a healing item that can be used by actors to restore their health.
 */
public class HealingVial extends TradeableItem implements Consumable, Upgradable{
    private boolean isUpgraded = false; // Initialize the upgrade status

    /**
     * The amount of health to increase
     */
    private int amount;


    /**
     * Constructor for the HealingVial class.
     */
    public HealingVial() {
        super("Healing Vial", 'a', true,35);
        addCapability(ItemCapability.HEALING);
        this.addCapability(ItemCapability.UPGRADABLE);
    }

    /**
     * Provides a list of allowable actions for the owner of the healing vial (actor).
     * If the vial has not been used yet, it allows the owner to perform a HealAction using the vial.
     *
     * @param owner The actor who owns the healing vial.
     * @return A list of allowable actions, including the HealAction if the vial is not yet used.
     */
    public ActionList allowableActions(Actor owner) {
        ActionList actions = new ActionList();

        actions.add(new ConsumeAction(this));

        return actions;
    }

    /**
     * Creates a new instance of the HealingVial.
     *
     * @return A new instance of the HealingVial.
     */
    @Override
    public Item newItemInstance(){
        return new HealingVial();
    }

    /**
     * Determines whether the price of the HealingVial is affected during trade.
     *
     * @param seller The actor selling the HealingVial.
     * @return ture if the price is affected; otherwise, false.
     */
    @Override
    public boolean isPriceAffected(Actor seller){
        double probability = 0;

        if (seller.hasCapability(ActorType.TRADER)){
            probability = 0.25;
        } else if (seller.hasCapability(ActorType.PLAYABLE)){
            probability = 0.1;
        }

        return Math.random() < probability;
    }

    /**
     * Retrieves the affected price of the HealingVial during trade.
     *
     * @param seller The actor selling the HealingVial.
     * @return The affected price.
     */
    @Override
    public int affectedPrice(Actor seller){
        double priceMultiplier = 1;

        if (seller.hasCapability(ActorType.TRADER)){
            priceMultiplier = 1.5;
        } else if (seller.hasCapability(ActorType.PLAYABLE)){
            priceMultiplier = 2;
        }

        return (int) (getPrice()*priceMultiplier);
    }

    /**
     * Retrieves the price of upgrading a HealingVial.
     *
     * @return The price.
     */
    @Override
    public int getUpgradePrice() {
        return 250;
    }

    /**
     * Retrieves the type of upgrade of HealingVial.
     *
     * @return The upgrade type.
     */
    @Override
    public Enum<UpgradeType> getUpgradeType() {
        return UpgradeType.UPGRADE_ONCE;
    }

    /**
     * Retrieves the status of the upgrade.
     *
     * @return The status of upgrade.
     */
    @Override
    public boolean isUpgraded() {
        return isUpgraded;
    }


    /**
     * Upgrades the Healing Vial.
     *
     * If the Healing Vial has already been upgraded, a message is printed indicating
     * that it is already upgraded. Otherwise, the Healing Vial is upgraded, and a message
     * is printed to confirm the upgrade.
     */
    public void upgrade() {
        if (this.isUpgraded()) {
            System.out.println("This Healing Vial has already been upgraded.");
        }

        // Check if the Healing Vial hasn't been upgraded yet
        if (!this.isUpgraded()) {
            // Update the amount after upgrade
            this.isUpgraded = true;
            System.out.println("Healing Vial has been upgraded!");
        }
    }


    /**
     * Consumes the Healing Vial to restore the health of the specified target actor.
     *
     * If the Healing Vial has been upgraded, it restores 80% of the target's maximum health.
     * Otherwise, it restores 10% of the target's maximum health.
     *
     * @param target The actor consuming the healing vial.
     */
    @Override
    public void consumedBy(Actor target) {
        // Check if the Healing Vial has been upgraded
        if (this.isUpgraded()) {
            // If upgraded, use the upgraded amount
            this.amount = (int) (0.8 * target.getAttributeMaximum(BaseActorAttributes.HEALTH));
        } else {
            // Otherwise, use the default amount
            this.amount = (int) (0.1 * target.getAttributeMaximum(BaseActorAttributes.HEALTH));
        }

        target.modifyAttribute(BaseActorAttributes.HEALTH, ActorAttributeOperations.INCREASE, amount);
        target.removeItemFromInventory(this); // Remove item after used
    }

    /**
     * Generates a string describing the consumption of the Healing Vial by the specified target actor.
     *
     * If the Healing Vial has been upgraded, the string specifies restoration of 80% of the target's maximum health.
     * Otherwise, it specifies restoration of 10% of the target's maximum health.
     *
     * @param target The actor consuming the healing vial.
     * @return A string describing the consumption action.
     */
    @Override
    public String consumeToString(Actor target) {

        // Check if the Healing Vial has been upgraded
        if (this.isUpgraded()) {
            // If upgraded, use the upgraded amount
            this.amount = (int) (0.8 * target.getAttributeMaximum(BaseActorAttributes.HEALTH));
        } else {
            // Otherwise, use the default amount
            this.amount = (int) (0.1 * target.getAttributeMaximum(BaseActorAttributes.HEALTH));
        }

        return String.format("Consume %s to restore %d amount of HP", this, amount);
    }

}