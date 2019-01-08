package constructmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import constructmod.ConstructMod;
import constructmod.actions.InstantDamageRandomEnemyAction;

public class AutoturretPower extends AbstractCyclePower {
	public static final String POWER_ID = ConstructMod.makeID("Autoturret");
	public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	
	public AutoturretPower(AbstractCreature owner, int amount) {
		this.name = powerStrings.NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		updateDescription();
		this.type = AbstractPower.PowerType.BUFF;
		this.isTurnBased = false;
		ConstructMod.setPowerImages(this);
	}
	
	@Override
	public void updateDescription() {
		this.description = String.format(DESCRIPTIONS[0],this.amount);
	}
	
	@Override
	public void onCycleCard(AbstractCard card) {
		flashWithoutSound();
		AbstractDungeon.actionManager.addToTop(new InstantDamageRandomEnemyAction(
				new DamageInfo(this.owner, this.amount, DamageInfo.DamageType.THORNS),AbstractGameAction.AttackEffect.BLUNT_LIGHT,ConstructMod.areCyclesFast()));
	}
}