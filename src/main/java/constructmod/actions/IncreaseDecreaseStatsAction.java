package constructmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class IncreaseDecreaseStatsAction extends AbstractGameAction
{

	public AbstractPlayer p;

    public IncreaseDecreaseStatsAction(AbstractPlayer p, int amount) {
    	this.p = p;
    	this.amount = amount;
    }
    
    @Override
    public void update() {
		final int str = p.hasPower(StrengthPower.POWER_ID)?p.getPower(StrengthPower.POWER_ID).amount:0;
		final int dex = p.hasPower(DexterityPower.POWER_ID)?p.getPower(DexterityPower.POWER_ID).amount:0;
		if (dex>str){
			AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new DexterityPower(p, this.amount), this.amount));
			AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new StrengthPower(p, -this.amount), -this.amount));
		}
		else if (str>dex){
			AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new StrengthPower(p, this.amount), this.amount));
			AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new DexterityPower(p, -this.amount), -this.amount));
		}
        this.isDone = true;
    }
}
