package constructmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import constructmod.ConstructMod;
import constructmod.patches.AbstractCardEnum;
import constructmod.powers.ExtraLongRangeLancePower;
import constructmod.powers.LongRangeLancePower;
import constructmod.powers.SpinDrivePower;

public class LongRangeLance extends AbstractConstructCard {
	public static final String ID = ConstructMod.makeID("LongRangeLance");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String M_UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	private static final int COST = 1;
	public static final int DAMAGE = 12;
	private static final int UPGRADE_PLUS_DAMAGE = 4;
	private static final int M_UPGRADE_PLUS_DAMAGE = -4;
	private static final int POOL = 1;

	public LongRangeLance() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, CardType.POWER,
				AbstractCardEnum.CONSTRUCTMOD, CardRarity.RARE, CardTarget.SELF, POOL);
		this.baseMagicNumber = this.magicNumber = DAMAGE;
		this.tags.add(CardTags.HEALING); // since it's an out-of-battle effect, don't want the player stacking it infinitely
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new LongRangeLancePower(p,this.magicNumber),this.magicNumber));
		if (megaUpgraded) AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new ExtraLongRangeLancePower(p,this.magicNumber),this.magicNumber));
	}

	@Override
	public AbstractCard makeCopy() {
		return new LongRangeLance();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPGRADE_PLUS_DAMAGE);
		} else if (this.canUpgrade()) {
			this.megaUpgradeName();
			this.upgradeMagicNumber(M_UPGRADE_PLUS_DAMAGE);
			this.rawDescription = M_UPGRADE_DESCRIPTION;
			this.initializeDescription();
		}
	}
}
