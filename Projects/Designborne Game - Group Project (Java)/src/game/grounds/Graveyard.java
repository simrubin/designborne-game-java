package game.grounds;

import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.enums.ActorType;
import game.spawner.Spawner;

/**
 * Created by:
 * @author Shannon Jian Hong Chia
 * Modified by:
 *
 */

/**
 * The Graveyard class represents a special type of ground that can spawn enemy actors based on the specified actor type.
 * It extends the Ground class and uses an EnemySpawner to create and spawn enemy actors within the graveyard.
 */

public class Graveyard extends Ground{

    private Spawner spawner;

    /**
     * The type of enemy to be spawn at the Graveyard
     */
    private ActorType actorType;

    /**
     * Constructor for the Graveyard class.
     *
     * @param actorType The type of enemy actor to spawn in the graveyard (e.g., ENEMY_WANDERING_UNDEAD or ENEMY_HOLLOW_SOLDIER).
     */
    public Graveyard(Spawner spawner) {
        super('n');
//        this.actorType = actorType;
//        this.enemySpawner = new EnemySpawner();
        this.spawner = spawner;
    }

    /**
     * Spawns an enemy actor within the graveyard if the location is empty (no existing actors).
     *
     * @param location The location within the graveyard where the enemy actor should be spawned.
     */

    /**
     * Performs an action on the location during each game tick, which includes attempting to spawn an enemy actor.
     *
     * @param location The location representing the graveyard.
     */
    public void tick(Location location) {
        super.tick(location);
//        spawnEnemy(location);
        spawner.spawn(location);
    }
}