package constructmod.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import com.megacrit.cardcrawl.vfx.combat.ScreenOnFireEffect;
import constructmod.ConstructMod;

import java.util.ArrayList;

public class OverheatAction extends AbstractGameAction
{
    private AbstractCard targetCard;

    public OverheatAction(final AbstractCard targetCard) {
        this.targetCard = targetCard;
        this.actionType = ActionType.SPECIAL;
    }
    
    @Override
    public void update() {
        ArrayList<AbstractCard> group = null;
        if (AbstractDungeon.player.drawPile.contains(this.targetCard)){
            group = AbstractDungeon.player.drawPile.group;
        }
        else if (AbstractDungeon.player.hand.contains(this.targetCard)){
            group = AbstractDungeon.player.hand.group;
        }
        else if (AbstractDungeon.player.discardPile.contains(this.targetCard)){
            group = AbstractDungeon.player.discardPile.group;
        }

        if (group == null) return;
        int index = group.indexOf(this.targetCard);
        ConstructMod.logger.debug("CARD INDEX:" + index);
        if (index >= 0 && index < group.size()){
            AbstractCard burn = new Burn();
            burn.current_x = burn.target_x = this.targetCard.current_x;
            burn.current_y = burn.target_y = this.targetCard.current_y;
            burn.angle = this.targetCard.angle;
            burn.drawScale = this.targetCard.drawScale;
            AbstractDungeon.topLevelEffects.add(new FlashAtkImgEffect(this.targetCard.current_x, this.targetCard.current_y + this.targetCard.hb.height*0.25f, AttackEffect.FIRE));
            CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT,false);
            group.add(index,burn);
        }
        group.remove(this.targetCard);
        this.isDone = true;
    }
}
