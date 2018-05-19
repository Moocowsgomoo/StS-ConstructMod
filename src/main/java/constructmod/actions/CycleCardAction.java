package constructmod.actions;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.powers.AbstractPower;

import constructmod.powers.AbstractCyclePower;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;

public class CycleCardAction extends AbstractGameAction
{
    private AbstractCard targetCard;
    private boolean isExhaust;
    
    public CycleCardAction(final AbstractCard targetCard, final boolean isExhaust) {
        this.targetCard = targetCard;
        this.actionType = ActionType.DISCARD;
        this.isExhaust = isExhaust;
    }
    
    @Override
    public void update() {    	
    	if (this.isExhaust) {
    		AbstractDungeon.player.hand.moveToExhaustPile(this.targetCard);
    		this.targetCard.triggerOnExhaust();
    	}
    	else {
	        AbstractDungeon.player.hand.moveToDiscardPile(this.targetCard);
	        GameActionManager.incrementDiscard(false);
	        //this.targetCard.triggerOnManualDiscard();
    	}
        
        if (!AbstractDungeon.player.drawPile.isEmpty()) {
            AbstractDungeon.player.draw();
            AbstractDungeon.player.hand.refreshHandLayout();
        }
        else {
        	AbstractDungeon.actionManager.addToTop(new DrawCardAction(AbstractDungeon.player,1));
        }
        this.isDone = true;
    }
}
