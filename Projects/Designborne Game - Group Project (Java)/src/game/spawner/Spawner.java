package game.spawner;

import edu.monash.fit2099.engine.positions.Location;

/**
 * Created by:
 * @author Shannon Jian Hong Chia
 * Modified by:
 *
 */

/**
 * The Spawner interface defines the contract for objects responsible for spawning actors in a specific location.
 */

public interface Spawner {

    /**
     * Spawns an actor at the specified location.
     *
     * @param location The location where the actor should be spawned.
     */
    void spawn(Location location);
}
