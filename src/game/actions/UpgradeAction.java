package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.enums.ActorType;
import game.enums.UpgradeType;
import game.items.Upgradable;

/**
 * Created by:
 * @author Chan Jing Yi
 * Modified by:
 *
 */

/**
 * The UpgradeAction class represents an action where one actor upgrades an upgradable item for another actor.
 * It extends the Action class.
 */

public class UpgradeAction extends Action{

    /**
     * The item to be upgraded.
     */
    private Item item;

    /**
     * The price of upgrade for the item.
     */
    private int upgradePrice;

    /**
     * The upgradable item being upgraded.
     */
    private Upgradable upgradableItem;

    /**
     * The actor who is upgrading the item.
     */
    private Actor upgrader;

    /**
     * The String output to be printed after executing action
     */
    private String output;

    /**
     * The type of "Upgrade" that can occur according to the nature of the item
     */
    private Enum<UpgradeType> upgradeType;


    /**
     * Constructor
     *
     * @param item       The item to be upgraded.
     * @param upgradePrice      The upgrade price of the item.
     * @param upgradable The upgradable item being upgraded.
     * @param upgrader   The actor who is upgrading the item.
     */
    public UpgradeAction(Item item, int upgradePrice, Actor upgrader, Upgradable upgradable) {//
        this.item = item;
        this.upgradePrice = upgradePrice;
        this.upgradableItem = upgradable;
        this.upgrader = upgrader;
        //this.upgradeType = upgradeType; // Set upgradeType
        this.output = "\nTransaction: \n";
    }


    /**
     * Executes the upgrade action, handling the transaction between the upgrader and the buyer.
     *
     * @param actor The actor performing the sell action.
     * @param map   The GameMap where the action is being executed.
     * @return A message indicating the outcome of the sell action.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        if (actor.hasCapability(ActorType.PLAYABLE)) {
            handleTraderUpgradeAction(actor);
        }
        return output;
    }

    /**
     * Provides a description of the upgrade action for display in the menu.
     *
     * @param actor The actor performing the action.
     * @return A description of the upgrade action.
     */
    @Override
    public String menuDescription(Actor actor) {
        return upgrader + " upgrades " + upgradableItem.toString() + " for $" + upgradePrice;
    }

    /**
     * Handles the upgrade action for a trader, based on the upgrade type and available balance of the actor.
     *
     * @param actor The actor performing the upgrade action.
     */
    private void handleTraderUpgradeAction(Actor actor) {

        // Get upgrade type
        Enum<UpgradeType> upgradeType = upgradableItem.getUpgradeType();

        // Check if the actor has enough balance for the upgrade
        if (actor.getBalance() >= upgradePrice) {
            // Perform upgrade based on the upgrade type
            if (upgradeType == UpgradeType.UPGRADE_MULTIPLE) {
                actor.deductBalance(upgradePrice);
                upgradableItem.upgrade(); // Perform the upgrade
                output += "Thank you for letting me upgrade your " + upgradableItem.toString() + ", come back again if you want more improvements ;)\n";
            } else if (upgradeType == UpgradeType.UPGRADE_ONCE && !upgradableItem.isUpgraded()) {
                actor.deductBalance(upgradePrice);
                upgradableItem.upgrade(); // Perform the upgrade
                output += "Thank you for letting me upgrade your " + upgradableItem.toString() + "\n";
            } else {
                output += "Please come back when you have enough money for upgrades\n";
            }
        } else {
            output += "You don't have enough money for the upgrade.\n";
        }
    }

}
