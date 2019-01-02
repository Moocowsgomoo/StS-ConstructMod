package constructmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import constructmod.ConstructMod;

public class NoCyclePower extends AbstractPower {
	public static final String POWER_ID = ConstructMod.makeID("NoCycle");
	public static final String NAME = "No Cycle";
	public static final String[] DESCRIPTIONS = new String[] {
			"Your cards cannot #yCycle for #b",
			" turn.",
			" turns."
	};
	public boolean justApplied = true;

	public NoCyclePower(AbstractCreature owner, int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		updateDescription();
		this.type = PowerType.DEBUFF;
		this.isTurnBased = true;
		ConstructMod.setPowerImages(this);
		this.justApplied = true;
	}
	
	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0] + this.amount + (this.amount == 1?DESCRIPTIONS[1]:DESCRIPTIONS[2]);
	}

	@Override
	public void atEndOfRound() {
		if (this.justApplied){
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
}