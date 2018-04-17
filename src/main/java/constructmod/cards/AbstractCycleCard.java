package constructmod.cards;

import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;


import basemod.abstracts.CustomCard;
import constructmod.powers.AbstractCyclePower;

public abstract class AbstractCycleCard extends CustomCard {
	
	protected boolean hasCycled = false;
	
	public AbstractCycleCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target, int cardPool) {
		super(id, name, img, cost, rawDescription, type, color, rarity, target, cardPool);
	}
	
	public void cycle() {
		if (hasCycled) return;
		hasCycled = true;
		
		AbstractDungeon.actionManager.addToBottom(new DiscardSpecificCardAction(this));
		AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, 1, false));
		
		for (AbstractPower pw : AbstractDungeon.player.powers) {
			if (pw instanceof AbstractCyclePower) {
				((AbstractCyclePower) pw).onCycleCard(this);
			}
		}
	}
}
