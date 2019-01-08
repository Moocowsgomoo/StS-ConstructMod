package constructmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import constructmod.ConstructMod;
import constructmod.actions.GatlingGunAction;
import constructmod.patches.AbstractCardEnum;

public class GatlingGun extends AbstractConstructCard {
	public static final String ID = ConstructMod.makeID("GatlingGun");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
	private static final int COST = -1;
	private static final int ATTACK_DMG = 3;
	private static final int UPGRADE_PLUS_ATTACK_DMG = 1;
	private static final int SHOTS_MULT = 2;
	private static final int M_UPGRADE_PLUS_ATTACK_DMG = -1;
	private static final int M_UPGRADE_PLUS_SHOTS_MULT = 1;
	private static final int POOL = 1;

	public GatlingGun() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, AbstractCard.CardType.ATTACK,
				AbstractCardEnum.CONSTRUCTMOD, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.ALL_ENEMY, POOL);
		this.damage = this.baseDamage = ATTACK_DMG;
		this.magicNumber = this.baseMagicNumber = SHOTS_MULT;
		this.isMultiDamage = true;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new GatlingGunAction(
				p, this.baseDamage, this.damageTypeForTurn , this.freeToPlayOnce, this.energyOnUse, this.magicNumber));
	}

	@Override
	public void applyPowers(){
		super.applyPowers();
		this.rawDescription = EXTENDED_DESCRIPTION[0] + DESCRIPTION;
		initializeDescription();
	}

	@Override
	public void onMoveToDiscard(){
		this.rawDescription = DESCRIPTION;
		initializeDescription();
	}

	@Override
	public AbstractCard makeCopy() {
		return new GatlingGun();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeDamage(UPGRADE_PLUS_ATTACK_DMG);
		} else if (this.canUpgrade()) {
			this.megaUpgradeName();
			this.upgradeDamage(M_UPGRADE_PLUS_ATTACK_DMG);
			this.upgradeMagicNumber(M_UPGRADE_PLUS_SHOTS_MULT);
		}
	}
}
