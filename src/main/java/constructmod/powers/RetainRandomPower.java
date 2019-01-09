package constructmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import constructmod.ConstructMod;
import constructmod.actions.RetainRandomCardAction;

public class RetainRandomPower extends AbstractPower {
	public static final String POWER_ID = ConstructMod.makeID("RetainRandom");
	public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

	public RetainRandomPower(AbstractCreature owner, int amount) {
		this.name = powerStrings.NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		updateDescription();
		this.type = PowerType.BUFF;
		this.isTurnBased = false;
		this.priority = 4;
		ConstructMod.setPowerImages(this,BunkerPower.POWER_ID);
	}
	
	@Override
	public void updateDescription() {
		this.description = String.format(this.amount == 1?DESCRIPTIONS[0]:DESCRIPTIONS[1],this.amount);
	}
	
	@Override
	public void atEndOfTurn(final boolean isPlayer) {
		if (!isPlayer) return;
		AbstractDungeon.actionManager.addToBottom(new RetainRandomCardAction(this.amount));
	}
}