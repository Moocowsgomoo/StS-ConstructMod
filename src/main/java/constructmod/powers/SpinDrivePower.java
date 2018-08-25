package constructmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import basemod.BaseMod;
import basemod.interfaces.PostDrawSubscriber;
import constructmod.ConstructMod;

public class SpinDrivePower extends AbstractPower {
	public static final String POWER_ID = ConstructMod.makeID("SpinDrive");
	public static final String NAME = "Spin Drive";
	public static final String[] DESCRIPTIONS = new String[] {
			"After you play a card, draw a card."
	};
	
	private int amountPerTurn=0;
	
	public SpinDrivePower(AbstractCreature owner, int amountOfDraws) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = this.amountPerTurn = amountOfDraws;
		updateDescription();
		this.type = AbstractPower.PowerType.BUFF;
		this.isTurnBased = false;
		this.img = new Texture("img/constructPowers/spin_drive.png");
	}
	
	@Override
	public void stackPower(final int stackAmount) {
		super.stackPower(stackAmount);
		this.amountPerTurn += stackAmount;
    }
	
	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0];
	}
	
	@Override
	public void atStartOfTurn() {
		this.amount = this.amountPerTurn;
    }
	
	@Override
	public void onPlayCard(final AbstractCard card, final AbstractMonster m) {
		if (this.amount > 0) amount--;
		else return;
		this.flashWithoutSound();
		AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, 1));
	}
}