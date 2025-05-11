package game.entity.enemy;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.actions.RemoveActorAction;
import game.enums.Ability;
import game.enums.ActorType;
import game.enums.BehaviourPriority;
import game.enums.Status;
import game.items.Bloodberry;
import game.items.Droppable;
import game.reset.ResetManager;
import game.reset.Resettable;
import game.rune.DroppedRuneManager;
import game.rune.Rune;
import java.util.Random;

/**
 * Created by:
 * @author Simeon Rubin
 * Modified by:
 */

/**
 * The LivingBranch class represents an enemy in the game, a living branch entity.
 * It is capable of causing damage to other actors and dropping items upon defeat.
 * The LivingBranch class is also resettable.
 */
public class LivingBranch extends Enemy implements Droppable, Resettable {

  /**
   * The constructor for the LivingBranch class.
   * Initializes the LivingBranch with a name, display character, hit points, and rune value.
   */
  public LivingBranch() {
    super("Living Branch", '?', 75, 500);
    this.addCapability(ActorType.ENEMY_LIVING_BRANCH);
    this.addCapability(Ability.VOID_IMMUNITY);
    this.setDisplayString("🌿");
    this.behaviours.remove(BehaviourPriority.WANDER_BEHAVIOUR.getPriority()); // Doesn't have a wander behavior
    ResetManager.getInstance().register(this);
  }

  /**
   * Returns the intrinsic weapon of the LivingBranch.
   *
   * @return An IntrinsicWeapon representing the attack capabilities of the LivingBranch.
   */
  @Override
  public IntrinsicWeapon getIntrinsicWeapon() {
    return new IntrinsicWeapon(250, "jabs", 90);
  }

  /**
   * Drops items such as Bloodberries and runes at the specified location upon defeat.
   *
   * @param location The location where the items are dropped.
   */
  @Override
  public void dropItem(Location location) {
    Random random = new Random();

    if (random.nextFloat() <= 0.5) {
      location.addItem(new Bloodberry());
    }

    Rune rune = new Rune(getRuneValue());
    location.addItem(rune);
    DroppedRuneManager.getInstance().register(rune, location);
  }

  /**
   * Defines the actions to be taken by the LivingBranch on its turn.
   *
   * @param actions     The list of allowable actions.
   * @param lastAction  The last action performed.
   * @param map         The GameMap on which the LivingBranch exists.
   * @param display     The display used for rendering the game.
   * @return An Action representing the action to be taken on the LivingBranch's turn.
   */
  @Override
  public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
    if (this.hasCapability(Status.RESET_INBOUND)) {
      return new RemoveActorAction();
    }
    return super.playTurn(actions, lastAction, map, display);
  }

  /**
   * Defines the allowable actions for the LivingBranch when interacting with other actors.
   *
   * @param otherActor  The other actor involved in the interaction.
   * @param direction   The direction of the interaction.
   * @param map         The GameMap in which the interaction occurs.
   * @return An ActionList representing the allowable actions for the interaction.
   */
  @Override
  public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
    return super.allowableActions(otherActor, direction, map);
  }

  /**
   * Resets the state of the LivingBranch by adding the {@code Status.RESET_INBOUND} capability.
   */
  @Override
  public void reset() {
    this.addCapability(Status.RESET_INBOUND);
  }
}
