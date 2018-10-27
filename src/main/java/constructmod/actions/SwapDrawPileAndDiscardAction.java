package constructmod.actions;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.common.*;
import java.util.*;
import com.megacrit.cardcrawl.core.*;

public class SwapDrawPileAndDiscardAction extends AbstractGameAction
{
	
	private AbstractPlayer p;
	private int count;
    
    public SwapDrawPileAndDiscardAction() {
        this.setValues(AbstractDungeon.player, source, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.p = AbstractDungeon.player;
    }
    
    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            CardGroup tmpDraw = new CardGroup(CardGroupType.UNSPECIFIED);
            tmpDraw.group.addAll(p.drawPile.group);
            CardGroup tmpDiscard = new CardGroup(CardGroupType.UNSPECIFIED);
            tmpDiscard.group.addAll(p.discardPile.group);
            
           for (AbstractCard c : tmpDiscard.group) {
                ++this.count;
                if (this.count < 6) {
                    AbstractDungeon.getCurrRoom().souls.shuffle(c, false);
                }
                else {
                    AbstractDungeon.getCurrRoom().souls.shuffle(c, true);
                }
                p.discardPile.removeCard(c);
            }

            this.count = 0;
            for (AbstractCard c2 : tmpDraw.group) {
                ++this.count;
                c2.darken(false);
                c2.shrink();
                if (p.hoveredCard == c2) {
                    p.releaseCard();
                }
                AbstractDungeon.actionManager.removeFromQueue(c2);
                c2.unhover();
                c2.untip();
                c2.stopGlowing();
                p.drawPile.group.remove(c2);
                if (this.count < 10) AbstractDungeon.getCurrRoom().souls.discard(c2);
                else p.discardPile.addToTop(c2);
                AbstractDungeon.player.onCardDrawOrDiscard();
            }
            
            tmpDraw.clear();
            tmpDiscard.clear();
            
        }
        this.tickDuration();
    }
}
