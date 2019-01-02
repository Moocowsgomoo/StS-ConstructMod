package constructmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.FastDrawCardAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.powers.AbstractPower;
import constructmod.ConstructMod;

public class SyphonPower extends AbstractPower {
	public static final String POWER_ID = ConstructMod.makeID("Syphon");
	public static final String NAME = "Syphon";
	public static final String[] DESCRIPTIONS = new String[] {
			"Whenever ",
			" takes Attack damage, draw #b1 card."
	};

	public SyphonPower(AbstractCreature owner, int amount) {
		this.name = NAME;
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
		this.description = DESCRIPTIONS[0] + FontHelper.colorString(this.owner.name, "y") + DESCRIPTIONS[1];
	}
	
	@Override
	public int onAttacked(final DamageInfo info, final int damageAmount) {
		if (damageAmount <= 0) return damageAmount;
		// must actually deal damage, hitting for 0 doesn't count.
		AbstractDungeon.actionManager.addToBottom(new FastDrawCardAction(this.owner,this.amount));
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