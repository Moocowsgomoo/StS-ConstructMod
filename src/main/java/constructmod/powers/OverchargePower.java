package constructmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import constructmod.ConstructMod;

public class OverchargePower extends AbstractPower {
	public static final String POWER_ID = ConstructMod.makeID("Overcharge");
	public static final String NAME = "Overcharge";
	public static final String[] DESCRIPTIONS = new String[] {
			"At the start of your turn, gain #b",
			" [R] and add #b",
			" to your hand."
	};
	
	private int statMultiplier;
	
	public OverchargePower(AbstractCreature owner, int amount, int mult) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		this.type = AbstractPower.PowerType.BUFF;
		this.isTurnBased = false;
		ConstructMod.setPowerImages(this);
		this.statMultiplier = mult;
		updateDescription();
	}
	
	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0] + this.amount*statMultiplier + DESCRIPTIONS[1] + this.amount + (this.amount>1?" Burns":" Burn") + DESCRIPTIONS[2];
	}
	
	@Override
	public void atStartOfTurn() {
		this.flash();
		AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(this.amount*statMultiplier));
		AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Burn(),this.amount));
	}
}