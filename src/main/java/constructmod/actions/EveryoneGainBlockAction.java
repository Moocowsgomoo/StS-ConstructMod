package constructmod.actions;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class EveryoneGainBlockAction extends AbstractGameAction
{
    public EveryoneGainBlockAction(final AbstractCreature source, final int numBlock) {
    	this.source = source;
    	this.amount = numBlock;
        this.actionType = ActionType.BLOCK;
        this.duration = 0.01f;
    }
    
    @Override
    public void update() {
    	AbstractDungeon.actionManager.addToTop(new GainBlockAction(this.source,AbstractDungeon.player,this.amount));
    	int temp = AbstractDungeon.getCurrRoom().monsters.monsters.size();
    	for (int i = 0; i < temp; i++) {
			AbstractMonster targetMonster = (AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i);
			if ((!targetMonster.isDying) && (targetMonster.currentHealth > 0) && (!targetMonster.isEscaping)) {
				targetMonster.addBlock(this.amount);
			}
		}
        this.isDone = true;
    }
}
