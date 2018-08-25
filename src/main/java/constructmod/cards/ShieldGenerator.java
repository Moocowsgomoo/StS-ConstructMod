package constructmod.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BlurPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;


import basemod.abstracts.CustomCard;
import constructmod.ConstructMod;
import constructmod.patches.AbstractCardEnum;

public class ShieldGenerator extends AbstractConstructCard {
	public static final String ID = ConstructMod.makeID("ShieldGenerator");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	public static final String M_UPGRADE_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION[0];
	private static final int COST = 1;
	private static final int BLUR_AMT = 4;
	private static final int VULN_AMT = 2;
	private static final int UPGRADE_PLUS_BLUR_AMT =  1;
	private static final int UPGRADED_VULN_AMT = 1;
	private static final int M_UPGRADE_BLOCK_AMT = 5;
	private static final int M_UPGRADE_PLUS_BLUR_AMT = 4;
	private static final int POOL = 1;

	public ShieldGenerator() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, AbstractCard.CardType.POWER,
				AbstractCardEnum.CONSTRUCTMOD, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF, POOL);
		this.baseBlock = this.block = 0;
		this.magicNumber = this.baseMagicNumber = BLUR_AMT;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		int vuln = this.upgraded?UPGRADED_VULN_AMT:VULN_AMT;
		AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.ApplyPowerAction(p, p, new BlurPower(p, this.magicNumber), this.magicNumber));
		AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.ApplyPowerAction(p, p, new VulnerablePower(p, vuln, false), vuln));
		if (this.megaUpgraded) AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
	}

	@Override
	public AbstractCard makeCopy() {
		return new ShieldGenerator();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPGRADE_PLUS_BLUR_AMT);
			this.rawDescription = UPGRADE_DESCRIPTION;
			this.initializeDescription();
		} else if (this.canUpgrade()) {
			this.megaUpgradeName();
			this.upgradeBlock(M_UPGRADE_BLOCK_AMT);
			this.upgradeMagicNumber(M_UPGRADE_PLUS_BLUR_AMT);
			this.rawDescription = UPGRADE_DESCRIPTION + M_UPGRADE_DESCRIPTION;
			this.initializeDescription();
		}
	}
}
