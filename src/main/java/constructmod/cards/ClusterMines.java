package constructmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import constructmod.ConstructMod;
import constructmod.actions.DealMultiRandomDamageAction;
import constructmod.patches.AbstractCardEnum;

public class ClusterMines extends AbstractConstructCard {
	public static final String ID = ConstructMod.makeID("ClusterMines");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String M_UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	private static final int COST = 2;
	private static final int ATTACK_DMG = 4;
	private static final int TIMES = 3;
	private static final int UPGRADE_PLUS_DAMAGE = 1;
	private static final int POOL = 1;

	public ClusterMines() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, CardType.ATTACK,
				AbstractCardEnum.CONSTRUCTMOD, CardRarity.COMMON, CardTarget.ALL_ENEMY, POOL);
		this.damage = this.baseDamage = ATTACK_DMG;
		this.magicNumber = this.baseMagicNumber = TIMES;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new DealMultiRandomDamageAction(
				new DamageInfo(p, this.baseDamage, this.damageTypeForTurn), this.magicNumber, AbstractGameAction.AttackEffect.FIRE));
	}

	@Override
	public void onBlockBroken(){
		super.onBlockBroken();
		if (megaUpgraded) this.modifyCostForCombat(-9);
		else this.freeToPlayOnce = true;
	}

	@Override
	public AbstractCard makeCopy() {
		return new ClusterMines();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeDamage(UPGRADE_PLUS_DAMAGE);
		} else if (this.canUpgrade()) {
			this.megaUpgradeName();
			this.rawDescription = M_UPGRADE_DESCRIPTION;
			this.initializeDescription();
		}
	}
}
