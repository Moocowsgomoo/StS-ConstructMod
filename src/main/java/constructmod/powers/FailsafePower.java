package constructmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import constructmod.ConstructMod;
import constructmod.cards.AbstractCycleCard;

public class FailsafePower extends AbstractOnDrawPower{
	public static final String POWER_ID = ConstructMod.makeID("Failsafe");
	public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

	private int amountPerTurn=0;

	public FailsafePower(AbstractCreature owner, int amount) {
		this.name = powerStrings.NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = this.amountPerTurn = amount;
		updateDescription();
		this.type = PowerType.BUFF;
		this.isTurnBased = false;
		ConstructMod.setPowerImages(this);
	}

	@Override
	public void stackPower(final int stackAmount) {
		super.stackPower(stackAmount);
		this.amountPerTurn += stackAmount;
	}
	
	@Override
	public void updateDescription() {
		if (this.amount == 1) this.description = String.format(DESCRIPTIONS[0],this.amountPerTurn);
		else this.description = String.format(DESCRIPTIONS[1],this.amount,this.amountPerTurn);
	}
	
	@Override
	public void onDrawCard (AbstractCard c) {
		if (this.amount > 0 && c.type == AbstractCard.CardType.STATUS) {
			this.flash();
			this.amount--;
			this.updateDescription();
			AbstractCycleCard.cycle(c);
		}
	}

	@Override
	public void atStartOfTurn() {
		this.amount = this.amountPerTurn;
		updateDescription();
	}
}