package constructmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;


import constructmod.ConstructMod;
import constructmod.patches.AbstractCardEnum;

public class ChargeShot extends AbstractConstructCard {
	public static final String ID = ConstructMod.makeID("ChargeShot");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	private static final int COST = 1;
	private static final int ATTACK_DMG = 5;
	private static final int UPGRADE_PLUS_ATTACK_DMG = 2;
	private static final int M_UPGRADE_PLUS_ATTACK_DMG = 3;
	private static final int CHARGE_DMG = 5;
	private static final int UPGRADE_PLUS_CHARGE_DMG = 2;
	private static final int M_UPGRADE_PLUS_CHARGE_DMG = 3;
	private static final int POOL = 1;

	public ChargeShot() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, AbstractCard.CardType.ATTACK,
				AbstractCardEnum.CONSTRUCTMOD, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY, POOL);
		this.damage = this.baseDamage = ATTACK_DMG;
		this.magicNumber = this.baseMagicNumber = CHARGE_DMG;
		this.retain = true;
	}

	public int getTrueBaseDamage(){
		int dmg = ATTACK_DMG;
		if (this.upgraded) dmg += UPGRADE_PLUS_ATTACK_DMG;
		if (this.megaUpgraded) dmg += M_UPGRADE_PLUS_ATTACK_DMG;
		return dmg;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		//AbstractDungeon.actionManager.addToBottom(new SFXAction("THUNDERCLAP", 0.05f));
		AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.DamageAction(m,
					new DamageInfo(p, this.damage, this.damageTypeForTurn),
					AbstractGameAction.AttackEffect.SMASH));
		this.baseDamage = getTrueBaseDamage();
	
		//this.rawDescription = DESCRIPTION;
		//initializeDescription();
	}
	
	@Override
	public void applyPowers(){
		
		super.applyPowers();
		
		this.retain = true;
		
		//this.rawDescription = UPGRADE_DESCRIPTION + DESCRIPTION;
		//initializeDescription();
	}
	
	/*@Override
	public void onMoveToDiscard(){
		this.rawDescription = DESCRIPTION;
		initializeDescription();
	}*/
	
	@Override
	public void triggerOnEndOfTurnForPlayingCard()
	{
		this.baseDamage += this.magicNumber;
		applyPowers();
		this.flash();
	}

	@Override
	public AbstractCard makeCopy() {
		return new ChargeShot();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPGRADE_PLUS_CHARGE_DMG);
			this.upgradeDamage(UPGRADE_PLUS_ATTACK_DMG);
		} else if (this.canUpgrade()) {
			this.megaUpgradeName();
			this.upgradeDamage(M_UPGRADE_PLUS_ATTACK_DMG);
			this.upgradeMagicNumber(M_UPGRADE_PLUS_CHARGE_DMG);
		}
	}
}
