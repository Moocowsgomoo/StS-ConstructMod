package constructmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import constructmod.ConstructMod;
import constructmod.relics.ExtraLongRangeLanceRelic;

public class ExtraLongRangeLancePower extends AbstractPower {
	public static final String POWER_ID = ConstructMod.makeID("ExtraLongRangeLance");
	public static final String NAME = "EXTRA-Long-Range Lance";
	public static final String[] DESCRIPTIONS = new String[] {
			"At the start of the combat after the next one, deal #b",
			" damage to a random enemy."
	};

	public ExtraLongRangeLancePower(AbstractCreature owner, int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		updateDescription();
		this.type = PowerType.BUFF;
		this.isTurnBased = false;
		ConstructMod.setPowerImages(this,LongRangeLancePower.POWER_ID);
	}
	
	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
	}

	@Override
	public void onVictory() {
		ExtraLongRangeLanceRelic relic = new ExtraLongRangeLanceRelic();
		AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, relic);
		relic.setCounter(this.amount);
		relic.updateDescription(AbstractDungeon.player.chosenClass);
	}


}