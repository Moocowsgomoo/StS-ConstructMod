package constructmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import constructmod.ConstructMod;
import constructmod.cards.AbstractCycleCard;

public class FailsafePower extends AbstractOnDrawPower{
	public static final String POWER_ID = ConstructMod.makeID("Failsafe");
	public static final String NAME = "Failsafe";
	public static final String[] DESCRIPTIONS = new String[] {
			"The next #b",
			" Status cards drawn will #yCycle. Resets to #b",
			" at the start of your turn.",
	};

	private int amountPerTurn=0;

	public FailsafePower(AbstractCreature owner, int amount) {
		this.name = NAME;
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
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.amountPerTurn + DESCRIPTIONS[2];
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