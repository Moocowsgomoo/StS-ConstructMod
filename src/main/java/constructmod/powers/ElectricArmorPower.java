package constructmod.powers;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import constructmod.ConstructMod;
import org.apache.logging.log4j.*;
import com.megacrit.cardcrawl.core.*;

public class ElectricArmorPower extends AbstractPower
{
    public static final String POWER_ID = "construct:ElectricArmor";
    public static final String[] DESCRIPTIONS = new String[] {
            "When attacked, deals damage back equal to your #yDexterity."
    };
    
    public ElectricArmorPower(final AbstractCreature owner, final int amount) {
        this.name = "Electric Armor";
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.isTurnBased = true;
        ConstructMod.setPowerImages(this);
    }

    @Override
    public void atEndOfRound() {
        if (this.amount == 0) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        }
        else {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
        }
    }
    
    @Override
    public int onAttacked(final DamageInfo info, final int damageAmount) {
        if (info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != null && info.owner != this.owner && this.owner.hasPower(DexterityPower.POWER_ID) && this.owner.getPower(DexterityPower.POWER_ID).amount > 0) {
            this.flash();
            AbstractDungeon.actionManager.addToTop(new DamageAction(info.owner,
                    new DamageInfo(this.owner,this.owner.getPower(DexterityPower.POWER_ID).amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SLASH_VERTICAL, true));
        }
        return damageAmount;
    }
    
    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
