package constructmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import constructmod.ConstructMod;

public class PanicFirePower extends AbstractCyclePower {
	public static final String POWER_ID = ConstructMod.makeID("PanicFire");
	public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	
	public PanicFirePower(AbstractCreature owner, int amount) {
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
		
		super.onCycleCard(card);
		
		if (card.upgraded) return;
		
		flash();
		
		// exhaust handled by CycleCardAction already (hard-coded to Panic Fire for now)
		
		//AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, AbstractDungeon.player.drawPile));
		//AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, AbstractDungeon.player.discardPile));
		AbstractDungeon.actionManager.addToTop(new DamageRandomEnemyAction(
				new DamageInfo(this.owner, this.amount, DamageInfo.DamageType.THORNS),AbstractGameAction.AttackEffect.BLUNT_HEAVY));
	}
}