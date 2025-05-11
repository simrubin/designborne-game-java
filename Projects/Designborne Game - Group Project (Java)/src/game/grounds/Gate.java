package game.grounds;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.RemoveActorAction;
import game.actions.TravelAction;
import game.actions.UnlockGateAction;
import game.enums.ActorType;
import game.enums.ItemCapability;
import game.enums.Status;
import game.enums.TerrainType;
import game.reset.ResetManager;
import game.reset.Resettable;
import java.util.ArrayList;

/**
 * The Gate class represents a special ground type in the game, which acts as a gate to connect two different GameMaps.
 * It extends the Ground class and provides the functionality for locking and unlocking the gate, as well as allowing actors to enter it.
 *
 * Created by:
 * @author Shannon Jian Hong Chia
 * Modified by:
 * @author Simeon Rubin
 */
public class Gate extends Ground implements Resettable {

    /**
     * The destinations at the end of the gate for a single destination gate
     */
    private ArrayList<GameMap> destinations;

    /**
     * The destinations at the end of the gate for a multi-destination gate
     */
    private GameMap destination;

    /**
     * The names of the destinations
     */
    private ArrayList<String> mapNames;

    /**
     * The name of the destination
     */
    private String mapName;

    /**
     * The x-Coordinate of the new destination
     */
    private int xCoordinate;

    /**
     * The y-Coordinate of the new destination
     */
    private int yCoordinate;

    /**
     * Constructor for the Gate class.
     *
     * @param destination The GameMap where the gate leads to.
     * @param mapName The name of the map.
     * @param xCoordinate The x-coordinate on the destination GameMap.
     * @param yCoordinate The y-coordinate on the destination GameMap.
     */
    public Gate(GameMap destination, String mapName, int xCoordinate, int yCoordinate) {
        super('=');
        addCapability(TerrainType.LOCKED);
        this.destination = destination;
        this.mapName = mapName;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    /**
     * Constructor for the Gate class when there are multiple destinations.
     *
     * @param destinations The list of GameMaps where the gate leads to.
     * @param mapNames The list of map names.
     * @param xCoordinate The x-coordinate on the destination GameMaps.
     * @param yCoordinate The y-coordinate on the destination GameMaps.
     */
    public Gate(ArrayList<GameMap> destinations, ArrayList<String> mapNames, int xCoordinate, int yCoordinate) {
        super('=');
        addCapability(TerrainType.LOCKED);
        this.destinations = destinations;
        this.mapNames = mapNames;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        ResetManager.getInstance().register(this);
    }

    /**
     * Checks if an actor can enter the gate.
     *
     * @param actor The actor attempting to enter the gate.
     * @return True if the gate is unlocked, allowing any actor to enter; otherwise, only actors with BLOCKED capability can enter.
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        // All actors can enter the gate if it's unlocked
        if (!this.hasCapability(TerrainType.LOCKED)) {
            return true;
        }
        return actor.hasCapability(TerrainType.LOCKED);
    }

    /**
     * Provides a list of allowable actions for an actor interacting with the gate.
     *
     * @param actor     The actor interacting with the gate.
     * @param location  The location of the gate.
     * @param direction String representing the direction of the actor.
     * @return A list of allowable actions, including unlocking the gate and traveling through it.
     */
    @Override
    public ActionList allowableActions(Actor actor, Location location, String direction) {
        ActionList actions = new ActionList();

        if (actor.hasCapability(ActorType.PLAYABLE)) {
            if (location.getGround().hasCapability(TerrainType.LOCKED)) {
                actions.add(new UnlockGateAction(ItemCapability.UNLOCKABLE, location));
            } else {
                if (destinations == null) {
                    actions.add(new TravelAction(destination, mapName, xCoordinate, yCoordinate));
                } else {
                    for (int i = 0; i < destinations.size(); i++) {
                        actions.add(new TravelAction(destinations.get(i), mapNames.get(i), xCoordinate, yCoordinate));
                    }
                }
            }
        }
        return actions;
    }

    /**
     * Resets the state of the gate by adding the {@code Status.RESET_INBOUND} capability.
     */
    @Override
    public void reset() {
        this.addCapability(Status.RESET_INBOUND);
    }

    /**
     * Lock the gate if there's a RESET INBOUND.
     *
     * @param location The location of the Ground.
     */
    @Override
    public void tick(Location location) {
        if (this.hasCapability(Status.RESET_INBOUND)) {
            location.getGround().addCapability(TerrainType.LOCKED);
            this.removeCapability(Status.RESET_INBOUND);
        }
        super.tick(location);
    }
}
