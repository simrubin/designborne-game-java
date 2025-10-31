package game.weapon;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.AttackAction;
import game.enums.*;
import game.skill.StabAndStepSkill;
import game.items.Upgradable;

/**
 * Created by:
 * @author Simeon Rubin
 * Modified by:
 *
 */

/**
 * A class representing a Great Knife weapon in the game.
 * This weapon is tradeable and can be used by actors to attack enemies.
 */


public class GreatKnife extends TradeableWeapon implements Upgradable{
  private boolean isUpgraded = false; // Initialize the upgrade status

  /**
   * Keeps track of the upgrade count
   */
  private int upgradeCount;

  /**
   * Constructs a GreatKnife object with default attributes.
   */
  public GreatKnife() {
    super("Great Knife", '>', 75, "stabs", 70, 175);
    this.addCapability(ItemCapability.UPGRADABLE);
  }

  /**
   * Generates a list of allowable actions for the GreatKnife based on the target actor and location.
   *
   * @param target    The target actor.
   * @param location  The location where the GreatKnife is.
   * @return          An ActionList containing allowable actions for the GreatKnife.
   */
  @Override
  public ActionList allowableActions(Actor target, Location location) {
    ActionList actions = new ActionList();

    if (target.hasCapability(Status.HOSTILE_TO_ENEMY)) {
      actions.add(new AttackAction(target, location.toString(), this));
      actions.add(new StabAndStepSkill(target, this));
    }

    return actions;
  }

  /**
   * Creates a new instance of the GreatKnife item.
   *
   * @return A new instance of the GreatKnife.
   */
  @Override
  public Item newItemInstance() {
    return new GreatKnife();
  }

  /**
   * Checks if the price of the GreatKnife is affected when selling to a seller actor.
   *
   * @param seller The actor who is the seller.
   * @return       True if the price is affected, false otherwise.
   */
  @Override
  public boolean isPriceAffected(Actor seller) {
    double probability = 0.05;
    return Math.random() < probability;
  }

  /**
   * Calculates the affected price of the GreatKnife when selling to a seller actor.
   *
   * @param seller The actor who is the seller.
   * @return       The affected price of the GreatKnife.
   */
  @Override
  public int affectedPrice(Actor seller) {
    double priceMultiplier = 3;
    return (int) (getPrice() * priceMultiplier);
  }

  /**
   * Gets the trade type of the GreatKnife for a seller actor.
   *
   * @param seller The actor who is the seller.
   * @return       The trade type of the GreatKnife.
   */
  @Override
  public Enum<TradeType> getTradeType(Actor seller) {
    Enum<TradeType> tradeType = super.getTradeType(seller);

    if (seller.hasCapability(ActorType.PLAYABLE)) {
      tradeType = TradeType.STEAL_RUNES;
    }
    return tradeType;
  }

  /**
   * Retrieves the price of upgrading a GreatKnife.
   *
   * @return The price.
   */
  @Override
  public int getUpgradePrice() {
    return 2000;
  }

  /**
   * Retrieves the type of upgrade of GreatKnife.
   *
   * @return The upgrade type.
   */
  @Override
  public Enum<UpgradeType> getUpgradeType() {
    return UpgradeType.UPGRADE_MULTIPLE;
  }

  /**
   * Retrieves the status of the upgrade.
   *
   * @return The status of upgrade.
   */
  @Override
  public boolean isUpgraded() {
    return isUpgraded;
  }

  /**
   * Upgrades the Great Knife to increase its hit rate.
   * Each upgrade increases the weapon’s hit rate by 1 per cent.
   */
  public void upgrade() {
    if (!this.isUpgraded()) {
      upgradeCount++;
      int additionalHitRate = upgradeCount;

      // Increase hit rate by 1 per cent
      this.increaseHitRate(1);

      System.out.println("Great Knife has been upgraded!");
      System.out.println("Additional hit rate after this upgrade: " + additionalHitRate + "%");

      // Reset isUpgraded back to false to allow for further upgrades
      this.isUpgraded = false;

      // Print out the hit rate after upgrade
      System.out.println("Current hit rate after upgrade: " + chanceToHit() + "%");
    } else {
      upgradeCount++;
      System.out.println("This Great Knife has already been upgraded " + upgradeCount + " times.");
    }
  }
}
