package constructmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import constructmod.relics.ClawGrip;

public class SunScreenAction extends AbstractGameAction
{
	public CardType cardType;

    public SunScreenAction(int amount, CardType cType) {
        this.duration = Settings.ACTION_DUR_XFAST;
        this.amount = amount;
        this.cardType = cType;
    }
    
    @Override
    public void update() {
    	final CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    	tmp.clear();
        for (final AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.type == cardType) {
                tmp.addToTop(c);
            }
        }
    	if (tmp.size() > 0) {
    		AbstractCard c2 = tmp.getRandomCard(true);
            AbstractDungeon.actionManager.addToTop(new GainBlockAction(AbstractDungeon.player,AbstractDungeon.player,this.amount));
    		AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(c2,AbstractDungeon.player.hand));
    	}
        this.isDone = true;
    }
}
