package constructmod.powers;

import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import constructmod.ConstructMod;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.actions.unique.*;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.*;

public class ReactiveShieldPower extends AbstractPower
{
    public static final String POWER_ID = ConstructMod.makeID("ReactiveShield");
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    
    public ReactiveShieldPower(final AbstractCreature owner, final int newAmount) {
        this.name = powerStrings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = newAmount;
        this.updateDescription();
        ConstructMod.setPowerImages(this);
    }
    
    @Override
    public void onGainedBlock(final float blockAmount) {
        if (blockAmount > 0.0f) {
            this.flash();
            AbstractDungeon.actionManager.addToTop(new SwordBoomerangAction(getLowestHPMonster(), new DamageInfo(this.owner, this.amount, DamageInfo.DamageType.THORNS), 1));
        }
    }
    
    public AbstractMonster getLowestHPMonster() {
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            return null;
        }
        int lowestHp = 9000;
        AbstractMonster tmp = null;
        for (final AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if (!m.halfDead && !m.isDying && !m.isEscaping && m.currentHealth < lowestHp) {
                tmp = m;
                lowestHp = m.currentHealth;
            }
        }
        return tmp;
    }
    
    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],this.amount);
    }
}
