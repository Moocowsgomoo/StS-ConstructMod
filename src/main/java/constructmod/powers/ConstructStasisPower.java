package constructmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import constructmod.ConstructMod;

public class ConstructStasisPower extends AbstractPower {
	public static final String POWER_ID = ConstructMod.makeID("SelfStasis");
	public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	
	public AbstractCard heldCard;
	
	public ConstructStasisPower(AbstractCreature owner, int amount, AbstractCard heldCard) {
		this.name = powerStrings.NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		this.type = AbstractPower.PowerType.BUFF;
		this.isTurnBased = false;
		this.loadRegion("stasis");
		this.heldCard = heldCard.makeCopy();
		updateDescription();
	}
	
	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0] + FontHelper.colorString(this.heldCard.name, "y") + String.format(this.amount==1?DESCRIPTIONS[1]:DESCRIPTIONS[2],this.amount);
	}
}