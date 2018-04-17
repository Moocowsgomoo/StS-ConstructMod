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

public class EnhancePower extends AbstractPower {
	public static final String POWER_ID = "Enhance";
	public static final String NAME = "Enhance";
	public static final String[] DESCRIPTIONS = new String[] {
			"At the end of your turn, upgrade ",
			" card(s) in your discard pile."
	};
	
	public EnhancePower(AbstractCreature owner, int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		updateDescription();
		this.type = AbstractPower.PowerType.BUFF;
		this.isTurnBased = false;
		this.img = new Texture("img/powers/enhance.png");
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
            if (c.canUpgrade()) {
                tmp.addToRandomSpot(c);
            }
        }
		for (int i=0;i<this.amount;i++) {
			if (tmp.size() > 0) {
				this.flash();
				tmp.getTopCard().upgrade();
				ConstructMod.logger.info("Enhance upgraded " + tmp.getTopCard());
				tmp.removeTopCard();
			}
		}
		tmp.clear();
	}
}