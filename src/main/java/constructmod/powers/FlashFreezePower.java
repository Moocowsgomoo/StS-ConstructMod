package constructmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import constructmod.ConstructMod;

public class FlashFreezePower extends AbstractPower {
	public static final String POWER_ID = ConstructMod.makeID("FlashFreeze");
	public boolean justApplied;
	public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

	public FlashFreezePower(AbstractCreature owner, int amount) {
		this.name = powerStrings.NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		updateDescription();
		this.type = PowerType.BUFF;
		this.isTurnBased = true;
		this.justApplied = true;
		ConstructMod.setPowerImages(this);
	}

	@Override
	public void atEndOfRound() {
		if (this.justApplied) {
			this.justApplied = false;
			return;
		}
		if (this.amount == 0) {
			AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
		}
		else {
			AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
		}
	}

	@Override
	public void updateDescription() {
		this.description = String.format(this.amount==1?DESCRIPTIONS[0]:DESCRIPTIONS[1],this.amount);
	}
}