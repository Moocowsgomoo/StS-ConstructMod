package constructmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import constructmod.ConstructMod;

public class ZapperPower extends AbstractPower {
	public static final String POWER_ID = ConstructMod.makeID("Zapper");
	public static final String NAME = "Zapper";
	public static final String[] DESCRIPTIONS = new String[] {
			"Whenever you gain #yStrength or #yDexterity, deal #b",
			" damage to a random enemy. It loses 1 #yStrength this turn."
	};
	
	public ZapperPower(AbstractCreature owner, int amount) {
		this.name = NAME;
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
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
	}
	
	@Override
	public void onApplyPower(final AbstractPower power, final AbstractCreature target, final AbstractCreature source) {
		if ((power.ID != "Strength" && power.ID != "Dexterity") || power.amount <= 0 || target != this.owner) return;
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