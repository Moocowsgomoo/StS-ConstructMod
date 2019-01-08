package constructmod.actions;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.common.*;
import java.util.*;
import com.megacrit.cardcrawl.core.*;

public class CopyCardToDiscardPileAction extends AbstractGameAction
{
    private static final float DURATION_PER_CARD = 0.25f;
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("constructActionText");
    private AbstractPlayer p;
    private int dupeAmount;
    private ArrayList<AbstractCard> cannotDuplicate;
    
    public CopyCardToDiscardPileAction(final AbstractCreature source, final int amount) {
        this.dupeAmount = amount;
        this.cannotDuplicate = new ArrayList<AbstractCard>();
        this.setValues(AbstractDungeon.player, source, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = 0.25f;
        this.p = AbstractDungeon.player;
    }
    
    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            for (final AbstractCard c : this.p.hand.group) {
                if (!this.isCopiable(c)) {
                    this.cannotDuplicate.add(c);
                }
            }
            if (this.cannotDuplicate.size() == this.p.hand.group.size()) {
                this.isDone = true;
                return;
            }
            if (this.p.hand.group.size() - this.cannotDuplicate.size() == 1) {
                for (final AbstractCard c : this.p.hand.group) {
                    if (this.isCopiable(c)) {
                        AbstractDungeon.actionManager.addToTop(new MakeTempCardInDiscardAction(
                        		c.makeStatEquivalentCopy(),dupeAmount));
                        this.isDone = true;
                        return;
                    }
                }
            }
            this.p.hand.group.removeAll(this.cannotDuplicate);
            if (this.p.hand.group.size() > 1) {
                AbstractDungeon.handCardSelectScreen.open(uiStrings.TEXT[0], 1, false, false, false, false);
                this.tickDuration();
                return;
            }
            if (this.p.hand.group.size() == 1) {
            	AbstractDungeon.actionManager.addToTop(new MakeTempCardInDiscardAction(
            			p.hand.getTopCard().makeStatEquivalentCopy(),dupeAmount));
                this.returnCards();
                this.isDone = true;
            }
        }
        
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (final AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
            	AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(c.makeStatEquivalentCopy()));
            	AbstractDungeon.actionManager.addToTop(new MakeTempCardInDiscardAction(
                		c.makeStatEquivalentCopy(),dupeAmount));
            }
            this.returnCards();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
        }
        this.tickDuration();
    }
    
    private void returnCards() {
        for (final AbstractCard c : this.cannotDuplicate) {
            this.p.hand.addToTop(c);
        }
        this.p.hand.refreshHandLayout();
    }
    
    private boolean isCopiable(final AbstractCard card) {
        return !card.rarity.equals(AbstractCard.CardRarity.RARE);
    }
}
