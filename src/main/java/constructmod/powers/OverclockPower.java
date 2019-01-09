package constructmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import constructmod.ConstructMod;

public class OverclockPower extends AbstractPower {
	public static final String POWER_ID = ConstructMod.makeID("Overclock");
	public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	
	private int statMultiplier;
	
	public OverclockPower(AbstractCreature owner, int amount, int mult) {
		this.name = powerStrings.NAME;
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
		this.description = String.format(this.amount==1?DESCRIPTIONS[0]:DESCRIPTIONS[1], this.amount*statMultiplier, this.amount);
	}
	
	@Override
	public void atStartOfTurn() {
		this.flash();
		AbstractDungeon.actionManager.addToBottom(new DrawCardAction(this.owner, this.amount*statMultiplier));
		AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Burn(),this.amount));
	}
}