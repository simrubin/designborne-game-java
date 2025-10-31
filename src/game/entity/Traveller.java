package game.entity;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.items.Item;

import game.actions.MonologueAction;
import game.actions.SellAction;
import game.enums.Ability;
import game.enums.ActorType;
import game.enums.ItemCapability;
import game.enums.PlayerCondition;
import game.items.HealingVial;
import game.items.RefreshingFlask;
import game.items.Tradeable;
import game.weapon.Broadsword;
import game.weapon.GreatKnife;

import java.util.ArrayList;

/**
 * Created by:
 * @author Shannon Jian Hong Chia
 * Modified by:
 *
 */

/**
 * The Traveller class represents a non-moving actor in the game, denoted by the character 'ඞ'.
 * The traveller acts as a trader offering various items for sale to the player.
 * Players can interact with the traveller to buy and sell items, impacting their wallet balance.
 */

public class Traveller extends Actor {

    /**
     * Constructor for the Traveller class.
     * Initializes the traveller with the name "Traveller", the display character 'ඞ', and 1 hit point.
     * Initializes the traveller's inventory with various items and their corresponding prices.
     */
    public Traveller(){
        super("Traveller",'ඞ',1);
        this.addCapability(ActorType.TRADER);
        this.addCapability(Ability.TRADE);

        // Add any relevant item into Traders inventory, and set the price accordingly, as different trader may sell items at different price
        this.addItemToInventory(new HealingVial().setPrice(100));
        this.addItemToInventory(new RefreshingFlask().setPrice(75));
        this.addItemToInventory(new Broadsword().setPrice(250));
        this.addItemToInventory(new GreatKnife().setPrice(300));
    }

    /**
     * Overrides the playTurn method to make the traveller perform no actions during its turn.
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
     * @param seller The actor whose items are being considered for trade.
     * @return An ActionList containing sell actions for each tradeable item in the actor's inventory.
     */
    public ActionList getItems(Actor seller){
        ActionList actions = new ActionList();

        // Iterate through every possible tradeable item that can be sold in actors inventory
        for (int i = 0; i < seller.getItemInventory().size(); i++){
            Item item = seller.getItemInventory().get(i);

            if (item.hasCapability(ItemCapability.TRADEABLE)){
                Tradeable tradeableItem = (Tradeable) item; //Cast item to be tradeable
                int price = tradeableItem.getPrice();
                actions.add(new SellAction(item, price, tradeableItem, seller));
            }
        }
        return actions;
    }


    /**
     * Generates a list of allowable actions for buying and selling items between actors.
     *
     * @param otherActor The actor with whom interaction is possible.
     * @param direction  String representing the direction of the other actor.
     * @param map        The current GameMap.
     * @return An ActionList containing possible buying and selling actions.
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = super.allowableActions(otherActor, direction, map);

        if (otherActor.hasCapability(Ability.TRADE)) {
            actions.add(getItems(this));
            actions.add(getItems(otherActor));
        }

        actions.add(new MonologueAction(this, monologueList(otherActor))); // Adds the option to listen to monologue

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

        monologue.add("Of course, I will never give you up, valuable customer!");
        monologue.add("I promise I will never let you down with the quality of the items that I sell.");
        monologue.add("You can always find me here. I'm never gonna run around and desert you, dear customer!");
        monologue.add("I'm never gonna make you cry with unfair prices.");
        monologue.add("Trust is essential in this business. I promise I’m never gonna say goodbye to a valuable customer like you.");
        monologue.add("Don't worry, I’m never gonna tell a lie and hurt you.");

        if (player.hasCapability(PlayerCondition.GIANT_HAMMER)) {
            //If the player holds a Giant Hammer
            monologue.add("Ooh, that’s a fascinating weapon you got there. I will pay a good price for it. You wouldn't get this price from any other guy.");
        }

        if (player.hasCapability(PlayerCondition.ABXERVYER_DEFEATED)) {
            if (player.hasCapability(PlayerCondition.GIANT_HAMMER)) {
                //Once the player defeats Abxervyer & they still hold the giant hammer
                monologue.add("Congratulations on defeating the lord of this area. I noticed you still hold on to that hammer. Why don’t you sell it to me? We've known each other for so long. I can tell you probably don’t need that weapon any longer.");
            }
        }
        else {
            //If the player hasn’t defeated Abxervyer
            monologue.add("You know the rules of this world, and so do I. Each area is ruled by a lord. Defeat the lord of this area, Abxervyer, and you may proceed to the next area.");
        }

        return monologue;
    }

}
