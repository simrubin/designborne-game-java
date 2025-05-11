package game.behaviour;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.MoveActorAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.entity.Player;
import game.enums.ActorType;

/**
 * A class that figures out a MoveAction that will move the actor one step 
 * closer to a target Actor.
 * @see edu.monash.fit2099.demo.mars.Application
 *
 * Created by:
 * @author Riordan D. Alfredo
 * Modified by: Jing Yi, Shannon
 *
 */
public class FollowBehaviour implements Behaviour {

	private final Actor target;

	/**
	 * Constructor.
	 */
	public FollowBehaviour(Actor subject) {
		this.target = subject;
	}

//	private Player findPlayer(GameMap map) {
//		for (int x : map.getXRange()) {
//			for (int y : map.getYRange()) {
//				Location location = map.at(x, y);
//				if (location.containsAnActor()) {
//					Actor actor = location.getActor();
//					if (actor.hasCapability(ActorType.PLAYABLE)) {
//						return (Player) actor;
//					}
//				}
//			}
//		}
//		return null;
//	}

	@Override
	public Action getAction(Actor actor, GameMap map) {
//		Actor target = findPlayer(map);
		if(!map.contains(target) || !map.contains(actor))
			return null;

		Location here = map.locationOf(actor);
		Location there = map.locationOf(target);

		int currentDistance = distance(here, there);
		for (Exit exit : here.getExits()) {
			Location destination = exit.getDestination();
			if (destination.canActorEnter(actor)) {
				int newDistance = distance(destination, there);
				if (newDistance < currentDistance) {
					return new MoveActorAction(destination, exit.getName());
				}
			}
		}

		return null;
	}

	/**
	 * Compute the Manhattan distance between two locations.
	 * 
	 * @param a the first location
	 * @param b the first location
	 * @return the number of steps between a and b if you only move in the four cardinal directions.
	 */
	private int distance(Location a, Location b) {
		return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
	}
}
