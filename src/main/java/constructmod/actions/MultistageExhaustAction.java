package constructmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import constructmod.cards.AbstractConstructCard;
import constructmod.powers.ConstructStasisPower;
import constructmod.powers.MultistagePower;

import java.util.ArrayList;

public class MultistageExhaustAction extends AbstractGameAction
{
    private AbstractPlayer p;
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("constructActionText");
    private ArrayList<AbstractCard> cannotDuplicate;

    public MultistageExhaustAction(final AbstractCreature source, final int amount) {
        this.setValues(AbstractDungeon.player, source, amount);
        this.actionType = ActionType.EXHAUST;
        this.duration = Settings.ACTION_DUR_FAST;
        this.p = AbstractDungeon.player;
        this.amount = amount;
        this.cannotDuplicate = new ArrayList<AbstractCard>();
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
                    	AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p,p,new MultistagePower(this.p, this.amount, c),this.amount));
                        AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(c, this.p.hand));
                        this.isDone = true;
                        return;
                    }
                }
            }
            this.p.hand.group.removeAll(this.cannotDuplicate);
            if (this.p.hand.group.size() > 1) {
                AbstractDungeon.handCardSelectScreen.open(uiStrings.TEXT[1], 1, false, false, false, false);
                this.tickDuration();
                return;
            }
            //if (this.p.hand.group.size() == 1) {
            //	AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p,p,new MultistagePower(this.p, this.amount, p.hand.getTopCard()),this.amount));
            //    AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(this.p.hand.getTopCard(), this.p.hand));
            //    this.returnCards();
            //    this.isDone = true;
            //}
        }
        
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (final AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
            	AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p,p,new MultistagePower(this.p, this.amount, c),this.amount));
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
        return card.cost >= 0 && card.costForTurn <= this.amount && card.type == AbstractCard.CardType.ATTACK;
    }
}
