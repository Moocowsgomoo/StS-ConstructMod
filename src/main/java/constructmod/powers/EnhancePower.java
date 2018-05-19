package constructmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import basemod.BaseMod;
import basemod.interfaces.PostDrawSubscriber;
import constructmod.ConstructMod;
import constructmod.cards.AbstractConstructCard;

public class EnhancePower extends AbstractPower {
	public static final String POWER_ID = "Enhance";
	public static final String NAME = "Enhance";
	public static final String[] DESCRIPTIONS = new String[] {
			"At the end of your turn, upgrade ",
			" card(s) in your discard pile."
	};
	
	private boolean canMegaUpgrade = false;
	
	public EnhancePower(AbstractCreature owner, int amount, boolean canMegaUpgrade) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		this.type = AbstractPower.PowerType.BUFF;
		this.isTurnBased = false;
		this.img = new Texture("img/powers/enhance.png");
		this.canMegaUpgrade = canMegaUpgrade;
		updateDescription();
	}
	
	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
	}
	
	@Override
	public void atEndOfTurn(final boolean isPlayer) {
		if (!isPlayer) return;
		final CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
		for (final AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if (c.canUpgrade() || (this.canMegaUpgrade && c instanceof AbstractConstructCard && ((AbstractConstructCard)c).canUpgrade(true))) {
                tmp.addToRandomSpot(c);
            }
        }
		if (tmp.size() > 0) this.flash();
		for (int i=0;i<this.amount;i++) {
			if (tmp.size() > 0) {
				final AbstractCard c = tmp.getTopCard();
				if (this.canMegaUpgrade && c instanceof AbstractConstructCard) ((AbstractConstructCard)c).upgrade(true,true);
				else c.upgrade();
				ConstructMod.logger.info("Enhance upgraded " + tmp.getTopCard());
				tmp.removeTopCard();
			}
		}
		tmp.clear();
	}
}