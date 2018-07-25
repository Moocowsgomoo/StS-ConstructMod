package constructmod.actions;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.ui.panels.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.core.*;

public class GatlingGunAction extends AbstractGameAction
{
    public int damage;
    private boolean freeToPlayOnce;
    private DamageInfo.DamageType damageType;
    private AbstractPlayer p;
    private int energyOnUse;
    private int shotsMult;
    
    public GatlingGunAction(final AbstractPlayer p, final int damage, final DamageInfo.DamageType damageType, final boolean freeToPlayOnce, final int energyOnUse, final int shotsMult) {
        this.freeToPlayOnce = false;
        this.energyOnUse = -1;
        this.damage = damage;
        this.damageType = damageType;
        this.p = p;
        this.freeToPlayOnce = freeToPlayOnce;
        if (freeToPlayOnce) {
            System.out.println("FREE TO PLAY");
        }
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
        this.shotsMult = shotsMult;
    }
    
    @Override
    public void update() {
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
        }
        if (this.p.hasRelic("Chemical X")) {
            effect += 2;
            this.p.getRelic("Chemical X").flash();
        }
        if (effect > 0) {
        	AbstractDungeon.actionManager.addToBottom(new DealMultiRandomDamageAction(
    				new DamageInfo(p, this.damage, this.damageType), effect*shotsMult, AbstractGameAction.AttackEffect.BLUNT_HEAVY,true));
        	
            if (!this.freeToPlayOnce) {
                this.p.energy.use(EnergyPanel.totalCount);
            }
        }
        this.isDone = true;
    }
}
