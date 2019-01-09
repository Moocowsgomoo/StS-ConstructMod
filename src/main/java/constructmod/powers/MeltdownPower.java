package constructmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import constructmod.ConstructMod;

public class MeltdownPower extends AbstractPower {
	public static final String POWER_ID = ConstructMod.makeID("Meltdown");
	public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	
	public MeltdownPower(AbstractCreature owner, int amount) {
		this.name = powerStrings.NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		this.type = AbstractPower.PowerType.BUFF;
		this.isTurnBased = false;
		ConstructMod.setPowerImages(this);
		updateDescription();
	}
	
	@Override
	public void updateDescription() {
		this.description = String.format(DESCRIPTIONS[0],this.amount);
	}
	
	@Override
	public void atStartOfTurn() {
		this.flash();
		AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(this.owner, DamageInfo.createDamageMatrix(this.amount, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
		AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Burn(),1));
	}
}