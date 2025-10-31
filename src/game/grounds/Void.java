package game.grounds;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.enums.Ability;
import game.enums.ActorType;
import game.utils.FancyMessage;
import game.entity.Player;

/**
 * Created by:
 * @author Shannon Jian Hong Chia
 * Modified by:
 *
 */

/**
 * The Void class represents a deep mystery hole that can be entered by any actor but results in death if entered.
 * It extends the Ground class and defines behavior for actors that enter it, specifically causing their death.
 */
public class Void extends Ground {

    /**
     * Constructor for the Void class.
     */
    public Void() {
        super('+');
    }

    /**
     * Determines whether an actor can enter the Void ground.
     * All actors are allowed to enter the Void ground.
     *
     * @param actor The actor attempting to enter the Void ground.
     * @return True, indicating that any actor can enter the Void ground.
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        return true;
    }

    /**
     * Handles the consequences of an actor entering the Void ground, causing their death.
     *
     * @param location The location within the game map where an actor has entered the Void ground.
     */
    @Override
    public void tick(Location location) {
        if (location.containsAnActor() && !location.getActor().hasCapability(Ability.VOID_IMMUNITY)) {
            Actor actor = location.getActor();
            new Display().println("\n" + actor + " has fallen into the void");

            // Kill the actor that entered the Void
            GameMap map = location.map();

            actor.hurt(actor.getAttributeMaximum(BaseActorAttributes.HEALTH));

            if(!actor.isConscious()){
                actor.unconscious(actor,location.map());
            }
            if (!actor.hasCapability(ActorType.PLAYABLE)){
                map.removeActor(actor);
            }
        }
    }
}