package constructmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import constructmod.ConstructMod;
import constructmod.relics.ExtraLongRangeLanceRelic;

public class ExtraLongRangeLancePower extends AbstractPower {
	public static final String POWER_ID = ConstructMod.makeID("ExtraLongRangeLance");
	public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

	public ExtraLongRangeLancePower(AbstractCreature owner, int amount) {
		this.name = powerStrings.NAME;
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
		this.description = String.format(DESCRIPTIONS[0],this.amount);
	}

	@Override
	public void onVictory() {
		ExtraLongRangeLanceRelic relic = new ExtraLongRangeLanceRelic();
		AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, relic);
		relic.setCounter(this.amount);
		relic.updateDescription(AbstractDungeon.player.chosenClass);
	}


}