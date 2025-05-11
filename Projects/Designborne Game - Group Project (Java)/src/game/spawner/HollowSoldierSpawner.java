package game.spawner;

import edu.monash.fit2099.engine.positions.Location;
import game.entity.enemy.HollowSoldier;
import game.spawner.Spawner;

import java.util.Random;

/**
 * Created by:
 * @author Shannon Jian Hong Chia
 * Modified by:
 *
 */

/**
 * The HollowSoldier class is responsible for spawning a HollowSoldier at a given location based on a specified probability.
 * It implements the Spawner interface.
 */
public class HollowSoldierSpawner implements Spawner {

    /**
     * Spawns a HollowSoldier at the given location if certain conditions are met.
     *
     * @param location The location where the HollowSoldier should be spawned.
     */
    public void spawn(Location location) {
        Random random = new Random();

        if(!location.containsAnActor()){
            if (random.nextFloat() <= 0.1) {
                location.addActor(new HollowSoldier());
            }
        }
    }

}
