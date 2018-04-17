package constructmod.actions;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.vfx.combat.*;
import com.megacrit.cardcrawl.actions.utility.*;

public class DealMultiRandomDamageAction extends AbstractGameAction
{
    private DamageInfo info;
    private static final float DURATION = 0.01f;
    private static final float POST_ATTACK_WAIT_DUR = 0.2f;
    private int numTimes;
    
    public DealMultiRandomDamageAction(final DamageInfo info, final int numTimes, final AttackEffect atkEffect) {
        this.info = info;
        this.target = AbstractDungeon.getMonsters().getRandomMonster(true);
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = atkEffect;
        this.duration = 0.01f;
        this.numTimes = numTimes;
    }
    
    @Override
    public void update() {
        if (this.target == null) {
            this.isDone = true;
            return;
        }
        if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
            AbstractDungeon.actionManager.clearPostCombatActions();
            this.isDone = true;
            return;
        }
        if (this.target.currentHealth > 0) {
            this.target.damageFlash = true;
            this.target.damageFlashFrames = 4;
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect));
            this.info.applyPowers(this.info.owner, this.target);
            this.target.damage(this.info);
            if (this.numTimes > 1 && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                --this.numTimes;
                AbstractDungeon.actionManager.addToTop(new DealMultiRandomDamageAction(this.info, this.numTimes, this.attackEffect));
            }
            AbstractDungeon.actionManager.addToTop(new WaitAction(0.2f));
        }
        this.isDone = true;
    }
}
