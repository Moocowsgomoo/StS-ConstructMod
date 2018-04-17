package constructmod.actions;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.cards.*;
import java.util.*;

public class DiscountRandomCardAction extends AbstractGameAction
{
    public DiscountRandomCardAction(int discount) {
        this.duration = Settings.ACTION_DUR_XFAST;
        this.amount = discount;
    }
    
    @Override
    public void update() {
    	final CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (final AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.costForTurn > 0) {
                tmp.addToRandomSpot(c);
            }
        }
    	if (tmp.size() > 0) {
    		AbstractCard c2 = tmp.getTopCard();
    		c2.flash();
    		c2.setCostForTurn(c2.costForTurn-amount);
    	}
        this.isDone = true;
    }
}
