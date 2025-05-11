package game.skill;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import game.actions.ActivateSkill;
import game.weapon.Broadsword;

/**
 * Created by:
 * @author Shannon Jian Hong Chia
 * Modified by:
 *
 */

/**
 * The FocusSkill class represents a skill that can be activated by an actor, providing enhancements to a Broadsword weapon.
 * It implements the ActivateSkill interface and allows the actor to focus their attacks with the Broadsword.
 */
public class FocusSkill implements ActivateSkill {

    /**
     * The weapons initial damage multiplier
     */
    private float defaultDamageMultiplier;

    /**
     * The weapons initial hit rate
     */
    private int defaultHitRate;

    /**
     * The weapons increased damage multiplier
     */
    private float damageMultiplier;

    /**
     * The weapons increased hit rate
     */
    private int hitRate;

    /**
     * The weapon that uses the Focus skill
     */
    private Broadsword broadsword;

    /**
     * Keep track whether the skill is active
     */
    private boolean skillActive = false;

    /**
     * Constructor for the FocusSkill class.
     *
     * @param broadsword              The Broadsword associated with this skill.
     * @param defaultDamageMultiplier The default damage multiplier without the skill.
     * @param defaultHitRate          The default hit rate without the skill.
     * @param damageMultiplier        The damage multiplier applied when the skill is active.
     * @param hitRate                 The hit rate applied when the skill is active.
     */
    public FocusSkill(Broadsword broadsword, float defaultDamageMultiplier, int defaultHitRate, float damageMultiplier, int hitRate) {
        this.defaultDamageMultiplier = defaultDamageMultiplier;
        this.defaultHitRate = defaultHitRate;
        this.damageMultiplier = damageMultiplier;
        this.hitRate = hitRate;
        this.broadsword = broadsword;
    }

    /**
     * Activates the focus skill for the actor, enhancing the Broadsword's damage and hit rate.
     *
     * @param actor The actor activating the skill.
     */
    @Override
    public void activateSkill(Actor actor) {

        int maxStamina = actor.getAttributeMaximum(BaseActorAttributes.STAMINA);
        int staminaRequired = (int) (0.2 * maxStamina);
        int currentStamina = actor.getAttribute(BaseActorAttributes.STAMINA);

        // Check stamina amount
        if (currentStamina >= staminaRequired) {

            // Reduce current Stamina
            actor.modifyAttribute(BaseActorAttributes.STAMINA, ActorAttributeOperations.DECREASE, staminaRequired);

            // Apply skill effect
            broadsword.updateHitRate(hitRate);
            broadsword.increaseDamageMultiplier(damageMultiplier);
            broadsword.setFocusRound(0);
            skillActive = true;
        }
    }

    /**
     * Deactivates the focus skill, reverting the Broadsword's damage and hit rate to their default values.
     */
    @Override
    public void deactivateSkill() {
        if (skillActive) {
            broadsword.updateDamageMultiplier(defaultDamageMultiplier);
            broadsword.updateHitRate(defaultHitRate);
            skillActive = false;
        }
    }

    /**
     * Checks if the focus skill is currently active.
     *
     * @return true if the skill is active, false otherwise.
     */
    @Override
    public boolean isSkillActive() {
        return skillActive;
    }
}