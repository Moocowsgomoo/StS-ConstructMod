package constructmod.actions;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.common.*;
import java.util.*;
import com.megacrit.cardcrawl.core.*;

public class BackupAction extends AbstractGameAction
{
    public static final String TEXT;
    private static final float DURATION_PER_CARD = 0.25f;
    private AbstractPlayer p;
    private int dupeAmount;
    private ArrayList<AbstractCard> cannotDuplicate;
    private boolean randomSpot;
    
    public BackupAction(final AbstractCreature source, final int amount, final boolean randomSpot) {
        this.dupeAmount = amount;
        this.cannotDuplicate = new ArrayList<AbstractCard>();
        this.setValues(AbstractDungeon.player, source, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = 0.25f;
        this.p = AbstractDungeon.player;
        this.randomSpot = randomSpot;
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
                        AbstractDungeon.actionManager.addToTop(new MakeTempCardInDrawPileAction(
                        		p,p,c.makeStatEquivalentCopy(),dupeAmount,randomSpot,false));
                        this.isDone = true;
                        return;
                    }
                }
            }
            this.p.hand.group.removeAll(this.cannotDuplicate);
            if (this.p.hand.group.size() > 1) {
                AbstractDungeon.handCardSelectScreen.open(BackupAction.TEXT, 1, false, false, false, false);
                this.tickDuration();
                return;
            }
            if (this.p.hand.group.size() == 1) {
            	AbstractDungeon.actionManager.addToTop(new MakeTempCardInDrawPileAction(
            			p,p,p.hand.getTopCard().makeStatEquivalentCopy(),dupeAmount,randomSpot,false));
                this.returnCards();
                this.isDone = true;
            }
        }
        
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (final AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
            	AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(c.makeStatEquivalentCopy()));
            	AbstractDungeon.actionManager.addToTop(new MakeTempCardInDrawPileAction(
                		p,p,c.makeStatEquivalentCopy(),dupeAmount,randomSpot,false));
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
    
    static {
        TEXT = "copy.";
    }
}
