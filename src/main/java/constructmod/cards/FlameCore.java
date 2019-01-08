package constructmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;


import constructmod.ConstructMod;
import constructmod.actions.InstantDamageRandomEnemyAction;
import constructmod.patches.AbstractCardEnum;

public class FlameCore extends AbstractCycleCard {
	public static final String ID = ConstructMod.makeID("FlameCore");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	public static final String M_UPGRADE_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION[0];
	private static final int COST = 0;
	private static final int HP_DMG = 3;
	//private static final int UPGRADE_PLUS_HP_DMG = 2;
	private static final int POOL = 1;

	public FlameCore() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, AbstractCard.CardType.SKILL,
				AbstractCardEnum.CONSTRUCTMOD, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF, POOL);
		this.baseMagicNumber = this.magicNumber = HP_DMG;
	}
	
	@Override
	public void triggerWhenDrawn(){
		if (!this.canCycle()) return; // have to check this before super call, otherwise our test for canCycle is false since it JUST cycled.
		super.triggerWhenDrawn();
		
		flash();
		if (ConstructMod.areCyclesFast()) {
			AbstractDungeon.actionManager.addToTop(new InstantDamageRandomEnemyAction(
					new DamageInfo(null, this.magicNumber, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE, true));
		}
		else{
			AbstractDungeon.actionManager.addToTop(new DamageRandomEnemyAction(
					new DamageInfo(null, this.magicNumber, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
		}
		CloneCore();
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToTop(new DamageRandomEnemyAction(
				new DamageInfo(null, this.magicNumber, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
		CloneCore();
	}

	@Override
	public AbstractCard makeCopy() {
		return new FlameCore();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.rawDescription = DESCRIPTION + UPGRADE_DESCRIPTION;
			this.initializeDescription();
		} else if (this.canUpgrade()) {
			this.megaUpgradeName();
			this.rawDescription = DESCRIPTION + M_UPGRADE_DESCRIPTION;
			this.initializeDescription();
		}
	}
}
