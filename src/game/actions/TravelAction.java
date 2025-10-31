package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.utils.Maps;

/**
 * Created by:
 * @author Shannon Jian Hong Chia
 * Modified by:
 * @author Simeon Rubin
 *
 * The TravelAction class represents an action where an actor can travel from one GameMap to another.
 * It allows one actor to move to a different GameMap at specified coordinates.
 */
public class TravelAction extends Action {

    /**
     * The GameMap that is going to be travelled to
     */
    private GameMap to;
    /**
     * The name of the GameMap that is going to be travelled to
     */
    private String mapName;
    /**
     * The X coordinate of the new location to be teleported to
     */
    private int xCoordinate;

    /**
     * The Y coordinate of the new location to be teleported to
     */
    private int yCoordinate;

    /**
     * Constructor for the TravelAction class.
     *
     * @param to           The GameMap the actor is traveling to.
     * @param xCoordinate  The x-coordinate on the destination GameMap.
     * @param yCoordinate  The y-coordinate on the destination GameMap.
     */
    public TravelAction(GameMap to, String mapName, int xCoordinate, int yCoordinate) {
        super();
        this.to = to;
        this.mapName = mapName;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    /**
     * Executes the TravelAction, moving the actor from one GameMap to another at the specified coordinates.
     *
     * @param actor The actor performing the travel action.
     * @param map   The GameMap where the action is being executed.
     * @return A message indicating the actor's teleportation to the new location.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        // Perform travel action
        map.moveActor(actor, to.at(xCoordinate, yCoordinate));
        return actor + " has been teleported to " + to.locationOf(actor) + " at the new location";
    }

    /**
     * Provides a description of the travel action for display in the menu.
     *
     * @param actor The actor performing the action.
     * @return A description of the travel action.
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " travels to " + mapName;
    }
}