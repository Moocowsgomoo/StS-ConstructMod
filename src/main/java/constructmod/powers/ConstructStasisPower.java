package constructmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import basemod.BaseMod;
import basemod.interfaces.PostDrawSubscriber;
import constructmod.ConstructMod;
import constructmod.cards.AbstractConstructCard;

public class ConstructStasisPower extends AbstractPower {
	public static final String POWER_ID = "Self-Stasis";
	public static final String NAME = "Self-Stasis";
	public static final String[] DESCRIPTIONS = new String[] {
			"Holding: ",
			". NL When Stasis is played again, add #b",
			" #yMega-upgraded copies to your hand."
	};
	
	public AbstractCard heldCard;
	
	public ConstructStasisPower(AbstractCreature owner, int amount, AbstractCard heldCard) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		this.type = AbstractPower.PowerType.BUFF;
		this.isTurnBased = false;
		this.img = new Texture("img/powers/stasis.png");
		this.heldCard = heldCard.makeCopy();
		updateDescription();
	}
	
	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0] + this.heldCard.name + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
	}
}