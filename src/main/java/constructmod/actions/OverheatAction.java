package constructmod.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.Soul;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.vfx.combat.DeckPoofEffect;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import com.megacrit.cardcrawl.vfx.combat.ScreenOnFireEffect;
import constructmod.ConstructMod;

import java.util.ArrayList;

public class OverheatAction extends AbstractGameAction
{
    private AbstractCard targetCard;

    public static float DISCARD_X = Settings.WIDTH * 0.96f;
    public static float DISCARD_Y = Settings.HEIGHT * 0.06f;
    public static float DRAW_PILE_X = Settings.WIDTH * 0.04f;
    public static float DRAW_PILE_Y = Settings.HEIGHT * 0.06f;

    public OverheatAction(final AbstractCard targetCard) {
        this.targetCard = targetCard;
        this.actionType = ActionType.SPECIAL;
    }
    
    @Override
    public void update() {
        ArrayList<AbstractCard> group = null;
        float effect_x = 0f;
        float effect_y = 0f;
        if (AbstractDungeon.player.drawPile.contains(this.targetCard)){
            group = AbstractDungeon.player.drawPile.group;
            effect_x = DRAW_PILE_X;
            effect_y = DRAW_PILE_Y;
        }
        else if (AbstractDungeon.player.hand.contains(this.targetCard)){
            group = AbstractDungeon.player.hand.group;
            effect_x = this.targetCard.current_x;
            effect_y = this.targetCard.current_y + this.targetCard.hb.height*0.25f;
        }
        else if (AbstractDungeon.player.discardPile.contains(this.targetCard)){
            group = AbstractDungeon.player.discardPile.group;
            effect_x = DISCARD_X;
            effect_y = DISCARD_Y;
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
            burn.transparency = targetCard.transparency;
            AbstractDungeon.topLevelEffects.add(new FlashAtkImgEffect(effect_x, effect_y, AttackEffect.FIRE));
            AbstractDungeon.topLevelEffects.add(new ExplosionSmallEffect(effect_x,effect_y));
            AbstractDungeon.topLevelEffects.add(new DeckPoofEffect(effect_x,effect_y,false));
            CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.HIGH, ScreenShake.ShakeDur.SHORT,false);
            group.add(index,burn);
        }
        group.remove(this.targetCard);
        this.isDone = true;
    }
}
