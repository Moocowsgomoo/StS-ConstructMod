package constructmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.BossCrystalImpactEffect;

import constructmod.ConstructMod;

public class SynchronizePower extends AbstractOnDrawPower{
	public static final String POWER_ID = ConstructMod.makeID("Synchronize");
	public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	
	private String drawnCardName = "";
	
	public SynchronizePower(AbstractCreature owner, int amount) {
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
		if (!drawnCardName.equals("")) this.description = this.description + String.format(DESCRIPTIONS[1],drawnCardName);
	}
	
	@Override
	public void onDrawCard (final AbstractCard c) {
		if (c.originalName.equals(drawnCardName)) {
			this.flash();
			
			//AbstractDungeon.actionManager.addToTop(new DrawCardAction(this.owner,1)); // occurs after damageAll due to action order
			AbstractDungeon.actionManager.addToTop(new DamageAllEnemiesAction(
					this.owner, DamageInfo.createDamageMatrix(this.amount, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
			
			for (final AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
	            if (!mo.isDeadOrEscaped()) {
	                AbstractDungeon.actionManager.addToTop(new VFXAction(new BossCrystalImpactEffect(mo.drawX, mo.drawY), 0.05f));
	            }
	        }
		
		}
		drawnCardName = c.originalName;
		updateDescription();
	}
}