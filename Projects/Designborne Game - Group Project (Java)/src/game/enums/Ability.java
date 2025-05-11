package game.enums;

/**
 * The Ability enum is used to represent various abilities that entities can possess in the game.
 * These abilities can be attached to an entity to define its unique capabilities.
 *
 * Example #1: If the player has the ability to jump over walls, you can attach Ability.WALL_JUMP to the player class.
 */
public enum Ability {

    /**
     * Represents an entity with the ability to trade with other entities.
     */
    TRADE,

    /**
     * Represents an entity with the ability to upgrade certain aspects or items.
     */
    UPGRADE,

    /**
     * Represents an entity with void immunity, making it immune to the effects of the void.
     */
    VOID_IMMUNITY
}
