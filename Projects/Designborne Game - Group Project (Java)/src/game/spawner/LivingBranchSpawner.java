package game.spawner;

import edu.monash.fit2099.engine.positions.Location;
import game.entity.enemy.LivingBranch;
import game.spawner.Spawner;

import java.util.Random;

/**
 * Created by:
 * @author Simeon Rubin
 * Modified by:
 */

/**
 * The LivingBranchSpawner class is responsible for spawning a LivingBranch enemy at a given location based on a specified probability.
 * It implements the Spawner interface.
 */
public class LivingBranchSpawner implements Spawner {

  /**
   * Spawns a LivingBranch at the given location if certain conditions are met.
   *
   * @param location The location where the LivingBranch should be spawned.
   */
  public void spawn(Location location) {
    Random random = new Random();

    if (!location.containsAnActor()) {
      if (random.nextFloat() <= 0.9) {
        location.addActor(new LivingBranch());
      }
    }
  }
}
