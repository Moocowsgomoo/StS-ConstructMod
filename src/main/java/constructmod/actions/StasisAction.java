package constructmod.actions;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.localization.*;

import constructmod.cards.AbstractConstructCard;
import constructmod.powers.ConstructStasisPower;

import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.common.*;
import java.util.*;
import com.megacrit.cardcrawl.core.*;

public class StasisAction extends AbstractGameAction
{
    private static final float DURATION_PER_CARD = 0.25f;
    private AbstractPlayer p;
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("constructActionText");
    private ArrayList<AbstractCard> cannotDuplicate;
    private AbstractCard returnedStasisCard = null;
    private int prevAmount=0;
    
    public StasisAction(final AbstractCreature source, final int amount) {
        this.setValues(AbstractDungeon.player, source, amount);
        this.actionType = ActionType.EXHAUST;
        this.duration = 0.25f;
        this.p = AbstractDungeon.player;
        this.cannotDuplicate = new ArrayList<AbstractCard>();
    }
    
    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {

        	// find previously stored stasis card
        	if (p.hasPower(ConstructStasisPower.POWER_ID)) {
        		this.returnedStasisCard = ((ConstructStasisPower)p.getPower(ConstructStasisPower.POWER_ID)).heldCard;
        		this.prevAmount = ((ConstructStasisPower)p.getPower(ConstructStasisPower.POWER_ID)).amount;
        		p.powers.remove(p.getPower(ConstructStasisPower.POWER_ID));
        	}
        	// Return previous stasis card to hand after this action
            if (returnedStasisCard != null) {
        		for (int j=0;j<5;j++) {
        			if (returnedStasisCard instanceof AbstractConstructCard) ((AbstractConstructCard)returnedStasisCard).upgrade(true, true);
            		else if (returnedStasisCard.canUpgrade()) returnedStasisCard.upgrade();
        		}
        		AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(returnedStasisCard,this.prevAmount));
            }
        	
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
                    	AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p,p,new ConstructStasisPower(this.p, this.amount, c),this.amount));
                        AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(c, this.p.hand));
                        this.isDone = true;
                        return;
                    }
                }
            }
            this.p.hand.group.removeAll(this.cannotDuplicate);
            if (this.p.hand.group.size() > 1) {
                AbstractDungeon.handCardSelectScreen.open(uiStrings.TEXT[3], 1, false, false, false, false);
                this.tickDuration();
                return;
            }
            if (this.p.hand.group.size() == 1) {
            	AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p,p,new ConstructStasisPower(this.p, this.amount, p.hand.getTopCard()),this.amount));
                AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(this.p.hand.getTopCard(), this.p.hand));
                this.returnCards();
                this.isDone = true;
            }
        }
        
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (final AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
            	AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p,p,new ConstructStasisPower(this.p, this.amount, c),this.amount));
                this.p.hand.moveToExhaustPile(c);
                CardCrawlGame.dungeon.checkForPactAchievement();
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
        return true;//card instanceof AbstractConstructCard;
    }
}
