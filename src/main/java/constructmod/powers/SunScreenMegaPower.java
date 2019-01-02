package constructmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import constructmod.ConstructMod;
import constructmod.actions.SunScreenAction;

public class SunScreenMegaPower extends AbstractPower {
	public static final String POWER_ID = ConstructMod.makeID("SunScreen");
	public static final String NAME = "Sun Screen (Mega)";
	public static final String[] DESCRIPTIONS = new String[] {
			"At the end of your turn, #yExhaust a random #yCurse card to gain #b",
			" #yBlock."
	};

	public SunScreenMegaPower(AbstractCreature owner, int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		updateDescription();
		this.type = PowerType.BUFF;
		this.isTurnBased = false;
		this.img = new Texture("img/constructPowers/32/SunScreen.png");
	}
	
	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
	}
	
	@Override
	public void atEndOfTurn(final boolean isPlayer) {
		if (!isPlayer) return;
		AbstractDungeon.actionManager.addToBottom(new SunScreenAction(this.amount, AbstractCard.CardType.CURSE));
	}
}