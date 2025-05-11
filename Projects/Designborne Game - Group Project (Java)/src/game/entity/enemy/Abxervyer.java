package game.entity.enemy;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.enums.*;
import game.weather.WeatherControl;
import game.behaviour.FollowBehaviour;
import game.grounds.Gate;
import game.reset.ResetManager;
import game.reset.Resettable;
import game.rune.DroppedRuneManager;
import game.utils.FancyMessage;
import game.utils.Maps;
import java.util.ArrayList;
import java.util.Arrays;
import game.items.Droppable;
import game.rune.Rune;

/**
 * Created by:
 * @author Shannon Jian Hong Chia
 * Modified by:
 *
 */

/**
 * The Abxervyer class represents a boss enemy named Abxervyer in the game.
 * It extends the Enemy class and introduces specific behaviors and characteristics.
 */
public class Abxervyer extends Enemy implements Droppable, Resettable {

    /**
     * WeatherControl instance that allows the modification of update in the Forest
     */
    private WeatherControl weatherControl;

    /**
     * Destination instance, that specifies where the gate should lead to after the boss die
     */
    private ArrayList<GameMap> destinations;

    /**
     * Constructor for the Abxervyer class.
     *
     * @param weatherControl The weather control object used for weather updates.
     * @param destinations    The destination game map for the new location after the boss die.
     */
    public Abxervyer(WeatherControl weatherControl, ArrayList<GameMap> destinations){
        super("Abxervyer",'Y',2000, 5000);
        this.addCapability(ActorType.ENEMY_BOSS);
        this.addCapability(Ability.VOID_IMMUNITY);
        ResetManager.getInstance().register(this);

//        behaviours.put(BehaviourPriority.FOLLOW_BEHAVIOUR.getPriority(), new FollowBehaviour());
        this.weatherControl = weatherControl;
        this.destinations = destinations;
    }

    /**
     * Retrieves the intrinsic weapon of Abxervyer.
     *
     * @return An intrinsic weapon representing the attack capabilities of Abxervyer.
     */
    @Override
    public IntrinsicWeapon getIntrinsicWeapon(){
        return new IntrinsicWeapon(80, "Kadoosh",25);
    }

    /**
     * Defines the behavior of Abxervyer during its turn, including weather updates.
     *
     * @param actions    Collection of possible actions for Abxervyer.
     * @param lastAction The Action Abxervyer took last turn.
     * @param map        The game map containing Abxervyer.
     * @param display    The I/O object to which messages may be written.
     * @return The valid action that Abxervyer can perform in the current turn.
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        weatherControl.updateWeather();
        new Display().println("Forecasted weather: " + WeatherControl.getCurrentWeather());

        if (this.hasCapability(Status.RESET_INBOUND)){
            this.modifyAttribute(BaseActorAttributes.HEALTH, ActorAttributeOperations.INCREASE,2000);
            return new DoNothingAction();
        }

        return super.playTurn(actions, lastAction, map, display);
    }

    /**
     * Handles dropping of runes when the Abxervyer is slain
     * @param location The location where the runes are dropped
     */
    @Override
    public void dropItem(Location location) {
        Rune rune = new Rune(getRuneValue());
        location.addItem(rune);
        DroppedRuneManager.getInstance().register(rune,location);
    }

    /**
     * Handles the behavior of Abxervyer when it becomes unconscious.
     * It creates a Gate object at its location, leading to the specified destination.
     *
     * @param actor The actor (Abxervyer) that is unconscious.
     * @param map   The game map where Abxervyer is located.
     * @return A message indicating that Abxervyer is unconscious.
     */
    @Override
    public String unconscious(Actor actor, GameMap map) {
        Location location = map.locationOf(this);
        actor.addCapability(PlayerCondition.ABXERVYER_DEFEATED); // Adds flag for conditional dialogue
        location.setGround(new Gate(destinations,new ArrayList<>(
            Arrays.asList(Maps.ancientWoods,Maps.overgrownSanctuary)),19,3));
        for (String line : FancyMessage.BOSS_DEFEATED.split("\n")) {
            new Display().println(line);
            try {
                Thread.sleep(200);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        return super.unconscious(actor, map);
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
