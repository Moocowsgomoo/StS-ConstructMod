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
import constructmod.powers.BunkerPower;
import constructmod.powers.RetainRandomPower;

public class Bunker extends AbstractConstructCard {
	public static final String ID = ConstructMod.makeID("Bunker");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String M_UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	private static final int COST = 1;
	private static final int POWER_BLOCK = 2;
	private static final int UPGRADE_POWER_BLOCK = 1;
	//private static final int M_UPGRADE_POWER_BLOCK = 1;
	private static final int POOL = 1;

	public Bunker() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, AbstractCard.CardType.POWER,
				AbstractCardEnum.CONSTRUCTMOD, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.SELF, POOL);
		this.magicNumber = this.baseMagicNumber = POWER_BLOCK;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new BunkerPower(p, this.magicNumber), this.magicNumber));
		if (this.megaUpgraded) {
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new RetainRandomPower(p,1), 1));
		}
	}

	@Override
	public AbstractCard makeCopy() {
		return new Bunker();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPGRADE_POWER_BLOCK);
		} else if (this.canUpgrade()) {
			this.megaUpgradeName();
			this.rawDescription = M_UPGRADE_DESCRIPTION;
			this.initializeDescription();
		}
	}
}
