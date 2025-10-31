
package game.skill;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.weapon.GreatKnife;
import java.util.Random;

/**
 * Created by:
 * @author Simeon Rubin
 * Modified by:
 *
 */

/**
 * A class representing the Stab and Step skill action in the game.
 * This skill allows an actor to attack a target with a GreatKnife and step away if successful.
 */
public class StabAndStepSkill extends Action {
  private Random rand = new Random();

  private Actor target;

  private GreatKnife greatKnife;

  /**
   * Constructs a StabAndStepSkill object for an actor to use against a target with a GreatKnife.
   *
   * @param target      The target actor to attack.
   * @param greatKnife  The GreatKnife weapon used to perform the skill.
   */
  public StabAndStepSkill(Actor target, GreatKnife greatKnife) {
    this.target = target;
    this.greatKnife = greatKnife;
  }

  /**
   * Executes the Stab and Step skill action.
   *
   * @param actor The actor performing the skill.
   * @param map   The game map where the skill is executed.
   * @return      A description of the outcome of the skill action.
   */
  @Override
  public String execute(Actor actor, GameMap map) {
    // Check stamina amount and reduce current Stamina
    int maxStamina = actor.getAttributeMaximum(BaseActorAttributes.STAMINA);
    int staminaRequired = (int) (0.25 * maxStamina);
    int currentStamina = actor.getAttribute(BaseActorAttributes.STAMINA);
    if (currentStamina < staminaRequired) {
      return actor + " does not have enough stamina to use the skill.";
    } else {
      actor.modifyAttribute(BaseActorAttributes.STAMINA, ActorAttributeOperations.DECREASE, staminaRequired);

    // Check if the attack hits
    if (rand.nextInt(100) <= greatKnife.chanceToHit()) {
      int damage = greatKnife.damage();
      target.hurt(damage);
      Location newLocation = generateValidLocation(map, actor);
      map.moveActor(actor, newLocation);
      String result = actor + " " + greatKnife.verb() + " " + target + " for " + damage + " damage.";
      if (!target.isConscious()) {
        result += "\n" + target.unconscious(actor, map);
      }
      return result;
    } else {
      return actor + " misses " + target + ".";
    }
    }
  }

  /**
   * Generates a valid location for the actor to step away to on the game map.
   *
   * @param map   The game map.
   * @param actor The actor performing the skill.
   * @return      A valid location for the actor to step away to.
   */
  private Location generateValidLocation(GameMap map, Actor actor) {
    int randomValue;
    int newX, newY;
    do {
      randomValue = -1 + (int) (Math.random() * 3);
      newX = map.locationOf(actor).x() + randomValue;
      newY = map.locationOf(actor).y() + randomValue;
    } while (map.isAnActorAt(map.at(newX, newY)) || (newX == map.locationOf(actor).x() && newY == map.locationOf(actor).y()));
    return map.at(newX, newY);
  }

  /**
   * Provides a description of the Stab and Step skill action for display in the game menu.
   *
   * @param actor The actor performing the skill.
   * @return      The menu description of the skill action.
   */
  @Override
  public String menuDescription(Actor actor) {
    return "Stab and Step Skill: " + actor + " attacks " + target + " with " + greatKnife + " and steps away";
  }
}
