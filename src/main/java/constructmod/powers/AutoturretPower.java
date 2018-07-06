package constructmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import constructmod.ConstructMod;

public class AutoturretPower extends AbstractCyclePower {
	public static final String POWER_ID = "Autoturret";
	public static final String NAME = "Autoturret";
	public static final String[] DESCRIPTIONS = new String[] {
			"Whenever a card cycles, deal ",
			" damage to a random enemy."
	};
	
	public AutoturretPower(AbstractCreature owner, int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		updateDescription();
		this.type = AbstractPower.PowerType.BUFF;
		this.isTurnBased = false;
		this.img = new Texture("img/powers/autoturret.png");
	}
	
	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
	}
	
	@Override
	public void onCycleCard(AbstractCard card) {
		flash();
		AbstractDungeon.actionManager.addToTop(new DamageRandomEnemyAction(
				new DamageInfo(this.owner, this.amount, DamageInfo.DamageType.THORNS),AbstractGameAction.AttackEffect.BLUNT_LIGHT));
	}
}