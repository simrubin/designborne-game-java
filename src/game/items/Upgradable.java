package game.items;

import game.enums.UpgradeType;

/**
 * Created by:
 * @author Chan Jing Yi
 * Modified by:
 *
 */

/**
 * The Upgradable interface defines methods that should be implemented by Upgradable items and weapons
 * that can be upgraded by an actor.
 */

public interface Upgradable {
    /**
     * Gets the price to upgrade an item.
     *
     * @return The price to upgrade the item.
     */
    int getUpgradePrice();

    /**
     * Gets the upgrade type of the item, indicating the nature of the upgrade (e.g., once, multiple times).
     *
     * @return The upgrade type of the item.
     */
    Enum<UpgradeType> getUpgradeType();

    /**
     * Checks whether the item has been upgraded.
     *
     * @return True if the item has been upgraded, false otherwise.
     */
    boolean isUpgraded();

    /**
     * Performs the upgrade of the item, changing its properties or abilities.
     */
    void upgrade();

}
