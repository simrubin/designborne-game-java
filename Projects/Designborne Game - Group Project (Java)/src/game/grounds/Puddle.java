package game.grounds;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.DrinkPuddleAction;
import game.enums.ActorType;
import game.enums.TerrainType;
import game.items.Consumable;

/** Created by:
 * @author Leo Zhang
 * Modified by:
 */

/**
 * The puddle class represents a ground tile which may be interacted with by the player
 */
public class Puddle extends Ground implements Consumable {

    /**
     * The targeted actor to increase their health
     */
    private Actor target;

    /**
     * The amount of stamina to increase
     */
    private int staminaAmount;

    /**
     * A constructor for the puddle class
     */
    public Puddle() {
        super('~');
        addCapability(TerrainType.CONSUMABLE);
    }

    /**
     * A list of allowable actions which may be undertaken by a player who is on the puddle tile
     * @param actor the Actor acting
     * @param location the current Location
     * @param direction the direction of the Ground from the Actor
     * @return a list of allowable actions the player may take in relation to the puddle
     */
    @Override
    public ActionList allowableActions(Actor actor, Location location, String direction) {
        ActionList actions = new ActionList();

        if (actor.hasCapability(ActorType.PLAYABLE)) {
            if (location.containsAnActor()) {
                actions.add(new DrinkPuddleAction(this));
            }
        }
        return actions;
    }

    /**
     * Handles the expected behaviour when a puddle is drank from by the player, which is to restore 1 health and 1% maximum stamina
     * @param target The actor who is consuming the item.
     */
    @Override
    public void consumedBy(Actor target) {
        this.target = target;
        target.modifyAttribute(BaseActorAttributes.HEALTH, ActorAttributeOperations.INCREASE, 1);
        this.staminaAmount = (int) (0.01 * target.getAttributeMaximum(BaseActorAttributes.STAMINA));
        target.modifyAttribute(BaseActorAttributes.STAMINA, ActorAttributeOperations.INCREASE, staminaAmount);
    }

    @Override
    public String consumeToString(Actor target) {
        return String.format("Consume %s to restore 1 HP and 1%% maximum stamina", this);
    }
}
