package constructmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.FastDrawCardAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import constructmod.ConstructMod;

public class SyphonPower extends AbstractPower {
	public static final String POWER_ID = ConstructMod.makeID("Syphon");
	public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

	public SyphonPower(AbstractCreature owner, int amount) {
		this.name = powerStrings.NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		updateDescription();
		this.type = PowerType.DEBUFF;
		this.isTurnBased = true;
		ConstructMod.setPowerImages(this);
	}
	
	@Override
	public void updateDescription() {
		this.description = String.format(DESCRIPTIONS[0],FontHelper.colorString(this.owner.name, "y"));
	}
	
	@Override
	public int onAttacked(final DamageInfo info, final int damageAmount) {
		if (damageAmount <= 0) return damageAmount;
		// must actually deal damage, hitting for 0 doesn't count.
		AbstractDungeon.actionManager.addToBottom(new DrawCardAction(this.owner,1));
		this.flash();
		return damageAmount;
	}

	@Override
	public void atEndOfRound() {
		if (this.amount == 0) {
			AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
		}
		else {
			AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
		}
	}
}