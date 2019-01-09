package constructmod.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import constructmod.ConstructMod;

public class MegaZapperPower extends AbstractPower {
	public static final String POWER_ID = ConstructMod.makeID("MegaZapper");
	public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	
	public MegaZapperPower(AbstractCreature owner, int amount) {
		this.name = powerStrings.NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		updateDescription();
		this.type = AbstractPower.PowerType.BUFF;
		this.isTurnBased = false;
		ConstructMod.setPowerImages(this, ZapperPower.POWER_ID);
	}
	
	@Override
	public void updateDescription() {
		this.description = String.format(DESCRIPTIONS[0],this.amount);
	}
	
	@Override
	public void onApplyPower(final AbstractPower power, final AbstractCreature target, final AbstractCreature source) {
		if ((power.ID != "Strength" && power.ID != "Dexterity") || power.amount >= 0 || target != this.owner) return;
		flash();
		
		final AbstractMonster mo = AbstractDungeon.getMonsters().getRandomMonster(true);
		if (mo != null) {
			if (!mo.hasPower("Artifact")) {
	             AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, this.owner, new GainStrengthPower(
	            		 mo, 1), 1, true, AbstractGameAction.AttackEffect.NONE));
	         }
			AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(mo,this.owner,new StrengthPower(mo,-1),-1));
			AbstractDungeon.actionManager.addToTop(new DamageAction(
					mo, new DamageInfo(this.owner, this.amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.NONE));
		}
	}
}