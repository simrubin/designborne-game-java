package game.weapon;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.actions.ActivateSkillAction;
import game.actions.AttackAction;
import game.enums.*;
import game.items.Upgradable;
import game.skill.FocusSkill;

/**
 * Created by:
 * @author Shannon Jian Hong Chia
 * Modified by:
 *
 */

/**
 * The Broadsword class represents a type of weapon item in the game that can be used for attacks and has a focus skill.
 * It extends the WeaponItem class and provides methods for allowable actions, tick functionality, and focus management.
 */

public class Broadsword extends TradeableWeapon implements Upgradable {
    private boolean isUpgraded = false; // Initialize the upgrade status

    /**
     * Broadsword's skill
     */
    private FocusSkill focusSkill;

    /**
     * Keep track how many rounds the focus skill have been activated
     */
    private int focusRound;

    /**
     * The weapons default damage multiplier
     */
    private final float defaultDamageMultiplier = 1;

    /**
     * The weapons default hit rate
     */
    private final int defaultHitRate;

    /**
     * Focus Skill buff: Increase damage multiplier rate
     */
    private final float skillDamageMultiplier = .1f;

    /**
     * Focus skill buff: Increase hit rate
     */
    private final int skillHitRate = 90;

    /**
     * Keeps track of the upgrade count
     */
    private int upgradeCount;

    /**
     * The default damage that the broadsword deals
     */
    private int defaultDamage = 110;


    /**
     * Constructor for the Broadsword class.
     */
    public Broadsword() {
        super("Broadsword", '1', 110, "Ueghh", 80,100);
        this.defaultHitRate = 80;
        this.focusRound = focusRound;
        this.focusSkill = new FocusSkill(this, defaultDamageMultiplier, defaultHitRate, skillDamageMultiplier, skillHitRate);
        this.addCapability(ItemCapability.UPGRADABLE);
    }

    /**
     * Returns a list of allowable actions for attacking a target at a location.
     *
     * @param target   The target actor to attack.
     * @param location The location of the target.
     * @return A list of allowable actions, including an AttackAction.
     */
    @Override
    public ActionList allowableActions(Actor target, Location location) {
        ActionList actions = new ActionList();

        if(target.hasCapability(Status.HOSTILE_TO_ENEMY)){
            actions.add(new AttackAction(target, location.toString(),this));
        }

        return actions;
    }

    /**
     * Returns a list of allowable actions for the Broadsword, including activating the focus skill.
     *
     * @param target The target actor for which actions are allowed.
     * @return A list of allowable actions, including activating the focus skill.
     */
    @Override
    public ActionList allowableActions(Actor target) {
        ActionList actions = new ActionList();
        actions.add(new ActivateSkillAction(focusSkill));
        return actions;
    }

    /**
     * Sets the current round of focus for the Broadsword.
     *
     * @param focusRound The current round of focus.
     */
    public void setFocusRound(int focusRound) {
        this.focusRound = focusRound;
    }

    /**
     * Performs actions during each tick of the game.
     * Manages the focus skill and its expiration.
     *
     * @param location The location of the Broadsword.
     * @param actor    The actor using the Broadsword.
     */
    public void tick(Location location, Actor actor) {
        if (focusSkill.isSkillActive()) {
            int MAX_ROUND = 5;

            if (focusRound < MAX_ROUND) {
                focusRound++;
                new Display().println((String.format("Focus counter: %d/%d", focusRound, MAX_ROUND)));

                if (focusRound == MAX_ROUND) {
                    new Display().println("Focus will expire next round!");
                    focusSkill.deactivateSkill();
                }
            }
        }
    }

    /**
     * Creates a new instance of the Broadsword.
     *
     * @return A new instance of the Broadsword.
     */
    @Override
    public Item newItemInstance(){
        return new Broadsword();
    }

    /**
     * Determines whether the price of the Broadsword is affected during trade.
     *
     * @param seller The actor selling the Broadsword.
     * @return true if the price is affected; otherwise false.
     */
    @Override
    public boolean isPriceAffected(Actor seller) {
        double probability = 0.05;
        return Math.random() < probability;
    }

    /**
     * Retrieves the affected price of the Broadsword during trade.
     *
     * @param seller The actor selling the Broadsword.
     * @return The affected price.
     */
    public int affectedPrice(Actor seller) {
        return getPrice();
    }

    /**
     * Retrieves the trade type for the Broadsword during trade.
     *
     * @param seller The actor selling the Broadsword.
     * @return The trade type.
     */
    @Override
    public Enum<TradeType> getTradeType(Actor seller) {
        Enum<TradeType> tradeType = super.getTradeType(seller);
        if (seller.hasCapability(ActorType.TRADER)){
            tradeType = TradeType.STEAL_RUNES;
        }
        return tradeType;
    }

    /**
     * Retrieves the price of upgrading a Broadsword.
     *
     * @return The price.
     */
    @Override
    public int getUpgradePrice() {
        return 1000;
    }

    /**
     * Retrieves the type of upgrade of Broadsword.
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
     * Returns the total damage dealt by the Broadsword, accounting for base damage and upgrades.
     * If the Broadsword has been upgraded, the additional damage points are added to the base damage.
     *
     * @return The total damage dealt by the Broadsword.
     */
    @Override
    public int damage() {
        int baseDamage = defaultDamage;

        // Add additional damage based on the number of upgrades
        return baseDamage + (upgradeCount * 10);
    }

    /**
     * Upgrades the Broadsword to increase its damage potential.
     *
     * If the Broadsword has already been upgraded, a message is printed indicating
     * that it is already upgraded. Otherwise, the Broadsword is upgraded, and a message
     * is printed to confirm the upgrade. The total damage is recalculated after the upgrade.
     */
    public void upgrade() {

        upgradeCount++;
        int additionalDamage = upgradeCount * 10;

        System.out.println("This Broadsword has been upgraded " + upgradeCount + " times.");
        System.out.println("Additional damage after this upgrade: " + additionalDamage);

        // Reset isUpgraded back to false to allow for further upgrades
        this.isUpgraded = false;

        // Print out the damage after upgrade
        System.out.println("Current damage after upgrade: " + damage());
    }


}