/**
 * Enumeration representing the priorities for different behaviors in the game.
 * Each behavior has a specific priority level associated with it.
 * @author Simeon Rubin
 * Modified by:
 *
 */
package game.enums;

public enum BehaviourPriority {
  /**
   * Priority level for the wander behavior.
   * This behavior represents the character's aimless wandering in the game.
   */
  WANDER_BEHAVIOUR(1000),

  /**
   * Priority level for the attack behavior.
   * This behavior represents the character's aggressive attack stance in the game.
   */
  ATTACK_BEHAVIOUR(999),

  /**
   * Priority level for the attack behavior.
   * This behavior represents the character's aggressive attack stance in the game.
   */
  FOLLOW_BEHAVIOUR(998);

  private int priority;

  /**
   * Constructs a BehaviorPriority enum with the specified priority level.
   *
   * @param priority The priority level associated with the behavior.
   */
  BehaviourPriority(int priority) {
    this.priority = priority;
  }

  /**
   * Gets the priority level associated with the behavior.
   *
   * @return The priority level for the behavior.
   */
  public int getPriority() {
    return priority;
  }
}
