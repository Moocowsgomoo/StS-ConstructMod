package constructmod.powers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import constructmod.ConstructMod;
import constructmod.actions.ShiftingStanceAction;

public class ShiftingStanceMegaPower extends AbstractPower {
	public static final String POWER_ID = ConstructMod.makeID("ShiftingStanceMega");
	public static final String NAME = "Shifting Stance (Mega)";
	public static final String[] DESCRIPTIONS = new String[] {
			"After you play #b",
			" more card, ",
			" more cards, ",
			"swap your #yStrength and #yDexterity, then increase the highest and decrease the lowest by #b1."
	};
	public static int idOffset = 0;
	public int maxAmount;

	public ShiftingStanceMegaPower(AbstractCreature owner, int amount) {
		this.name = NAME;
		this.ID = POWER_ID + idOffset; // prevents power stacking
		idOffset++;
		this.owner = owner;
		this.maxAmount = this.amount = amount;
		this.type = PowerType.BUFF;
		this.isTurnBased = false;
		ConstructMod.setPowerImages(this,"ShiftingStance");
		updateDescription();
	}
	
	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0] + this.amount + (this.amount==1?DESCRIPTIONS[1]:DESCRIPTIONS[2])  + DESCRIPTIONS[3];
	}

	@Override
	public void onPlayCard(final AbstractCard card, final AbstractMonster m) {
		if (this.amount > 0) amount--;

		if (this.amount <= 0){
			// do the thing and reset
			this.flash();
			this.amount = this.maxAmount;
			AbstractPlayer p = AbstractDungeon.player;
			AbstractDungeon.actionManager.addToBottom(new ShiftingStanceAction(p,true));
		}
	}
}