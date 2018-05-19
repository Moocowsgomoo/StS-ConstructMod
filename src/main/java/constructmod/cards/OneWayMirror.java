package constructmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import constructmod.patches.AbstractCardEnum;

public class OneWayMirror extends AbstractConstructCard {
	public static final String ID = "OneWayMirror";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 2;
	private static final int BLOCK_AMT = 12;
	private static final int VULN_AMT = 2;
	private static final int UPGRADE_BLOCK_AMT = 2;
	private static final int UPGRADE_VULN_AMT = 1;
	private static final int M_UPGRADE_BLOCK_AMT = 3;
	private static final int M_UPGRADE_VULN_AMT = 5;
	private static final int POOL = 1;

	public OneWayMirror() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, AbstractCard.CardType.SKILL,
				AbstractCardEnum.CONSTRUCTMOD, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ALL, POOL);
		this.baseBlock = this.block = BLOCK_AMT;
		this.baseMagicNumber = this.magicNumber = VULN_AMT;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		
		AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
		
		for (final AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p, new VulnerablePower(mo, this.magicNumber, false), this.magicNumber));
        }
	}

	@Override
	public AbstractCard makeCopy() {
		return new OneWayMirror();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeBlock(UPGRADE_BLOCK_AMT);
			this.upgradeMagicNumber(UPGRADE_VULN_AMT);
		} else if (this.canUpgrade()) {
			this.megaUpgradeName();
			this.upgradeBlock(M_UPGRADE_BLOCK_AMT);
			this.upgradeMagicNumber(M_UPGRADE_VULN_AMT);
		}
	}
}
