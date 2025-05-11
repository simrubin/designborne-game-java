
package game.skill;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.weapon.GiantHammer;

/**
 * Created by:
 * @author Simeon Rubin
 * Modified by:
 *
 */

/**
 * A class representing the Great Slam skill action in the game.
 * This skill allows an actor to perform a powerful slam attack with a GiantHammer, damaging the target and surrounding actors.
 */
public class GreatSlamSkill extends Action {

  private Actor target;

  private GiantHammer giantHammer;

  /**
   * Constructs a GreatSlamSkill object for an actor to use against a target with a GiantHammer.
   *
   * @param target      The target actor to attack.
   * @param giantHammer The GiantHammer weapon used to perform the skill.
   */
  public GreatSlamSkill(Actor target, GiantHammer giantHammer) {
    this.target = target;
    this.giantHammer = giantHammer;
  }

  /**
   * Executes the Great Slam skill action, damaging the target and surrounding actors.
   *
   * @param actor The actor performing the skill.
   * @param map   The game map where the skill is executed.
   * @return      A description of the outcome of the skill action.
   */
  @Override
  public String execute(Actor actor, GameMap map) {
    int maxStamina = actor.getAttributeMaximum(BaseActorAttributes.STAMINA);
    int staminaRequired = (int) (0.05 * maxStamina);
    int currentStamina = actor.getAttribute(BaseActorAttributes.STAMINA);

    // Check if the player has enough stamina to use the skill
    if (currentStamina < staminaRequired) {
      return actor + " does not have enough stamina to use the skill.";
    } else {

      // Reduce player's stamina
      actor.modifyAttribute(BaseActorAttributes.STAMINA, ActorAttributeOperations.DECREASE,
          staminaRequired);

      int damage = giantHammer.damage();
      int shockwaveDamage = damage / 2;
      String result =
          actor + " " + giantHammer.verb() + " " + target + " for " + damage + " damage.";
      target.hurt(damage);

      // Iterate through surrounding locations and if they contain an actor (excluding the target), do half damage
      for (int i = -1; i <= 1; i++) {
        for (int j = -1; j <= 1; j++) {
          Location surroundLocation = map.at(map.locationOf(target).x() + i,
              map.locationOf(target).y() + j);
          if (surroundLocation.containsAnActor() && !surroundLocation.getActor().equals(target)) {
            Actor surroundTarget = surroundLocation.getActor();
            surroundTarget.hurt(shockwaveDamage);
            result +=
                "\n" + "The shockwave from the slam " + giantHammer.verb() + " " + surroundTarget
                    + " for " + shockwaveDamage + " damage.";
            if (!surroundTarget.isConscious() && !surroundTarget.equals(actor)
                && !surroundTarget.equals(target)) {
              result += "\n" + surroundTarget.unconscious(actor, map);
            }
          }
        }
      }

      if (!target.isConscious()) {
        result += "\n" + target.unconscious(actor, map);
      }
      if (!actor.isConscious()) {
        result += "\n" + actor.unconscious(actor, map);
      }

      return result;
    }
  }

  /**
   * Provides a description of the Great Slam skill action for display in the game menu.
   *
   * @param actor The actor performing the skill.
   * @return      The menu description of the skill action.
   */
  @Override
  public String menuDescription(Actor actor) {
    return "Great Slam Skill: " + actor + " attacks " + target + " with " + giantHammer + " and the shockwave attacks surrounding actors.";
  }
}
