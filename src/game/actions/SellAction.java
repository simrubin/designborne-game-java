package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.enums.ActorType;
import game.enums.TradeType;
import game.items.Tradeable;

/**
 * Created by:
 * @author Shannon Jian Hong Chia
 * Modified by:
 *
 */

/**
 * The SellAction class represents an action where one actor sells a tradeable item to another actor.
 * It extends the Action class.
 */
public class SellAction extends Action {

    /**
     * The item to be sold.
     */
    private Item item;

    /**
     * The selling price of the item.
     */
    private int price;

    /**
     * The tradeable item being sold.
     */
    private Tradeable tradeableItem;

    /**
     * The actor who is selling the item.
     */
    private Actor seller;

    /**
     * The String output to be printed after executing action
     */
    private String output;

    /**
     * The type of "Trade" that can occur if scam is to occur
     */
    private Enum<TradeType> tradeType;

    /**
     * Constructor
     *
     * @param item           The item to be sold.
     * @param price          The selling price of the item.
     * @param tradeable      The tradeable item being sold.
     * @param seller         The actor who is selling the item.
     */
    public SellAction(Item item, int price, Tradeable tradeable, Actor seller){
        this.item = item;
        this.price = price;
        this.tradeableItem = tradeable;
        this.seller = seller;
        this.output = "\nTransaction: \n";
        this.tradeType = TradeType.NON_SCAMMABLE; // Default all item cannot scam.
    }

    /**
     * Executes the sell action, handling the transaction between the seller and the buyer.
     *
     * @param actor The actor performing the sell action.
     * @param map   The GameMap where the action is being executed.
     * @return A message indicating the outcome of the sell action.
     */
    @Override
    public String execute(Actor actor, GameMap map) {

        boolean isScam = checkScamAndAffectedPrice();

        if (seller.hasCapability(ActorType.PLAYABLE)) {
            handlePlayerSellAction(isScam, actor);
        } else if (seller.hasCapability(ActorType.TRADER)){
            handleTraderSellAction(isScam, actor);
        }

        return output;
    }

    /**
     * Provides a description of the sell action for display in the menu.
     *
     * @param actor The actor performing the action.
     * @return A description of the sell action.
     */
    @Override
    public String menuDescription(Actor actor) {
        return seller + " sells " + tradeableItem.toString() + " for $" + price ;
    }

    /**
     * Checks if a scam will occur and if the price is affected.
     *
     * @return True if a scam will occur, false otherwise.
     */
    private boolean checkScamAndAffectedPrice() {
        boolean isScam = false; // Default flag that no scam will occur

        // Cycle through RNG, whether price is affected or not (True means some sort of scam gonna happen; False means safe)
        if (tradeableItem.isPriceAffected(seller)){
            Enum<TradeType> itemTradeType = tradeableItem.getTradeType(seller);

            // See the type of scam will occur with the respective item
            if (itemTradeType != TradeType.NON_SCAMMABLE){
                tradeType = itemTradeType;
                isScam = true;
            } else {
                price = tradeableItem.affectedPrice(seller);
                output += seller + " has made some last minute change to the price of " + tradeableItem.toString() + " to $" + price + "\n";
            }
        }

        return isScam;
    }

    /**
     * Handles the sell action when the seller is a player.
     *
     * @param isScam True if a scam will occur, false otherwise.
     * @param actor  The actor performing the action.
     */
    private void handlePlayerSellAction(boolean isScam, Actor actor){
        if (isScam){
            if (tradeType == TradeType.STEAL_ITEMS){
                actor.removeItemFromInventory(item);
                output += "Sike, unlucky! This item belongs to me now hehe\n";
            } else if (tradeType == TradeType.STEAL_RUNES){
                seller.deductBalance(price);
                output += "Sike, thanks for the runes, but the item is still under my possession hehe\n";
            }
        } else {
            actor.removeItemFromInventory(item);
            actor.addBalance(price);
            output += "Thank you for selling, $" + price + " have been deposited into your account!\n";
        }
    }

    /**
     * Handles the sell action when the seller is a trader.
     *
     * @param isScam True if a scam will occur, false otherwise.
     * @param actor  The actor performing the action.
     */
    private void handleTraderSellAction(boolean isScam, Actor actor){

        if (isScam) {
            if (tradeType == TradeType.STEAL_RUNES) {
                actor.deductBalance(price);
                output += "Sike, thanks for the money hehe\n";
            }
        } else if (actor.getBalance() > price) {
            actor.deductBalance(price);
            actor.addItemToInventory(tradeableItem.newItemInstance());
            output += "Thank you for your purchase, " + tradeableItem.toString() + " have been added to your inventory\n";
        } else {
            output += "Please come back when you have enough $$$$$\n";
        }
    }

}