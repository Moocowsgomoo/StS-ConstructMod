package constructmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import basemod.BaseMod;
import basemod.interfaces.PostDrawSubscriber;
import constructmod.ConstructMod;

public class OverpowerPower extends AbstractPower {
	public static final String POWER_ID = ConstructMod.makeID("Overpower");
	public static final String NAME = "Overpower";
	public static final String[] DESCRIPTIONS = new String[] {
			"Granting #b+",
			" #yStrength and #yDexterity. At the start of your turn, add #b",
			" to your hand."
	};
	
	private int statMultiplier;
	
	public OverpowerPower(AbstractCreature owner, int amount, int mult) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		this.type = AbstractPower.PowerType.BUFF;
		this.isTurnBased = false;
		this.img = new Texture("img/constructPowers/overpower.png");
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
		AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Burn(),this.amount));
	}
	
	@Override
	public void onRemove() {
		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.owner,this.owner,new DexterityPower(owner,-this.amount*statMultiplier)));
		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.owner,this.owner,new StrengthPower(owner,-this.amount*statMultiplier)));
    }
}