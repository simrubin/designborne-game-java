package game.spawner;

import edu.monash.fit2099.engine.positions.Location;
import game.entity.enemy.HollowSoldier;
import game.entity.enemy.WanderingUndead;
import game.spawner.Spawner;

import java.util.Random;

/**
 * Created by:
 * @author Shannon Jian Hong Chia
 * Modified by:
 *
 */

/**
 * The WanderingUndeadSpawner class is responsible for spawning a WanderingUndead at a given location based on a specified probability.
 * It implements the Spawner interface.
 */

public class WanderingUndeadSpawner implements Spawner {

    /**
     * Spawns a WanderingUndead at the given location if certain conditions are met.
     *
     * @param location The location where the WanderingUndead should be spawned.
     */
    @Override
    public void spawn(Location location) {
        Random random = new Random();

        if (!location.containsAnActor()) {
            if (random.nextFloat() <= 0.25) {
                location.addActor(new WanderingUndead());
            }
        }
    }
}
