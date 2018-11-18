package constructmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import constructmod.ConstructMod;
import constructmod.cards.AbstractConstructCard;
import constructmod.powers.AbstractCyclePower;

public class CheckOverheatAction extends AbstractGameAction
{

    public CheckOverheatAction() {
        this.actionType = ActionType.SPECIAL;
    }
    
    @Override
    public void update() {
        for (AbstractCard c:AbstractDungeon.player.drawPile.group){
            if (c instanceof AbstractConstructCard){
                ((AbstractConstructCard) c).checkOverheat();
            }
        }
        for (AbstractCard c:AbstractDungeon.player.hand.group){
            if (c instanceof AbstractConstructCard){
                ((AbstractConstructCard) c).checkOverheat();
            }
        }
        for (AbstractCard c:AbstractDungeon.player.discardPile.group){
            if (c instanceof AbstractConstructCard){
                ((AbstractConstructCard) c).checkOverheat();
            }
        }
        for (AbstractCard c:AbstractDungeon.player.limbo.group){
            if (c instanceof AbstractConstructCard){
                ((AbstractConstructCard) c).checkOverheat();
            }
        }
        if (AbstractDungeon.player.cardInUse instanceof AbstractConstructCard){
            ((AbstractConstructCard) AbstractDungeon.player.cardInUse).checkOverheat();
        }
        this.isDone = true;
    }
}
