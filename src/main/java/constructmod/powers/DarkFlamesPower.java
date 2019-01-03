package constructmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import constructmod.ConstructMod;

public class DarkFlamesPower extends AbstractPower {
	public static final String POWER_ID = ConstructMod.makeID("DarkFlames");
	public static final String NAME = "Dark Flames";
	public static final String[] DESCRIPTIONS = new String[] {
			"At the start of your turn, deal damage to ALL enemies equal to the number of #yBurns in your #yexhaust pile."
	};

	public DarkFlamesPower(AbstractCreature owner) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = -1;
		this.type = PowerType.BUFF;
		this.isTurnBased = false;
		this.img = new Texture("img/constructPowers/48/Meltdown.png");
		updateDescription();
	}
	
	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0];
	}
	
	@Override
	public void atStartOfTurn() {
		this.flash();
		int count = 0;
		for (AbstractCard c : AbstractDungeon.player.exhaustPile.group){
			if (c instanceof Burn) count++;
		}
		AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(this.owner, DamageInfo.createDamageMatrix(count, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
	}
}