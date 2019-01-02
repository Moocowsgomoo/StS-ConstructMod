package constructmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import constructmod.ConstructMod;
import constructmod.actions.RetainRandomCardAction;

public class RetainRandomPower extends AbstractPower {
	public static final String POWER_ID = ConstructMod.makeID("RetainRandom");
	public static final String NAME = "Retain Random";
	public static final String[] DESCRIPTIONS = new String[] {
			"At the end of your turn, #yRetain #b",
			" random card.",
			" random cards."
	};

	public RetainRandomPower(AbstractCreature owner, int amount) {
		this.name = NAME;
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
		this.description = DESCRIPTIONS[0] + this.amount + (this.amount == 1?DESCRIPTIONS[1]:DESCRIPTIONS[2]);
	}
	
	@Override
	public void atEndOfTurn(final boolean isPlayer) {
		if (!isPlayer) return;
		AbstractDungeon.actionManager.addToBottom(new RetainRandomCardAction(this.amount));
	}
}