package constructmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import constructmod.ConstructMod;

public class PanicFirePower extends AbstractCyclePower {
	public static final String POWER_ID = "OrbAssault";
	public static final String NAME = "Panic Fire";
	public static final String[] DESCRIPTIONS = new String[] {
			"Whenever a card cycles, exhaust it and deal ",
			" damage to a random enemy."
	};
	
	public PanicFirePower(AbstractCreature owner, int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		updateDescription();
		this.type = AbstractPower.PowerType.BUFF;
		this.isTurnBased = false;
		this.img = new Texture("img/powers/panic_fire.png");
	}
	
	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
	}
	
	@Override
	public void onCycleCard(AbstractCard card) {
		flash();
		AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, AbstractDungeon.player.drawPile));
		AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, AbstractDungeon.player.discardPile));
		AbstractDungeon.actionManager.addToBottom(new DamageRandomEnemyAction(
				new DamageInfo(null, this.amount, DamageInfo.DamageType.THORNS),AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
	}
}