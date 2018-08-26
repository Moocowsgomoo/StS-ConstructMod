package constructmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import basemod.BaseMod;
import basemod.interfaces.PostDrawSubscriber;
import constructmod.ConstructMod;

public class SiegeFormPower extends AbstractPower {
	public static final String POWER_ID = ConstructMod.makeID("SiegeForm");
	public static final String NAME = "Siege Form";
	public static final String[] DESCRIPTIONS = new String[] {
			"When you play a card, gain #b",
			" #yStrength until the end of the turn.",
	};
	
	public SiegeFormPower(AbstractCreature owner, int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		updateDescription();
		this.type = AbstractPower.PowerType.BUFF;
		this.isTurnBased = false;
		this.img = new Texture("img/constructPowers/siege_form.png");
	}
	
	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
	}
	
	@Override
	public void onPlayCard (final AbstractCard card, final AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner, new StrengthPower(owner, this.amount), this.amount));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner, new LoseStrengthPower(owner, this.amount), this.amount));
	}
}