package constructmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import constructmod.ConstructMod;
import constructmod.cards.AbstractConstructCard;

public class EnhanceMegaPower extends AbstractPower {
	public static final String POWER_ID = ConstructMod.makeID("EnhanceMega");
	public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

	public EnhanceMegaPower(AbstractCreature owner, int amount) {
		this.name = powerStrings.NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		this.type = PowerType.BUFF;
		this.isTurnBased = false;
		ConstructMod.setPowerImages(this,EnhancePower.POWER_ID);
		updateDescription();
	}
	
	@Override
	public void updateDescription() {
		this.description = String.format(amount==1?DESCRIPTIONS[0]:DESCRIPTIONS[1],this.amount);
	}
	
	@Override
	public void atEndOfTurn(final boolean isPlayer) {
		if (!isPlayer) return;
		final CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
		for (final AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if (c.canUpgrade() || (c instanceof AbstractConstructCard && ((AbstractConstructCard)c).canUpgrade(true))) {
                tmp.addToRandomSpot(c);
            }
        }
		if (tmp.size() > 0) this.flash();
		for (int i=0;i<this.amount;i++) {
			if (tmp.size() > 0) {
				final AbstractCard c = tmp.getTopCard();
				if (c instanceof AbstractConstructCard) ((AbstractConstructCard)c).upgrade(true,true);
				else c.upgrade();
				ConstructMod.logger.info("Enhance upgraded " + tmp.getTopCard());
				tmp.removeTopCard();
			}
		}
		tmp.clear();
	}
}