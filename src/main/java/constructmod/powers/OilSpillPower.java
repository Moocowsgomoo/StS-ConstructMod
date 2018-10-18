package constructmod.powers;

import basemod.BaseMod;
import basemod.interfaces.PostBattleSubscriber;
import basemod.interfaces.PostDrawSubscriber;
import basemod.interfaces.PostDungeonInitializeSubscriber;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.combat.BossCrystalImpactEffect;
import constructmod.ConstructMod;

public class OilSpillPower extends AbstractOnDrawPower{
	public static final String POWER_ID = ConstructMod.makeID("OilSpill");
	public static final String NAME = "Oil";
	public static final String[] DESCRIPTIONS = new String[] {
			"Whenever you draw a #yBurn card, deal #b",
			" damage to ",
			"."
	};

	public OilSpillPower(AbstractCreature owner, int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		updateDescription();
		this.type = PowerType.DEBUFF;
		this.isTurnBased = false;
		this.img = new Texture("img/constructPowers/oilspill.png");
	}
	
	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + FontHelper.colorString(this.owner.name, "y") + DESCRIPTIONS[2];
	}
	
	@Override
	public void onDrawCard (AbstractCard c) {
		if (c.cardID == Burn.ID) {
			this.flash();
			AbstractDungeon.actionManager.addToTop(new DamageAction(
					this.owner,new DamageInfo(this.owner,this.amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
		
		}
	}
}