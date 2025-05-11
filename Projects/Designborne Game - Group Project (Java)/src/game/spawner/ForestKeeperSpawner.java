package game.spawner;

import edu.monash.fit2099.engine.positions.Location;
import game.weather.WeatherControl;
import game.entity.enemy.ForestKeeper;
import game.weather.Weather;

import java.util.Random;

/**
 * Created by:
 * @author Jing Yi
 * Modified by:Shannon Jian Hong Chia
 *
 */

/**
 * The ForestKeeperSpawner class is responsible for spawning a ForestKeeper at a given location based on weather conditions.
 * It implements the Spawner interface.
 */
public class ForestKeeperSpawner implements Spawner {

    /**
     * The spawn probability during rainy weather.
     */
    private final float RAINY_SPAWN_PROBABILITY = 0.15f;

    /**
     * The spawn probability during sunny weather.
     */
    private final float SUNNY_SPAWN_PROBABILITY = 0.3f;

    /**
     * The current spawn probability based on the weather.
     */
    private float spawnProbability;

    /**
     * Spawns a ForestKeeper at the given location if certain conditions are met, considering the current weather.
     *
     * @param location The location where the ForestKeeper should be spawned.
     */
    @Override
    public void spawn(Location location) {
        Random random = new Random();

        if (!location.containsAnActor()) {

            if (WeatherControl.getCurrentWeather() == Weather.RAINY){
                spawnProbability = RAINY_SPAWN_PROBABILITY;
            } else if (WeatherControl.getCurrentWeather() == Weather.SUNNY) {
                spawnProbability = SUNNY_SPAWN_PROBABILITY;
            }

            if (random.nextFloat() <= spawnProbability){
                location.addActor(new ForestKeeper());
            }

        }
    }
}
