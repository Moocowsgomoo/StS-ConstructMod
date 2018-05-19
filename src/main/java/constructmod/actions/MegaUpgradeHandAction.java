package constructmod.actions;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;

import constructmod.cards.AbstractConstructCard;
import com.megacrit.cardcrawl.cards.*;

public class MegaUpgradeHandAction extends AbstractGameAction
{
	
    public MegaUpgradeHandAction() {
        this.duration = Settings.ACTION_DUR_XFAST;
    }
    
    @Override
    public void update() {
    	int i;
        for (final AbstractCard c:AbstractDungeon.player.hand.group) {
        	for (i=0;i<5;i++) {
        		if (c instanceof AbstractConstructCard) ((AbstractConstructCard)c).upgrade(true,true);
            	else c.upgrade();
        	}
        }
        this.isDone = true;
    }
}
