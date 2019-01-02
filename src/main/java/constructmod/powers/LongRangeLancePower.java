package constructmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import constructmod.ConstructMod;
import constructmod.relics.LongRangeLanceRelic;

public class LongRangeLancePower extends AbstractPower {
	public static final String POWER_ID = ConstructMod.makeID("LongRangeLance");
	public static final String NAME = "Long-Range Lance";
	public static final String[] DESCRIPTIONS = new String[] {
			"At the start of the next combat, deal #b",
			" damage to a random enemy."
	};

	public LongRangeLancePower(AbstractCreature owner, int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		updateDescription();
		this.type = PowerType.BUFF;
		this.isTurnBased = false;
		ConstructMod.setPowerImages(this);
	}
	
	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
	}

	@Override
	public void onVictory() {
		LongRangeLanceRelic relic = new LongRangeLanceRelic();
		relic.setCounter(this.amount);
		relic.updateDescription(AbstractDungeon.player.chosenClass);
		AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, relic);
	}


}