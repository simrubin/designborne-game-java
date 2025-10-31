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
import game.items.HealingVial;
import game.items.OldKey;
import game.enums.ActorType;
import game.rune.Rune;
import game.reset.ResetManager;
import game.reset.Resettable;
import game.rune.DroppedRuneManager;

import java.util.Random;

public class WanderingUndead extends Enemy implements Droppable, Resettable {

    public WanderingUndead() {
        super("Wandering Undead", 't', 100, 50);
        ResetManager.getInstance().register(this);
        this.addCapability(ActorType.ENEMY_WANDERING_UNDEAD);
        this.setDisplayString("🧟");
    }

    /**
     * Punch: 30 damage / 50% hit rate.
     */
    @Override
    public IntrinsicWeapon getIntrinsicWeapon(){
        return new IntrinsicWeapon(30, "Punch",50);
    }

    /**
     * Handles the behavior of the Wandering Undead when it becomes unconscious.
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

        if (random.nextFloat() <= 0.25){
            location.addItem(new OldKey());
        }

        if (random.nextFloat() <= 0.20){
            location.addItem(new HealingVial());
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
