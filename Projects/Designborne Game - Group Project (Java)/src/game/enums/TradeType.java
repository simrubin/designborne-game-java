package game.enums;

/**
 * Created by:
 * @author Shannon Jian Hong Chia
 * Modified by:
 *
 */

/**
 * An enumeration representing different type of transaction that can occur between player and trader.
 */
public enum TradeType {

    /**
     * Represents a non-scammable transaction
     */
    NON_SCAMMABLE,

    /**
     * Represents a transaction where the seller will steal runes.
     */
    STEAL_RUNES,

    /**
     * Represents a transaction where the seller will steal items.
     */
    STEAL_ITEMS
}
