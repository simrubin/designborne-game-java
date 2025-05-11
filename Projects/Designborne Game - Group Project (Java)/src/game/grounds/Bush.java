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
 * The Bush class represents a special type of ground that can spawn enemy actors based on the specified actor type.
 * It extends the Ground class and uses an EnemySpawner to create and spawn enemy actors within the bush.
 */

public class Bush extends Ground{

    private Spawner spawner;

    /**
     * The type of enemy to be spawn at the bush
     */
    private ActorType actorType;

    /**
     * Constructor for the bush class.
     *
     * @param actorType The type of enemy actor to spawn in the bush (e.g., ENEMY_WANDERING_UNDEAD or ENEMY_HOLLOW_SOLDIER).
     */
    public Bush(Spawner spawner) {
        super('m');
        this.spawner = spawner;
    }

    /**
     * Spawns an enemy actor within the bush if the location is empty (no existing actors).
     *
     * @param location The location within the bush where the enemy actor should be spawned.
     */

    /**
     * Performs an action on the location during each game tick, which includes attempting to spawn an enemy actor.
     *
     * @param location The location representing the bush.
     */
    public void tick(Location location) {
        super.tick(location);
        spawner.spawn(location);
    }
}