package constructmod.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.Soul;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.DeckPoofEffect;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import com.megacrit.cardcrawl.vfx.combat.ScreenOnFireEffect;
import constructmod.ConstructMod;
import constructmod.powers.AbstractCyclePower;

import java.util.ArrayList;

public class OverheatAction extends AbstractGameAction
{
    private AbstractCard targetCard;

    public static float DISCARD_X = Settings.WIDTH * 0.96f;
    public static float DISCARD_Y = Settings.HEIGHT * 0.06f;
    public static float DRAW_PILE_X = Settings.WIDTH * 0.04f;
    public static float DRAW_PILE_Y = Settings.HEIGHT * 0.06f;

    public  boolean triggerOnOverheat;

    public OverheatAction(final AbstractCard targetCard, boolean triggerOnOverheat) {
        this.targetCard = targetCard;
        this.actionType = ActionType.SPECIAL;
        this.triggerOnOverheat = triggerOnOverheat;
    }
    
    @Override
    public void update() {

        if (this.targetCard == null){
            this.isDone = true;
            return;
        }

        ArrayList<AbstractCard> group = null;
        float effect_x = 0f;
        float effect_y = 0f;

        AbstractDungeon.actionManager.cardQueue.removeIf((item)->item.card != null && item.card.equals(this.targetCard));

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
        else if (AbstractDungeon.player.limbo.contains(this.targetCard)){
            group = AbstractDungeon.player.limbo.group;
            effect_x = this.targetCard.current_x;
            effect_y = this.targetCard.current_y;
        }
        else if (AbstractDungeon.player.cardInUse == this.targetCard){
            group = AbstractDungeon.player.discardPile.group;
            effect_x = this.targetCard.current_x;
            effect_y = this.targetCard.current_y;

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
            AbstractDungeon.player.cardInUse = burn;
            group.add(0,burn);
        }

        if (group == null){
            this.isDone = true;
            return;
        }
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

        if (this.triggerOnOverheat) {
            for (AbstractPower p : AbstractDungeon.player.powers) {
                if (p instanceof AbstractCyclePower) ((AbstractCyclePower) p).onOverheatCard(this.targetCard);
            }
        }

        this.isDone = true;
    }
}
