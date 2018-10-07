package constructmod.cards;

import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;


import constructmod.actions.CycleCardAction;
import constructmod.powers.AbstractCyclePower;

public abstract class AbstractCycleCard extends AbstractConstructCard {
	
	public int timesCycled = 0;
	
	public AbstractCycleCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target, int cardPool) {
		super(id, name, img, cost, rawDescription, type, color, rarity, target, cardPool);
	}
	
	@Override
	public void atTurnStart(){
		timesCycled = 0;
	}
	
	@Override
	public void triggerWhenDrawn(){
		cycle();
	}
	
	// Individual cards override this method to add their own cycle conditions. They always check this parent method as well.
	public boolean canCycle() {
		return timesCycled < 1;
	}
	
	public void cycle() {
		if (!canCycle()) return;
		timesCycled++;
		
		AbstractDungeon.actionManager.addToTop(new CycleCardAction(this, AbstractDungeon.player.hasPower(PanicFire.ID) && !this.upgraded));
		
		for (AbstractPower pw : AbstractDungeon.player.powers) {
			if (pw instanceof AbstractCyclePower) {
				((AbstractCyclePower) pw).onCycleCard(this);
			}
		}

		for (AbstractCard c:AbstractDungeon.player.drawPile.group){
			if (c instanceof AbstractConstructCard){
				((AbstractConstructCard) c).reduceOverheat();
			}
		}
		for (AbstractCard c:AbstractDungeon.player.hand.group){
			if (c instanceof AbstractConstructCard){
				((AbstractConstructCard) c).reduceOverheat();
			}
		}
		for (AbstractCard c:AbstractDungeon.player.discardPile.group){
			if (c instanceof AbstractConstructCard){
				((AbstractConstructCard) c).reduceOverheat();
			}
		}
		
		AbstractDungeon.actionManager.addToTop(new WaitAction(0.2f));
	}
}
