package constructmod.actions;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;

import constructmod.relics.ClawGrip;

import com.megacrit.cardcrawl.cards.*;
import java.util.*;

public class ClawGripAction extends AbstractGameAction
{
	private ClawGrip grip;
	
    public ClawGripAction(ClawGrip grip) {
        this.duration = Settings.ACTION_DUR_XFAST;
        this.grip = grip;
    }
    
    @Override
    public void update() {
    	final CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    	tmp.clear();
        for (final AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.costForTurn > 0 || !c.retain) {
                tmp.addToTop(c);
            }
        }
    	if (tmp.size() > 0) {
    		AbstractCard c2 = tmp.getRandomCard(true);
    		c2.flash();
    		c2.retain = true;
    		//c2.setCostForTurn(c2.costForTurn-amount);
    		this.grip.card = c2;
    	}
        this.isDone = true;
    }
}
