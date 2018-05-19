package constructmod.cards;

import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;


import constructmod.actions.CycleCardAction;
import constructmod.powers.AbstractCyclePower;

public abstract class AbstractCycleCard extends AbstractConstructCard {
	
	public boolean hasCycled = false;
	
	public AbstractCycleCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target, int cardPool) {
		super(id, name, img, cost, rawDescription, type, color, rarity, target, cardPool);
	}
	
	public void cycle() {
		if (hasCycled) return;
		hasCycled = true;
		
		AbstractDungeon.actionManager.addToTop(new CycleCardAction(this, AbstractDungeon.player.hasPower("PanicFire") && !this.upgraded));
		
		for (AbstractPower pw : AbstractDungeon.player.powers) {
			if (pw instanceof AbstractCyclePower) {
				((AbstractCyclePower) pw).onCycleCard(this);
			}
		}
		
		AbstractDungeon.actionManager.addToTop(new WaitAction(0.2f));
	}
}
