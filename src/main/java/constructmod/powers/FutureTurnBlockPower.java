package constructmod.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class FutureTurnBlockPower extends AbstractPower {
    public static final String POWER_ID = "construct:FutureTurnBlock";
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public FutureTurnBlockPower(AbstractCreature owner, int armorAmt) {
        this.name = powerStrings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = armorAmt;
        this.updateDescription();
        this.loadRegion("defenseNext");
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],this.amount);
    }

    public void atStartOfTurn() {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new NextTurnBlockPower(this.owner,this.amount),this.amount));
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this.POWER_ID));
    }
}
