package game.grounds;

import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.enums.ActorType;
import game.spawner.Spawner;

/**
 * Created by:
 * @author Chan Jing Yi
 * Modified by:
 *
 */

/**
 * The EmptyHut class represents a special type of ground that can spawn enemy actors based on the specified actor type.
 * It extends the Ground class and uses an EnemySpawner to create and spawn enemy actors within the empty hut.
 */

public class EmptyHut extends Ground{

    private Spawner spawner;

    /**
     * The type of enemy to be spawn at the EmptyHut
     */
    private ActorType actorType;

    /**
     * Constructor for the EmptyHut class.
     *
     * @param actorType The type of enemy actor to spawn in the empty hut (e.g., ENEMY_FOREST_KEEPER).
     */
    public EmptyHut(Spawner spawner) {
        super('h');
        this.spawner = spawner;
    }

    /**
     * Spawns an enemy actor within the empty hut if the location is empty (no existing actors).
     *
     * @param location The location within the empty hut where the enemy actor should be spawned.
     */

    /**
     * Performs an action on the location during each game tick, which includes attempting to spawn an enemy actor.
     *
     * @param location The location representing the empty hut.
     */
    public void tick(Location location) {
        super.tick(location);
        spawner.spawn(location);
    }
}