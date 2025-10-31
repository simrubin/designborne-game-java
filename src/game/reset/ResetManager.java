package game.reset;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by:
 * @author Shannon Jian Hong Chia
 * Modified by:
 *
 */

/**
 * The {@code ResetManager} class is a singleton manager responsible for handling objects that implement the {@code Resettable} interface.
 * It allows the registration, unregistration, and execution of the reset operation for all registered resettable instances.
 */

public class ResetManager {
    private final List<Resettable> resettables;
    private static ResetManager resetManagerInstance;

    /**
     * Constructor for Reset Manager
     */
    private ResetManager(){
        this.resettables = new ArrayList<>();
    }

    /**
     * Getter for singleton instance of ResetManager
     * @return the singleton ResetManager
     */
    public static ResetManager getInstance(){
        if(resetManagerInstance == null){
            resetManagerInstance = new ResetManager();
        }
        return resetManagerInstance;
    }

    /**
     * Register a resettable instance
     * @param resettable instance to be registered
     */
    public void register(Resettable resettable){
        this.resettables.add(resettable);
    }

    /**
     * Unregister a resettable instance
     * @param resettable instance to be unregistered
     */
    public void unregister(Object resettable){
        this.resettables.remove(resettable);
    }


    /**
     * Execute reset operation for all registered instances
     */
    public void run(){
        for(Resettable resettable:this.resettables){
            resettable.reset();
        }
    }
}
