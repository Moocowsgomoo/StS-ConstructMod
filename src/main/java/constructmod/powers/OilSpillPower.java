package constructmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.powers.AbstractPower;
import constructmod.ConstructMod;

public class OilSpillPower extends AbstractPower{
	public static final String POWER_ID = ConstructMod.makeID("OilSpill");
	public static final String NAME = "Oil";
	public static final String[] DESCRIPTIONS = new String[] {
			"Whenever a #yBurn card hits you, deal #b",
			" damage to ",
			"."
	};

	public OilSpillPower(AbstractCreature owner, int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		updateDescription();
		this.type = PowerType.DEBUFF;
		this.isTurnBased = false;
		ConstructMod.setPowerImages(this);
	}
	
	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + FontHelper.colorString(this.owner.name, "y") + DESCRIPTIONS[2];
	}

	public void onBurnDamage () {
		this.flash();
		AbstractDungeon.actionManager.addToBottom(new DamageAction(
				this.owner,new DamageInfo(this.owner,this.amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));

	}
}