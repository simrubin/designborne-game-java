package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by:
 * @author Leo Zhang
 * Modified by:
 */

/**
 * The MonologueAction class handles all actions with actors that have monologues.
 */
public class MonologueAction extends Action {

    /**
     * The Actor which has monologue text
     */
    private Actor monologueActor;

    /**
     * An ArrayList of all the possible monologue options for a given actor
     */
    private ArrayList<String> monologueList;

    /**
     * Random number generator
     */
    private Random rand = new Random();

    /**
     * Provides the details of the actor delivering the monologue
     * @return The name of the monologuing actor as well as their health
     */
    @Override
    public String toString() {
        return monologueActor.toString();
    }

    /**
     * Constructor for the MonologueAction class
     * @param monologueActor The actor which has the ability to speak in monologues
     * @param monologueList A list of all the potential monologues
     */
    public MonologueAction(Actor monologueActor, ArrayList<String> monologueList) {
        this.monologueActor = monologueActor;
        this.monologueList = monologueList;
    }

    /**
     * Handles the execution of the actor delivering a monologue
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return A line of monologue delivered by the monologuing actor.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        int monologueIndex = rand.nextInt(monologueList.size());
        String actorMonologue = monologueList.get(monologueIndex);

        return this.monologueActor + ": \"" + actorMonologue + "\"";
    }

    /**
     * Adds the action to listen to the actor with monologues to the action menu
     * @param actor The actor performing the action.
     * @return A menu option giving the player an option to listen.
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " listens to " + this.monologueActor;
    }
}
