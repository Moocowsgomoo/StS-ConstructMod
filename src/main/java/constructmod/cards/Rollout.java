package constructmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.IronWaveEffect;
import constructmod.ConstructMod;
import constructmod.patches.AbstractCardEnum;

public class Rollout extends AbstractConstructCard {
	public static final String ID = ConstructMod.makeID("Rollout");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
	public static final int COST = 1;
	public static final int CYCLE_DMG_MULT = 3;
	public static final int OVERHEAT = 10;
	public static final int UPGRADE_PLUS_OVERHEAT = 5;
	public static final int M_UPGRADE_PLUS_CYCLE_DMG_MULT = 1;
	public static final int M_UPGRADE_PLUS_OVERHEAT = 5;
	private static final int POOL = 1;

	public Rollout() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, CardType.ATTACK,
				AbstractCardEnum.CONSTRUCTMOD, CardRarity.COMMON, CardTarget.ENEMY, POOL);
		this.damage = this.baseDamage = 0;
		this.magicNumber = this.baseMagicNumber = CYCLE_DMG_MULT;
		this.overheat = OVERHEAT;
	}

	@Override
	public void applyPowers() {
		this.baseDamage = ConstructMod.cyclesThisTurn * this.magicNumber;
		super.applyPowers();
		this.rawDescription = EXTENDED_DESCRIPTION[0] + DESCRIPTION;
		this.initializeDescription();
	}

	@Override
	public void onMoveToDiscard() {
		this.rawDescription = DESCRIPTION;
		this.initializeDescription();
	}

	@Override
	public void calculateCardDamage(final AbstractMonster mo) {
		this.baseDamage = ConstructMod.cyclesThisTurn * this.magicNumber;
		super.calculateCardDamage(mo);
		this.rawDescription = EXTENDED_DESCRIPTION[0] + DESCRIPTION;
		this.initializeDescription();
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		if (p != null && m != null) {
			AbstractDungeon.actionManager.addToBottom(new VFXAction(new IronWaveEffect(p.hb.cX, p.hb.cY, m.hb.cX), 0.5f));
		}
		AbstractDungeon.actionManager.addToBottom(new DamageAction((AbstractCreature) m,
				new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));
	}

	@Override
	public AbstractCard makeCopy() {
		return new Rollout();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeOverheat(UPGRADE_PLUS_OVERHEAT);
		} else if (this.canUpgrade()) {
			this.megaUpgradeName();
			this.upgradeMagicNumber(M_UPGRADE_PLUS_CYCLE_DMG_MULT);
			this.upgradeOverheat(M_UPGRADE_PLUS_OVERHEAT);
		}
	}
}
