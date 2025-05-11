package game.enums;

/**
 * The PlayerCondition enum is used in order to check if certain conditions
 * have been met by the player. This is used to trigger conditional events.
 *
 * Example: If the player has defeated Abxervyer, they will have the ABXERVYER_DEFEATED enum.
 */
public enum PlayerCondition {

    /**
     * Indicates that a player has defeated Abxervyer.
     */
    ABXERVYER_DEFEATED,

    /**
     * Indicates that a player has a great knife in their inventory.
     */
    GREAT_KNIFE,

    /**
     * Indicates that a player has a giant hammer in their inventory.
     */
    GIANT_HAMMER,
}
