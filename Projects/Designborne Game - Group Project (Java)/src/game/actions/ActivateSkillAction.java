package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.ActivateSkill;

/**
 * Created by:
 * @author Shannon Jian Hong Chia
 * Modified by:
 *
 */

/**
 * The ActivateSkillAction class represents an action that allows an actor to activate or reactivate an implementor of the ActivateSkill interface.
 * It extends the Action class and provides methods for executing the action and describing it in the game menu.
 */

public class ActivateSkillAction extends Action {

    /**
     * The Skill that is going to be activated
     */
    private ActivateSkill skill;

    /**
     * Constructor for the ActivateSkillAction class.
     *
     * @param skill The skill to activate or reactivate.
     */
    public ActivateSkillAction(ActivateSkill skill) {
        this.skill = skill;
    }

    /**
     * Executes the action, either activating or reactivating the skill for the actor.
     *
     * @param actor The actor performing the action.
     * @param map   The game map where the action is executed.
     * @return A description of the action's result, indicating whether the skill was activated or reactivated.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        if (skill.isSkillActive()) {
            skill.activateSkill(actor);
            return "Reactivated " + skill.getClass().getSimpleName() + " skill";
        } else {
            skill.activateSkill(actor);
            return "Activated " + skill.getClass().getSimpleName() + " skill";
        }
    }

    /**
     * Provides a description of the action for display in the game menu.
     *
     * @param actor The actor for which the action is described.
     * @return A description of the action, indicating that the skill can be activated.
     */
    @Override
    public String menuDescription(Actor actor) {
        return "Activate " + skill.getClass().getSimpleName();
    }
}