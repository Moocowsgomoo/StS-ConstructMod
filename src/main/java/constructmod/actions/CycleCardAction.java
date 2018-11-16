package constructmod.actions;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.powers.AbstractPower;

import constructmod.ConstructMod;
import constructmod.cards.AbstractConstructCard;
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
        if (!AbstractDungeon.player.hand.contains(this.targetCard)){
            this.isDone = true;
            return;
        }

        ConstructMod.incrementCycles();
        for (AbstractPower pw : AbstractDungeon.player.powers) {
            if (pw instanceof AbstractCyclePower) {
                ((AbstractCyclePower) pw).onCycleCard(this.targetCard);
            }
        }
        TumbleFollowupAction.onCycleCard(this.targetCard);

        boolean overheatQueued = false; // trying to see if we can interrupt the cycle process to blow stuff up, not easy
        for (AbstractCard c:AbstractDungeon.player.drawPile.group){
            if (c instanceof AbstractConstructCard){
                overheatQueued = overheatQueued || ((AbstractConstructCard) c).checkOverheat();
            }
        }
        for (AbstractCard c:AbstractDungeon.player.hand.group){
            if (c instanceof AbstractConstructCard){
                overheatQueued = overheatQueued || ((AbstractConstructCard) c).checkOverheat();
            }
        }
        for (AbstractCard c:AbstractDungeon.player.discardPile.group){
            if (c instanceof AbstractConstructCard){
                overheatQueued = overheatQueued || ((AbstractConstructCard) c).checkOverheat();
            }
        }
        if (AbstractDungeon.player.cardInUse instanceof AbstractConstructCard){
            overheatQueued = overheatQueued || ((AbstractConstructCard) AbstractDungeon.player.cardInUse).checkOverheat();
        }

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
