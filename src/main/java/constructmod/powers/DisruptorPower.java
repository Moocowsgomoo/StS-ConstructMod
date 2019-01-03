package constructmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import constructmod.ConstructMod;

public class DisruptorPower extends AbstractPower {
	public static final String POWER_ID = ConstructMod.makeID("Disruptor");
	public static final String NAME = "Disruptor";
	public static final String[] DESCRIPTIONS = new String[] {
			"After you lose HP, gain #b",
			" times that much #yBlock.",
	};
	
	public DisruptorPower(AbstractCreature owner, int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		updateDescription();
		this.type = AbstractPower.PowerType.BUFF;
		this.isTurnBased = false;
		this.img = new Texture("img/constructPowers/48/Disruptor.png");
	}
	
	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
	}
	
	@Override
	public int onAttacked(final DamageInfo info, final int damageAmount) {
		AbstractDungeon.actionManager.addToTop(new GainBlockAction(owner, owner, this.amount * (damageAmount)));
        return damageAmount;
    }
}