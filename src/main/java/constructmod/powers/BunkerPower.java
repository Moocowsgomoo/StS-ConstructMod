package constructmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import basemod.BaseMod;
import basemod.interfaces.PostDrawSubscriber;
import constructmod.ConstructMod;

public class BunkerPower extends AbstractPower {
	public static final String POWER_ID = ConstructMod.makeID("Bunker");
	public static final String NAME = "Bunker";
	public static final String[] DESCRIPTIONS = new String[] {
			"When a card is #yRetained, gain #b",
			" #yBlock."
	};
	
	public BunkerPower(AbstractCreature owner, int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		updateDescription();
		this.type = AbstractPower.PowerType.BUFF;
		this.isTurnBased = false;
		this.img = new Texture("img/constructPowers/bunker.png");
	}
	
	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
	}
	
	@Override
	public void atEndOfTurn(final boolean isPlayer) {
		if (!isPlayer) return;
		for (final AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.retain) {
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(owner, owner, amount));
            }
        }
	}
}