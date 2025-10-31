package game.actions;

import edu.monash.fit2099.engine.actors.Actor;

/**
 * Created by:
 * @author Shannon Jian Hong Chia
 * Modified by:
 *
 */

/**
 * The ActivateSkill interface defines the contract for objects that can be activated and deactivated as skills.
 */
public interface ActivateSkill {

    /**
     * Activates the skill for a specified actor.
     *
     * @param actor The actor for which the skill is activated.
     */
    void activateSkill(Actor actor);

    /**
     * Deactivates the skill.
     */
    void deactivateSkill();

    /**
     * Checks whether the skill is currently active.
     *
     * @return True if the skill is active, false otherwise.
     */
    boolean isSkillActive();

}
