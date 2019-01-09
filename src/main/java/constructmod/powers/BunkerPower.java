package constructmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import constructmod.ConstructMod;
import constructmod.actions.BunkerAction;

public class BunkerPower extends AbstractPower {
	public static final String POWER_ID = ConstructMod.makeID("Bunker");
	public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	
	public BunkerPower(AbstractCreature owner, int amount) {
		this.name = powerStrings.NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		updateDescription();
		this.type = AbstractPower.PowerType.BUFF;
		this.isTurnBased = false;
		ConstructMod.setPowerImages(this);
	}
	
	@Override
	public void updateDescription() {
		this.description = String.format(DESCRIPTIONS[0], this.amount);
	}
	
	@Override
	public void atEndOfTurn(final boolean isPlayer) {
		if (!isPlayer) return;
		AbstractDungeon.actionManager.addToBottom(new BunkerAction(AbstractDungeon.player,this.amount));
	}
}