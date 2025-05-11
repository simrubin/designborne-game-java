package game.spawner;

import edu.monash.fit2099.engine.positions.Location;
import game.entity.enemy.EldentreeGuardian;
import game.entity.enemy.HollowSoldier;
import game.spawner.Spawner;

import java.util.Random;


/**
 * Created by:
 * @author Simeon Rubin
 * Modified by:
 */

/**
 * The EldentreeGuardianSpawner class is responsible for spawning an EldentreeGuardian at a given location based on a specified probability.
 * It implements the Spawner interface.
 */
public class EldentreeGuardianSpawner implements Spawner {

  /**
   * Spawns an EldentreeGuardian at the given location if certain conditions are met.
   *
   * @param location The location where the EldentreeGuardian should be spawned.
   */
  public void spawn(Location location) {
    Random random = new Random();

    if (!location.containsAnActor()) {
      if (random.nextFloat() <= 0.2) {
        location.addActor(new EldentreeGuardian());
      }
    }
  }
}
