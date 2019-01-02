package constructmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import constructmod.cards.AbstractConstructCard;
import constructmod.patches.AbstractCardEnum;
import constructmod.patches.TheConstructEnum;

public class SupernovaAction extends AbstractGameAction
{

    boolean upgradeCards;

    public SupernovaAction(boolean upgradeCards) {
        this.upgradeCards = upgradeCards;
    }
    
    @Override
    public void update() {
        AbstractCard newCard;
    	for (final AbstractCard c:AbstractDungeon.player.drawPile.group) {
            if (c.type == AbstractCard.CardType.STATUS) {
                AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(c,AbstractDungeon.player.hand,true));
                newCard = AbstractDungeon.returnTrulyRandomCardInCombat().makeCopy();
                newCard.modifyCostForCombat(-99);
                if(upgradeCards) newCard.upgrade();
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(newCard,1,true,true));
            }
        }
        for (final AbstractCard c:AbstractDungeon.player.hand.group) {
            if (c.type == AbstractCard.CardType.STATUS) {
                AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(c,AbstractDungeon.player.hand,true));
                newCard = AbstractDungeon.returnTrulyRandomCardInCombat().makeCopy();
                newCard.modifyCostForCombat(-99);
                if(upgradeCards) newCard.upgrade();
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(newCard));
            }
        }
        for (final AbstractCard c:AbstractDungeon.player.discardPile.group) {
            if (c.type == AbstractCard.CardType.STATUS) {
                AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(c,AbstractDungeon.player.hand,true));
                newCard = AbstractDungeon.returnTrulyRandomCardInCombat().makeCopy();
                newCard.modifyCostForCombat(-99);
                if(upgradeCards) newCard.upgrade();
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(newCard,1));
            }
        }
        this.isDone = true;
    }
}
