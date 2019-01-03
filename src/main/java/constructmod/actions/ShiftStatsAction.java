package constructmod.actions;

import basemod.helpers.BaseModCardTags;
import basemod.helpers.CardTags;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class ShiftStatsAction extends AbstractGameAction
{

	public AbstractPlayer p;
	public boolean alsoIncrease = false;

    public ShiftStatsAction(AbstractPlayer p, boolean alsoIncrease) {
    	this.p = p;
    	this.alsoIncrease = alsoIncrease;
    }
    
    @Override
    public void update() {
		final int str = p.hasPower(StrengthPower.POWER_ID)?p.getPower(StrengthPower.POWER_ID).amount:0;
		final int dex = p.hasPower(DexterityPower.POWER_ID)?p.getPower(DexterityPower.POWER_ID).amount:0;
		if (alsoIncrease) AbstractDungeon.actionManager.addToTop(new IncreaseDecreaseStatsAction(p,1));
		if (dex-str != 0) AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new StrengthPower(p, dex-str), dex-str));
		if (str-dex != 0) AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new DexterityPower(p, str-dex), str-dex));
        this.isDone = true;
    }
}
