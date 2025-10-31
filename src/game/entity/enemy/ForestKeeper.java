package game.entity.enemy;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.weather.WeatherControl;
import game.actions.RemoveActorAction;
import game.enums.Status;
import game.weather.Weather;
import game.items.Droppable;
import game.enums.ActorType;
import game.items.HealingVial;
import game.behaviour.FollowBehaviour;
import game.enums.BehaviourPriority;
import game.rune.Rune;
import game.reset.ResetManager;
import game.reset.Resettable;
import game.rune.DroppedRuneManager;


import java.util.Random;

/**
 * Created by:
 * @author Chan Jing Yi
 * Modified by: Shannon Jian Hong Chia
 *
 */

/**
 * The ForestKeeper class represents a type of actor in the game, specifically a hostile enemy character known as a Forest Keeper.
 * It extends the Actor class and defines behaviors and characteristics unique to the Forest Keeper.
 */
public class ForestKeeper extends Enemy implements Droppable, Resettable {

    private int healAmount = 10;

    public ForestKeeper(){
        super("Forest Keeper", '8', 125, 50);
        this.addCapability(ActorType.ENEMY_FOREST_KEEPER);
        ResetManager.getInstance().register(this);
        this.setDisplayString("🧙‍");
//        behaviours.put(BehaviourPriority.FOLLOW_BEHAVIOUR.getPriority(), new FollowBehaviour());
    }

    /**
     * Punch: 25 damage / 75% hit rate.
     */
    public IntrinsicWeapon getIntrinsicWeapon(){
        return new IntrinsicWeapon(25, "Hit",75);
    }

    /**
     * Handles the behavior of the Forest Keeper when it becomes unconscious.
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

        if (WeatherControl.getCurrentWeather() == Weather.RAINY){
            this.modifyAttribute(BaseActorAttributes.HEALTH, ActorAttributeOperations.INCREASE,healAmount);
        }

        if (this.hasCapability(Status.RESET_INBOUND)){
            return new RemoveActorAction();
        }

        return super.playTurn(actions, lastAction, map, display);
    }

    /**
     * Adds the Follow Behaviour as an allowable action by the entity
     *
     * @param otherActor the Actor that might be performing attack
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        if (otherActor.hasCapability(ActorType.PLAYABLE)){
            this.behaviours.put(BehaviourPriority.FOLLOW_BEHAVIOUR.getPriority(), new FollowBehaviour(otherActor));
        }
        return super.allowableActions(otherActor, direction, map);
    }

    /**
     * Resets the state of the entity by adding the {@code Status.RESET_INBOUND} capability.
     */
    @Override
    public void reset() {
        this.addCapability(Status.RESET_INBOUND);
    }
}
