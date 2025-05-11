package game.rune;

import edu.monash.fit2099.engine.positions.Location;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by:
 * @author Shannon Jian Hong Chia
 * Modified by:
 *
 */

/**
 * The DroppedRuneManager class manages the dropped runes in a game environment.
 * It provides methods to register, unregister, remove, and clear dropped runes.
 * The class follows the Singleton pattern to ensure a single instance is used globally.
 */

public class DroppedRuneManager {

    /**
     * A map to store the association between runes and their corresponding locations.
     */
    private final Map<Rune, Location> droppedRunes;

    /**
     * The singleton instance of the DroppedRuneManager.
     */
    private static DroppedRuneManager droppedRuneManagerInstance;

    /**
     * Private constructor to create a new DroppedRuneManager and initialize the map.
     */
    public DroppedRuneManager(){
        droppedRunes = new HashMap<>();
    }

    /**
     * Returns the singleton instance of the DroppedRuneManager.
     *
     * @return The DroppedRuneManager instance.
     */
    public static DroppedRuneManager getInstance(){
        if(droppedRuneManagerInstance == null){
            droppedRuneManagerInstance = new DroppedRuneManager();
        }
        return droppedRuneManagerInstance;
    }

    /**
     * Registers a rune with its corresponding location.
     *
     * @param rune     The rune to be registered.
     * @param location The location where the rune is dropped.
     */
    public void register(Rune rune, Location location){
        this.droppedRunes.put(rune, location);
    }

    /**
     * Unregisters a rune, removing it from the manager.
     *
     * @param rune The rune to be unregistered.
     */
    public void unregister(Rune rune){
        this.droppedRunes.remove(rune);
    }

    /**
     * Removes a rune from both the location and the manager.
     *
     * @param rune The rune to be removed.
     */
    public void remove(Rune rune){
        droppedRunes.get(rune).removeItem(rune);
        unregister(rune);
    }

    /**
     * Clears all dropped runes from their respective locations.
     */
    public void clear(){
        droppedRunes.forEach((rune, location) -> location.removeItem(rune));
//        droppedRunes.clear();
    }
}
