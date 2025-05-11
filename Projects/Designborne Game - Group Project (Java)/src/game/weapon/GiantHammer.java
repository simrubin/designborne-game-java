
package game.weapon;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.AttackAction;
import game.enums.Status;
import game.enums.TradeType;
import game.skill.GreatSlamSkill;


/**
 * Created by:
 * @author Simeon Rubin
 * Modified by:
 *
 */

/**
 * A class representing a Giant Hammer weapon in the game.
 * This weapon is tradeable and can be used by actors to attack enemies.
 */

public class GiantHammer extends TradeableWeapon {

  /**
   * Constructs a GiantHammer object with default attributes.
   */
  public GiantHammer() {
    super("Giant Hammer", 'P', 160, "smashes", 90, 250);
  }

  /**
   * Generates a list of allowable actions for the GiantHammer based on the target actor and location.
   *
   * @param target    The target actor.
   * @param location  The location where the GiantHammer is.
   * @return          An ActionList containing allowable actions for the GiantHammer.
   */
  @Override
  public ActionList allowableActions(Actor target, Location location) {
    ActionList actions = new ActionList();

    if (target.hasCapability(Status.HOSTILE_TO_ENEMY)) {
      actions.add(new AttackAction(target, location.toString(), this));
      actions.add(new GreatSlamSkill(target, this));
    }

    return actions;
  }

  /**
   * Checks if the price of the GiantHammer is affected when selling to a seller actor.
   *
   * @param seller The actor who is the seller.
   * @return       Always returns false because the price is not affected.
   */
  @Override
  public boolean isPriceAffected(Actor seller) {
    return false;
  }

  /**
   * Calculates the affected price of the GiantHammer when selling to a seller actor.
   *
   * @param seller The actor who is the seller.
   * @return       Always returns 0 because the price is not affected.
   */
  @Override
  public int affectedPrice(Actor seller) {
    return 0;
  }

  /**
   * Creates a new instance of the GiantHammer item.
   *
   * @return A new instance of the GiantHammer.
   */
  @Override
  public Item newItemInstance() {
    return new GiantHammer();
  }

  /**
   * Gets the trade type of the GiantHammer for a seller actor.
   *
   * @param seller The actor who is the seller.
   * @return       The trade type of the GiantHammer.
   */
  @Override
  public Enum<TradeType> getTradeType(Actor seller) {
    return super.getTradeType(seller);
  }
}
