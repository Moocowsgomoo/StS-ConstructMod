package constructmod.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.QuantumEgg;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import constructmod.ConstructMod;
import constructmod.actions.QueueCardFrontAction;
import constructmod.actions.ShiftStatsAction;
import constructmod.actions.ShiftingStanceAction;

public class ShiftingStancePower extends AbstractPower {
	public static final String POWER_ID = ConstructMod.makeID("ShiftingStance");
	public static final String NAME = "Shifting Stance";
	public static final String[] DESCRIPTIONS = new String[] {
			"After you play #b",
			" more card, ",
			" more cards, ",
			"swap your #yStrength and #yDexterity."
	};
	public static int idOffset = 0;
	public int maxAmount;

	public ShiftingStancePower(AbstractCreature owner, int amount) {
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
			AbstractDungeon.actionManager.addToBottom(new ShiftingStanceAction(p,false));
		}
	}
}