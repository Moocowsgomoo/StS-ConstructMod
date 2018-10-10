package constructmod.powers;

import basemod.BaseMod;
import basemod.interfaces.PostBattleSubscriber;
import basemod.interfaces.PostDrawSubscriber;
import basemod.interfaces.PostDungeonInitializeSubscriber;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import constructmod.ConstructMod;
import constructmod.actions.CycleCardAction;
import constructmod.cards.AbstractConstructCard;
import constructmod.cards.AbstractCycleCard;
import constructmod.cards.PanicFire;

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
		this.img = new Texture("img/constructPowers/failsafe.png");
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