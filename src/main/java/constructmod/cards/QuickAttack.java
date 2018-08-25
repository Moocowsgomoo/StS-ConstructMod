package constructmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;

import constructmod.ConstructMod;
import constructmod.patches.AbstractCardEnum;

public class QuickAttack extends AbstractConstructCard {
	public static final String ID = ConstructMod.makeID("QuickAttack");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String M_UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	private static final int COST = 0;
	private static final int ATTACK_DMG = 3;
	private static final int DEX_GAIN = 2;
	private static final int UPGRADE_PLUS_ATTACK_DMG = 2;
	private static final int UPGRADE_PLUS_DEX_GAIN = 1;
	private static final int M_UPGRADE_PLUS_ATTACK_DMG = 3;
	private static final int M_UPGRADE_PLUS_DEX_GAIN = 2;
	private static final int M_UPGRADE_PERMANENT_DEX_GAIN = 2;
	private static final int POOL = 1;

	public QuickAttack() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, AbstractCard.CardType.ATTACK,
				AbstractCardEnum.CONSTRUCTMOD, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY, POOL);
		this.magicNumber = this.baseMagicNumber = DEX_GAIN;
		this.damage = this.baseDamage = ATTACK_DMG;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		// damage
		
		int dexDown = this.magicNumber;
		if (this.megaUpgraded) dexDown -= M_UPGRADE_PERMANENT_DEX_GAIN;
		
		AbstractDungeon.actionManager.addToBottom(new DamageAction(m,new DamageInfo(p, this.damage, this.damageTypeForTurn),AttackEffect.SLASH_DIAGONAL));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new DexterityPower(p, this.magicNumber),this.magicNumber));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new LoseDexterityPower(p, dexDown),dexDown));
	}

	@Override
	public AbstractCard makeCopy() {
		return new QuickAttack();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeDamage(UPGRADE_PLUS_ATTACK_DMG);
			this.upgradeMagicNumber(UPGRADE_PLUS_DEX_GAIN);
		} else if (this.canUpgrade()) {
			this.megaUpgradeName();
			this.upgradeDamage(M_UPGRADE_PLUS_ATTACK_DMG);
			this.upgradeMagicNumber(M_UPGRADE_PLUS_DEX_GAIN);
			this.rawDescription = M_UPGRADE_DESCRIPTION;
			this.initializeDescription();
		}
	}
}
