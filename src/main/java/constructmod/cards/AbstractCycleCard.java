package constructmod.cards;

import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;


import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import constructmod.ConstructMod;
import constructmod.actions.CycleCardAction;
import constructmod.powers.IModifyMaxCyclesPower;
import constructmod.powers.NoCyclePower;
import constructmod.relics.IModifyMaxCyclesRelic;

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
		if (!canCycle()) return;
		//if (timesCycled >= getMaxCycles(timesCycled)) return;

		timesCycled++;
		cycle(this);
	}
	
	// Individual cards override this method to add their own cycle conditions. They always check this parent method as well.
	public boolean canCycle() {
		return timesCycled < getMaxCycles(timesCycled);
	}

	public int getMaxCycles(int currentTimesCycled){
		int maxCycles = 1;
		if (AbstractDungeon.player == null) return 1;
		for (AbstractRelic r:AbstractDungeon.player.relics){
			if (r instanceof IModifyMaxCyclesRelic){
				maxCycles = ((IModifyMaxCyclesRelic) r).modifyMaxCycles(maxCycles,currentTimesCycled);
			}
		}
		for (AbstractPower p:AbstractDungeon.player.powers){
			if (p instanceof IModifyMaxCyclesPower){
				maxCycles = ((IModifyMaxCyclesPower) p).modifyMaxCycles(maxCycles,currentTimesCycled);
			}
		}
		return maxCycles;
	}
	
	public static void cycle(AbstractCard card) {
		AbstractDungeon.actionManager.addToTop(new CycleCardAction(card, AbstractDungeon.player.hasPower(PanicFire.ID) && !card.upgraded));
		if (!ConstructMod.areCyclesFast()) AbstractDungeon.actionManager.addToTop(new WaitAction(0.2f));
	}
}
