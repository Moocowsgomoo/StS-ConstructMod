package constructmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.QuantumEgg;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import constructmod.ConstructMod;
import constructmod.actions.QueueCardFrontAction;

public class MultistagePower extends AbstractPower {
	public static final String POWER_ID = ConstructMod.makeID("Multistage");
	public static final String NAME = "Multistage";
	public static final String[] DESCRIPTIONS = new String[] {
			"At the start of your next #b",
			" turn(s), play a copy of ",
			"."
	};
	public static int idOffset = 0;

	public AbstractCard heldCard;

	public MultistagePower(AbstractCreature owner, int amount, AbstractCard heldCard) {
		this.name = NAME;
		this.ID = POWER_ID + idOffset; // prevents power stacking
		idOffset++;
		this.owner = owner;
		this.amount = amount;
		this.type = PowerType.BUFF;
		this.isTurnBased = true;
		ConstructMod.setPowerImages(this);
		this.heldCard = heldCard.makeStatEquivalentCopy();
		updateDescription();
	}
	
	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.heldCard.name + DESCRIPTIONS[2];
	}

	@Override
	public void atStartOfTurn() {
		this.flash();
		AbstractCard card = this.heldCard.makeStatEquivalentCopy();
		card.purgeOnUse = true;
		card.freeToPlayOnce = true;
		if (AbstractDungeon.player.hasRelic(QuantumEgg.ID)) card.upgrade();
		AbstractDungeon.actionManager.addToBottom(new QueueCardFrontAction(card,AbstractDungeon.getRandomMonster(), EnergyPanel.getCurrentEnergy()));

		if (this.amount == 0) {
			AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
		}
		else {
			AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
		}
	}
}