package game.items;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.items.Item;
import game.actions.ConsumeAction;
import game.enums.ActorType;
import game.enums.TradeType;
import game.enums.ItemCapability;
import game.enums.UpgradeType;


/**
 * Created by:
 * @author Shannon Jian Hong Chia
 * Modified by:
 *
 */

/**
 * The RefreshingFlask class represents an item that can be used to increase an actor's stamina in the game.
 */
public class RefreshingFlask extends TradeableItem implements Consumable, Upgradable{
    private boolean isUpgraded = false; // Initialize the upgrade status

    /**
     * The amount of stamina to increase
     */
    private int amount;


    /**
     * Constructor for the RefreshingFlask class.
     * Initializes a refreshing flask item with the name "Refreshing Flask," the display character 'u', and sets it as a consumable item.
     */
    public RefreshingFlask() {
        super("Refreshing Flask", 'u', true,25);
        this.addCapability(ItemCapability.UPGRADABLE);
    }

    /**
     * Provides a list of allowable actions for the owner of the refreshing flask (actor).
     * If the flask has not been used yet, it allows the owner to perform an IncreaseStaminaAction using the flask.
     *
     * @param owner The actor who owns the refreshing flask.
     * @return A list of allowable actions, including the IncreaseStaminaAction if the flask is not yet used.
     */
    public ActionList allowableActions(Actor owner) {
        ActionList actions = new ActionList();

        actions.add(new ConsumeAction(this));

        return actions;
    }

    /**
     * Creates a new instance of the RefreshingFlask.
     *
     * @return A new instance of the RefreshingFlask.
     */
    @Override
    public Item newItemInstance(){
        return new RefreshingFlask();
    }

    /**
     * Determines whether the price of the RefreshingFlask is affected during trade.
     *
     * @param seller The actor selling the HealingVial.
     * @return true if the price is affected; otherwise, false.
     */
    @Override
    public boolean isPriceAffected(Actor seller){
        double probability = 0;

        if (seller.hasCapability(ActorType.TRADER)){
            probability = 0.1;
        } else if (seller.hasCapability(ActorType.PLAYABLE)){
            probability = 0.5;
        }

        return Math.random() < probability;
    }

    /**
     * Retrieves the affected price of the RefreshingFlask during trade.
     *
     * @param seller The actor selling the HealingVial.
     * @return The affected price.
     */
    @Override
    public int affectedPrice(Actor seller){
        double priceMultiplier = 1;

        if (seller.hasCapability(ActorType.TRADER)) {
            priceMultiplier = 0.8;
        }

        return (int) (getPrice()*priceMultiplier);
    }

    /**
     * Retrieves the trade type for the RefreshingFlask during trade.
     *
     * @param seller The actor selling the RefreshingFlask.
     * @return The trade type.
     */
    @Override
    public Enum<TradeType> getTradeType(Actor seller){
        Enum<TradeType> tradeType = super.getTradeType(seller);

        if(seller.hasCapability(ActorType.PLAYABLE)){
            tradeType = TradeType.STEAL_ITEMS;
        }
        return tradeType;
    }

    /**
     * Retrieves the price of upgrading a RefreshingFlask.
     *
     * @return The price.
     */
    @Override
    public int getUpgradePrice() {
        return 175;
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
     * Upgrades the Refreshing Flask.
     *
     * If the Refreshing Flask has already been upgraded, a message is printed indicating
     * that it is already upgraded. Otherwise, the Refreshing Flask is upgraded, and a message
     * is printed to confirm the upgrade.
     */
    public void upgrade() {
        if (this.isUpgraded()) {
            System.out.println("This Refreshing FLask has already been upgraded.");
        }

        // Check if the Refreshing Flask hasn't been upgraded yet
        if (!this.isUpgraded()) {
            // Update the amount after upgrade
            this.isUpgraded = true;
            System.out.println("Refreshing FLask has been upgraded!");
        }
    }

    /**
     * Consumes the Refreshing FLask to restore the stamina of the specified target actor.
     *
     * If the Refreshing FLask has been upgraded, it restores 100% of the target's maximum stamina.
     * Otherwise, it restores 20% of the target's maximum stamina.
     *
     * @param target The actor consuming the Refreshing FLask.
     */
    @Override
    public void consumedBy(Actor target) {
        // Check if the Healing Vial has been upgraded
        if (this.isUpgraded()) {
            // If upgraded, use the upgraded amount
            this.amount = (int) (1.0 * target.getAttributeMaximum(BaseActorAttributes.STAMINA));
        } else {
            // Otherwise, use the default amount
            this.amount = (int) (0.2 * target.getAttributeMaximum(BaseActorAttributes.STAMINA));
        }

        target.modifyAttributeMaximum(BaseActorAttributes.STAMINA, ActorAttributeOperations.INCREASE, amount);
        target.removeItemFromInventory(this); // Remove item after used
    }

    /**
     * Generates a string describing the consumption of the Refreshing FLask by the specified target actor.
     *
     * If the Refreshing FLask has been upgraded, the string specifies restoration of 100% of the target's maximum stamina.
     * Otherwise, it specifies restoration of 20% of the target's maximum stamina.
     *
     * @param target The actor consuming the Refreshing FLask.
     * @return A string describing the consumption action.
     */
    @Override
    public String consumeToString(Actor target) {
        // Check if the Refreshing FLask has been upgraded
        if (this.isUpgraded()) {
            // If upgraded, use the upgraded amount
            this.amount = (int) (1.0 * target.getAttributeMaximum(BaseActorAttributes.STAMINA));
        } else {
            // Otherwise, use the default amount
            this.amount = (int) (0.2 * target.getAttributeMaximum(BaseActorAttributes.STAMINA));
        }

        return String.format("Consume %s to restore %d amount of stamina", this, amount);
    }

}