package game.entity;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttribute;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.displays.Display;

import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.displays.Menu;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.actions.AttackAction;
import game.actions.MonologueAction;
import game.actions.ResetAction;
import game.enums.Ability;
import game.enums.ActorType;
import game.enums.PlayerCondition;
import game.enums.Status;
import game.items.Droppable;
import game.rune.Rune;
import game.reset.Resettable;
import game.rune.DroppedRuneManager;
import game.utils.FancyMessage;
import game.weapon.GreatKnife;

import static game.enums.PlayerCondition.GIANT_HAMMER;
import static game.enums.PlayerCondition.GREAT_KNIFE;

/**
 * Class representing the Player.
 *
 * Created by:
 * @author Adrian Kristanto
 * Modified by: Shannon Jian Hong Chia
 *
 */
public class Player extends Actor implements Resettable, Droppable {

    /**
     * The players current Stamina
     */
    private int currentStamina;

    /**
     * The maximum stamina the player has
     */
    private int maxStamina;

    /**
     * The maximum HP the player has
     */
    private int maxHP;

    /**
     * The current HP the player has
     */
    private int currentHP;

    /**
     * Saves spawn location as respawn location
     */
    private Location respawnLocation;

    /**
     * Constructor.
     *
     * @param name        Name to call the player in the UI
     * @param displayChar Character to represent the player in the UI
     * @param hitPoints   Player's starting number of hitpoints
     * @param stamina     Player's starting stamina
     * @param spawnLocation Player's starting spawn location will be set at respawn location
     */
    public Player(String name, char displayChar, int hitPoints, int stamina, Location spawnLocation) {
        super(name, displayChar, hitPoints);
        this.addCapability(Status.HOSTILE_TO_ENEMY);
        this.addAttribute(BaseActorAttributes.STAMINA, new BaseActorAttribute(stamina)); // New stamina attribute
        this.addCapability(ActorType.PLAYABLE);
        this.addCapability(Ability.TRADE);

        this.currentStamina = getAttribute(BaseActorAttributes.STAMINA);
        this.maxStamina = getAttributeMaximum(BaseActorAttributes.STAMINA);
        this.currentHP = getAttribute(BaseActorAttributes.HEALTH);
        this.maxHP = getAttributeMaximum(BaseActorAttributes.HEALTH);

        this.respawnLocation = spawnLocation;
    }

    /**
     * Handles the player's turn, including multi-turn actions, stamina recovery, attempt revive.
     *
     * @param actions    Collection of possible Actions for the player.
     * @param lastAction The Action the player took last turn.
     * @param map        The map containing the player.
     * @param display    The I/O object to which messages may be written.
     * @return The action the player will take in this turn.
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {

        // Attempts revive by checking whether Death Inbound or no
        if(this.hasCapability(Status.DEATH_INBOUND)){
            return enterRevival(map);
        }

        // Triggers great knife flag if great knife is present in the inventory
        if(this.getItemInventory().contains("Great Knife")) {
            this.addCapability(GREAT_KNIFE);
        }
        else {
            this.removeCapability(GREAT_KNIFE);
        }

        // Triggers giant hammer flag if giant hammer is present in the inventory
        if(this.getItemInventory().contains("Giant Hammer")) {
            this.addCapability(GIANT_HAMMER);
        }
        else {
            this.removeCapability(GIANT_HAMMER);
        }


        // Handle multi-turn Actions
        if (lastAction.getNextAction() != null)
            return lastAction.getNextAction();

        recoverStamina();

        // Prints current balance
        new Display().println(String.format("Current Balance: $%d",this.getBalance()));

        // return/print the console menu
        Menu menu = new Menu(actions);
        return menu.showMenu(this, display);
    }


    /**
     * Specifies the allowable actions when another actor interacts with the player.
     * The player can attack any hostile actor.
     *
     * @param otherActor The Actor that might be performing an attack.
     * @param direction  The direction of the other Actor.
     * @param map        The current GameMap.
     * @return A list of allowable actions, including an AttackAction if the other actor is hostile.
     */
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = new ActionList();
        if(otherActor.hasCapability(Status.HOSTILE_TO_ENEMY)){
            // add intrinsic weapon
            actions.add(new AttackAction(this, direction));
        }

        return actions;
    }

    /**
     * Recovers the player's stamina by 1% of maximum stamina each turn.
     */
    public void recoverStamina() {
        int recoverAmount = maxStamina / 100; // 1% of max stamina
        this.modifyAttribute(BaseActorAttributes.STAMINA, ActorAttributeOperations.INCREASE, recoverAmount);
    }

    /**
     * Punch: 15 damage / 80% hit rate.
     */
    @Override
    public IntrinsicWeapon getIntrinsicWeapon(){
        return new IntrinsicWeapon(15, "Punch",80);
    }

    /**
     * Returns a string representation of the player, including their name, current hit points, and stamina.
     *
     * @return A string representation of the player.
     */
    @Override
    public String toString(){
        return name + " (" +
        super.toString() + " (" +
        this.getAttribute(BaseActorAttributes.STAMINA) + "/" +          // Print Stamina Attribute
        this.getAttributeMaximum(BaseActorAttributes.STAMINA) + ")" +
        ")";
    }

    /**
     * Revives the actor, moving them to the respawn location if it is vacant, healing to maximum health, and removing the
     * {@code Status.DEATH_INBOUND} capability.
     * @return message indicating result of revival
     */
    public String revive(){
        if(!respawnLocation.containsAnActor()){
            respawnLocation.map().moveActor(this,respawnLocation);
        }

        // revive
        this.heal(maxHP);
        this.removeCapability(Status.DEATH_INBOUND);
        return "The Abstracted One shall live to see another day";
    }

    /**
     * Initiates the revival process
     *
     * @param map on which the revival occurs
     * @return DoNothingAction
     */
    public Action enterRevival(GameMap map) {

        // Print Fancy message
        for (String line : FancyMessage.YOU_DIED.split("\n")) {
            new Display().println(line);
            try {
                Thread.sleep(200);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        // Perform all Resettable
        new ResetAction().execute(this,map);

        // Clear all dropped rune
        DroppedRuneManager.getInstance().clear();

        // Drop Rune
        dropItem(map.locationOf(this));

        // Reset balance
        this.deductBalance(getBalance());

        // Perform Revive
        this.revive();
        return new DoNothingAction();

    }

    /**
     * Resets the actor by healing them to their max health
     */
    @Override
    public void reset() {
        this.heal(maxHP);
    }

    /**
     * Upon being unconscious, will get a "DEATH_INBOUND" status
     *
     * @param actor the perpetrator
     * @param map where the actor fell unconscious
     * @return
     */
    public String unconscious(Actor actor, GameMap map) {
        this.addCapability(Status.DEATH_INBOUND);
        return "You shall not die here The Abstracted One";
    }

    /**
     * Drops Rune at the specified location.
     * @param location at which items are dropped
     */
    @Override
    public void dropItem(Location location) {
        Rune rune = new Rune(getBalance());
        location.addItem(rune);
        DroppedRuneManager.getInstance().register(rune,location);
//        DroppedRuneManager.getInstance().protect(rune);
    }
}
