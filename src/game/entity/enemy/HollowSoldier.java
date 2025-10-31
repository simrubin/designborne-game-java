package game.entity.enemy;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.actions.RemoveActorAction;
import game.enums.Status;
import game.items.Droppable;
import game.enums.ActorType;
import game.items.HealingVial;
import game.items.RefreshingFlask;
import game.rune.Rune;
import game.reset.ResetManager;
import game.reset.Resettable;
import game.rune.DroppedRuneManager;

import java.util.Random;

/**
 * Created by:
 * @author Shannon Jian Hong Chia
 * Modified by:
 *
 */

/**
 * The HollowSoldier class represents a type of actor in the game, specifically a hostile enemy character known as a Hollow Soldier.
 * It extends the Actor class and defines behaviors and characteristics unique to the Hollow Soldier.
 */
public class HollowSoldier extends Enemy implements Droppable, Resettable {

    public HollowSoldier(){
        super("Hollow Soldier", '&', 200, 100);
        ResetManager.getInstance().register(this);
        this.addCapability(ActorType.ENEMY_HOLLOW_SOLDIER);
        this.setDisplayString("💀");
    }

    /**
     * Punch: 50 damage / 50% hit rate.
     */
    public IntrinsicWeapon getIntrinsicWeapon(){
        return new IntrinsicWeapon(50, "Punch",50);
    }

    /**
     * Handles the behavior of the Hollow Soldier when it becomes unconscious.
     *
     * @param actor The actor that is unconscious.
     * @param map   The GameMap where the actor is located.
     * @return A message indicating that the actor is unconscious.
     */
    @Override
    public String unconscious(Actor actor, GameMap map) {
        dropItem(map.locationOf(actor));
        return super.unconscious(actor, map);
    }

    /**
     * Drops items at the specified location based on a random chance.
     * @param location at which items are dropped
     */
    @Override
    public void dropItem(Location location) {
        Random random = new Random();

        if (random.nextFloat() <= 0.20){
            location.addItem(new HealingVial());
        }

        if (random.nextFloat() <= 0.30){
            location.addItem(new RefreshingFlask());
        }

        Rune rune = new Rune(getRuneValue());
        location.addItem(rune);
        DroppedRuneManager.getInstance().register(rune,location);
    }

    /**
     * At each turn, select a valid action to perform.
     *
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        if (this.hasCapability(Status.RESET_INBOUND)){
            return new RemoveActorAction();
        }
        return super.playTurn(actions, lastAction, map, display);
    }

    /**
     * Resets the state of the entity by adding the {@code Status.RESET_INBOUND} capability.
     */
    @Override
    public void reset() {
        this.addCapability(Status.RESET_INBOUND);
    }
}
