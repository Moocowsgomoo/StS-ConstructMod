package constructmod.actions;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.core.*;

public class GainMaxHPAction extends AbstractGameAction
{
    public GainMaxHPAction(final AbstractCreature target, final AbstractCreature source, final int amount) {
    	this.duration = Settings.ACTION_DUR_XFAST;
        this.setValues(target, source, amount);
        this.actionType = ActionType.SPECIAL;
    }
    
    @Override
    public void update() {
    	if (amount < 0) this.target.decreaseMaxHealth(-amount);
    	else this.target.increaseMaxHp(amount, true);
    	
    	this.isDone = true;
    }
}
