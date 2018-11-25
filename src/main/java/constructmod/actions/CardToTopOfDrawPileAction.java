package constructmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import constructmod.ConstructMod;
import constructmod.powers.AbstractCyclePower;

public class CardToTopOfDrawPileAction extends AbstractGameAction
{
    private AbstractCard targetCard;

    public CardToTopOfDrawPileAction(final AbstractCard targetCard) {
        this.targetCard = targetCard;
        this.actionType = ActionType.CARD_MANIPULATION;
    }
    
    @Override
    public void update() {
        if (!AbstractDungeon.player.hand.contains(this.targetCard)){
            this.isDone = true;
            return;
        }

        AbstractDungeon.player.hand.moveToDeck(this.targetCard,false);

        this.isDone = true;
    }
}
