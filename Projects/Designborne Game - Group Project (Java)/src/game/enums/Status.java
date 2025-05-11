package game.enums;

/**
 * Use this enum class to represent a status.
 * Example #1: if the player is sleeping, you can attack a Status.SLEEP to the player class
 * Created by:
 * @author Riordan D. Alfredo
 * Modified by: Shannon Jian Hong Chia
 */
public enum Status {
    /**
     * Indicates hostility towards enemies.
     */
    HOSTILE_TO_ENEMY,

    /**
     * Indicates an impending reset.
     */
    RESET_INBOUND,

    /**
     * Indicates an impending death event.
     */
    DEATH_INBOUND
}
