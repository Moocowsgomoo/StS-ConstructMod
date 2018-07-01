package constructmod.powers;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.actions.unique.*;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.core.*;

public class ReactiveShieldPower extends AbstractPower
{
    public static final String POWER_ID = "ReactiveShield";
    public static final String[] DESCRIPTIONS = new String[] {
			"When you gain Block, deal ",
			" damage to the enemy with the lowest HP."
	};
    
    public ReactiveShieldPower(final AbstractCreature owner, final int newAmount) {
        this.name = "Reactive Shield";
        this.ID = "ReactiveShield";
        this.owner = owner;
        this.amount = newAmount;
        this.updateDescription();
        this.img = new Texture("img/powers/reactiveshield.png");
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
        this.description = ReactiveShieldPower.DESCRIPTIONS[0] + this.amount + ReactiveShieldPower.DESCRIPTIONS[1];
    }
}
