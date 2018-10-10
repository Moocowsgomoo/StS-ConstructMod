package constructmod.cards;

import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;
import constructmod.ConstructMod;
import constructmod.patches.AbstractCardEnum;

public class Missile extends AbstractCycleCard {
	public static final String ID = ConstructMod.makeID("Missile");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 2;
	private static final int ATTACK_DMG = 15;
	private static final int UPGRADE_PLUS_ATTACK_DMG = 4;
	public static final int DEBUFF_AMT = 2;
	private static final int M_UPGRADE_NEW_COST = 1;
	private static final int POOL = 1;

	public boolean hasBeenPlayed = false;

	public Missile() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, CardType.ATTACK,
				AbstractCardEnum.CONSTRUCTMOD, CardRarity.COMMON, CardTarget.ENEMY, POOL);
		this.damage = this.baseDamage = ATTACK_DMG;
		this.magicNumber = this.baseMagicNumber = DEBUFF_AMT;
	}

	@Override
	public boolean canCycle() {
		return super.canCycle() && hasBeenPlayed;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		hasBeenPlayed = true;
		AbstractDungeon.actionManager.addToBottom(new VFXAction(new WeightyImpactEffect(m.hb.cX,m.hb.cY)));
		// Each action waits AT MOST 0.1f if Fast Mode is enabled; slow it down a bit even in Fast Mode.
		AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4f));
		AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1f));
		AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1f));
		AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1f));
		AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1f));
		AbstractDungeon.actionManager.addToBottom(new VFXAction(new ExplosionSmallEffect(m.hb.cX, m.hb.cY), 0.03f));
		AbstractDungeon.actionManager.addToBottom(new DamageAction(m,
					new DamageInfo(p, this.damage, this.damageTypeForTurn),
					AbstractGameAction.AttackEffect.NONE));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m,p,new WeakPower(m,this.magicNumber,false),this.magicNumber));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m,p,new VulnerablePower(m,this.magicNumber,false),this.magicNumber));
	}

	@Override
	public AbstractCard makeCopy() {
		return new Missile();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeDamage(UPGRADE_PLUS_ATTACK_DMG);
		} else if (canUpgrade()) {
			this.megaUpgradeName();
			this.upgradeBaseCost(M_UPGRADE_NEW_COST);
		}
	}
}
