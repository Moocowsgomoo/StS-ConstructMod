package constructmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class ShiftingStanceAction extends AbstractGameAction
{

	public AbstractPlayer p;
    public boolean alsoIncrease = false;

    public ShiftingStanceAction(AbstractPlayer p,boolean alsoIncrease) {
    	this.p = p;
    	this.alsoIncrease = alsoIncrease;
    }
    
    @Override
    public void update() {
		AbstractDungeon.actionManager.addToBottom(new ShiftStatsAction(p,alsoIncrease));
        this.isDone = true;
    }
}
