package game.entity;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.items.Item;

import game.actions.MonologueAction;
import game.actions.UpgradeAction;
import game.enums.Ability;
import game.enums.ActorType;
import game.enums.ItemCapability;
import game.enums.PlayerCondition;
import game.items.Upgradable;

import java.util.ArrayList;

/**
 * Created by:
 * @author Chan Jing Yi
 * Modified by:
 *
 */

/**
 * The Blacksmith class represents a non-moving actor in the game, denoted by the character 'B'.
 * Players can interact with the blacksmith to upgrade their items and weapons, after paying some runes.
 */

public class Blacksmith extends Actor {
    /**
     * Constructor for the Blacksmith class.
     * Initializes the blacksmith with the name "Blacksmith", the display character 'B', and 1 hit point.
     */
    public Blacksmith(){
        super("Blacksmith",'B',1);
        this.addCapability(ActorType.TRADER);
        this.addCapability(Ability.UPGRADE);
    }

    /**
     * Overrides the playTurn method to make the blacksmith perform no actions during its turn.
     *
     * @param actions    Collection of possible Actions for this actor.
     * @param lastAction The Action this actor took last turn.
     * @param map        The map containing the actor.
     * @param display    The I/O object to which messages may be written.
     * @return A DoNothingAction indicating that the traveller performs no action during its turn.
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        return new DoNothingAction();
    }

    /**
     * Generates a list of items that can be traded by an actor.
     *
     * @param player The actor whose items are being considered for trade.
     * @return An ActionList containing sell actions for each tradeable item in the actor's inventory.
     */
    public ActionList getItems(Actor player){
        ActionList actions = new ActionList();

        // Iterate through every possible tradeable item that can be sold in actors inventory
        for (int i = 0; i < player.getItemInventory().size(); i++){
            Item item = player.getItemInventory().get(i);
//            Upgradable upgradableItem = (Upgradable) item; //Cast item to be tradeable

            if (item.hasCapability(ItemCapability.UPGRADABLE)){
                Upgradable upgradableItem = (Upgradable) item; //Cast item to be tradeable

                if (!upgradableItem.isUpgraded()){
                    int price = upgradableItem.getUpgradePrice();
                    actions.add(new UpgradeAction(item, price, player, upgradableItem));
                }

            }
        }
        return actions;
    }

    /**
     * Generates a list of allowable actions for buying and selling items between actors.
     *
     * @param actor the Actor that might be performing attack
     * @param direction  String representing the direction of the other actor.
     * @param map        The current GameMap.
     * @return An ActionList containing possible buying and selling actions.
     */
    @Override
    public ActionList allowableActions(Actor actor, String direction, GameMap map) {
        ActionList actions = super.allowableActions(actor, direction, map);

        if (actor.hasCapability(Ability.TRADE)) {
            actions.add(getItems(actor));
        }

        actions.add(new MonologueAction(this, monologueList(actor))); // Adds the option to listen to monologue

        return actions;
    }

    /**
     * An array list which contains all potential monologues from a given actor
     *
     * @param player The player who is talking to the actor
     * @return An array list of all potential monologues in string format
     */
    public ArrayList monologueList(Actor player) {
        ArrayList monologue = new ArrayList<String>();

        monologue.add("I used to be an adventurer like you, but then .... Nevermind, let’s get back to smithing.");
        monologue.add("It’s dangerous to go alone. Take my creation with you on your adventure!");
        monologue.add("Ah, it’s you. Let’s get back to make your weapons stronger.");

        if (player.hasCapability(PlayerCondition.ABXERVYER_DEFEATED)) {
            //If Abxervyer has been defeated
            monologue.add("Somebody once told me that a sacred tree rules the land beyond the ancient woods until this day.");
        }
        else {
            //If the player hasn’t defeated Abxervyer
            monologue.add("Beyond the burial ground, you’ll come across the ancient woods ruled by Abxervyer. Use my creation to slay them and proceed further!");
        }

        if (player.hasCapability(PlayerCondition.GREAT_KNIFE)) {
            //If the player holds a great knife
            monologue.add("Hey now, that’s a weapon from a foreign land that I have not seen for so long. I can upgrade it for you if you wish.");
        }

        return monologue;
    }

}

