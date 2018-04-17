package constructmod.actions;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.*;

public class CripplingDamageAction extends AbstractGameAction
{
    private DamageInfo info;
    
    public CripplingDamageAction(final AbstractCreature target, final DamageInfo info, final AttackEffect effect) {
        this.setValues(target, this.info = info);
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = effect;
    }
    
    @Override
    public void update() {
        if (this.duration == 0.5f) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect));
        }
        this.tickDuration();
        if (this.isDone) {
            this.debuff(this.info);
            this.target.damage(this.info);
            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }
    }
    
    private void debuff(final DamageInfo info) {
        int dmgAmount = info.output;
        dmgAmount -= this.target.currentBlock;
        if (dmgAmount > 0) {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(
            		this.target, this.source, new WeakPower(this.target,99,false), 99, true, AbstractGameAction.AttackEffect.NONE));
            AbstractDungeon.actionManager.addToTop(new WaitAction(0.1f));
        }
    }
}
