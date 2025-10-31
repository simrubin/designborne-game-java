package game.entity.enemy;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.actions.RemoveActorAction;
import game.behaviour.FollowBehaviour;
import game.enums.Ability;
import game.enums.ActorType;
import game.enums.BehaviourPriority;
import game.enums.Status;
import game.items.Droppable;
import game.items.HealingVial;
import game.items.RefreshingFlask;
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
 * The EldentreeGuardian class represents an enemy type in the game with special capabilities.
 * It extends the Enemy class and has the ability to drop items and runes upon defeat.
 */
public class EldentreeGuardian extends Enemy implements Droppable, Resettable {

  /**
   * The constructor for the EldentreeGuardian class.
   * Creates an Eldentree Guardian with specific attributes.
   */
  public EldentreeGuardian() {
    super("Eldentree Guardian", 'e', 250, 250);
    this.addCapability(ActorType.ENEMY_ELDENTREE_GUARDIAN);
    this.addCapability(Ability.VOID_IMMUNITY);
    this.setDisplayString("🌳");
    ResetManager.getInstance().register(this);
  }

  /**
   * Retrieves the intrinsic weapon of the Eldentree Guardian.
   *
   * @return The intrinsic weapon used by the Eldentree Guardian.
   */
  @Override
  public IntrinsicWeapon getIntrinsicWeapon() {
    return new IntrinsicWeapon(50, "slashes", 80);
  }

  /**
   * Drops items and runes at the specified location when the Eldentree Guardian is defeated.
   *
   * @param location The location where items and runes will be dropped.
   */
  @Override
  public void dropItem(Location location) {
    Random random = new Random();

    if (random.nextFloat() <= 0.25) {
      location.addItem(new HealingVial());
    }

    if (random.nextFloat() <= 0.15) {
      location.addItem(new RefreshingFlask());
    }

    Rune rune = new Rune(getRuneValue());
    location.addItem(rune);
    DroppedRuneManager.getInstance().register(rune, location);
  }

  /**
   * Performs the Eldentree Guardian's turn, considering the possibility of resetting.
   *
   * @param actions    The list of allowable actions for the Eldentree Guardian.
   * @param lastAction The last action performed.
   * @param map        The current GameMap.
   * @param display    The display to output information.
   * @return The action to be performed by the Eldentree Guardian during its turn.
   */
  @Override
  public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
    if (this.hasCapability(Status.RESET_INBOUND)) {
      return new RemoveActorAction();
    }
    return super.playTurn(actions, lastAction, map, display);
  }

  /**
   * Retrieves the allowable actions for the Eldentree Guardian when interacting with other actors.
   *
   * @param otherActor The other actor involved in the interaction.
   * @param direction  The direction of the interaction.
   * @param map        The current GameMap.
   * @return The list of allowable actions for the Eldentree Guardian's interaction with other actors.
   */
  @Override
  public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
    if (otherActor.hasCapability(ActorType.PLAYABLE)) {
      this.behaviours.put(BehaviourPriority.FOLLOW_BEHAVIOUR.getPriority(), new FollowBehaviour(otherActor));
    }
    return super.allowableActions(otherActor, direction, map);
  }

  /**
   * Resets the state of the Eldentree Guardian by adding the {@code Status.RESET_INBOUND} capability.
   */
  @Override
  public void reset() {
    this.addCapability(Status.RESET_INBOUND);
  }
}
