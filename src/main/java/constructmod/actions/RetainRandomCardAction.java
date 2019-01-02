package constructmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import constructmod.relics.ClawGrip;

public class RetainRandomCardAction extends AbstractGameAction{


    public RetainRandomCardAction(int amount) {
        this.duration = Settings.ACTION_DUR_XFAST;
        this.amount = amount;
    }
    
    @Override
    public void update() {
    	final CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    	tmp.clear();
        for (final AbstractCard c : AbstractDungeon.player.hand.group) {
            if (!c.retain && ((c.type != CardType.STATUS && c.type != CardType.CURSE)||c.cost > -2)) {
                tmp.addToTop(c);
            }
        }
    	while (tmp.size() > 0 && this.amount > 0) {
    		AbstractCard c2 = tmp.getRandomCard(true);
    		c2.retain = true;
    		c2.flash();
    		tmp.removeCard(c2);
    		this.amount--;
    	}
        this.isDone = true;
    }
}
