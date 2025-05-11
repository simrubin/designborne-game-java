package game.entity.enemy;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.actions.AttackAction;
import game.behaviour.AttackBehaviour;
import game.behaviour.WanderBehaviour;
import game.enums.ActorType;
import game.enums.BehaviourPriority;
import game.enums.Status;

import java.util.HashMap;
import java.util.Map;

public abstract class Enemy extends Actor {

    protected Map<Integer, Behaviour> behaviours = new HashMap<>();
    private int runeValue;

    /**
     * The constructor of the Actor class.
     *
     * @param name        the name of the Actor
     * @param displayChar the character that will represent the Actor in the display
     * @param hitPoints   the Actor's starting hit points
     * @param runeValue   the amount of runes the actor drops when felled
     */
    public Enemy(String name, char displayChar, int hitPoints, int runeValue) {
        super(name, displayChar, hitPoints);
        this.behaviours.put(BehaviourPriority.WANDER_BEHAVIOUR.getPriority(), new WanderBehaviour());
        this.behaviours.put(BehaviourPriority.ATTACK_BEHAVIOUR.getPriority(), new AttackBehaviour(ActorType.PLAYABLE));
        this.addCapability(Status.HOSTILE_TO_ENEMY);
        this.runeValue = runeValue;
    }

    /**
     * A getter for the amount of runes an actor drops upon death
     * @return the value of an actor's rune drops
     */
    public int getRuneValue() {
        return this.runeValue;
    }

    /**
     * At each turn, select a valid action to perform.
     *
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return the valid action that can be performed in that iteration or null if no valid action is found
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        for (Behaviour behaviour : behaviours.values()) {
            Action action = behaviour.getAction(this, map);
            if(action != null)
                return action;
        }

        return new DoNothingAction();
    }

    /**
     * The enemy can be attacked by any actor that has the HOSTILE_TO_ENEMY capability
     *
     * @param otherActor the Actor that might be performing attack
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = new ActionList();
        if(otherActor.hasCapability(Status.HOSTILE_TO_ENEMY)){
            actions.add(new AttackAction(this, direction));
        }
        return actions;
    }

    /**
     * Defines the intrinsic weapon of the hostile enemy.
     *
     * @return An intrinsic weapon representing the attack capabilities of the hostile enemy.
     */
    public abstract IntrinsicWeapon getIntrinsicWeapon();

    @Override
    public String unconscious(GameMap map) {
        return super.unconscious(map);
    }
}
