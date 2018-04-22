package constructmod.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MetallicizePower;


import constructmod.patches.AbstractCardEnum;

public class MetalShell extends AbstractConstructCard {
	public static final String ID = "MetalShell";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	private static final int BLOCK_AMT = 6;
	private static final int METALLICIZE_AMT = 1;
	private static final int UPGRADE_PLUS_BLOCK_AMT = 2;
	private static final int UPGRADE_PLUS_METALLICIZE_AMT = 1;
	private static final int M_UPGRADE_PLUS_BLOCK_AMT = 1;
	private static final int M_UPGRADE_PLUS_METALLICIZE_AMT = 1;
	private static final int POOL = 1;

	public MetalShell() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, AbstractCard.CardType.SKILL,
				AbstractCardEnum.CONSTRUCTMOD, AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.SELF, POOL);
		this.baseBlock = BLOCK_AMT;
		this.magicNumber = this.baseMagicNumber = METALLICIZE_AMT;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new MetallicizePower(p, this.magicNumber), this.magicNumber));
		AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
	}

	@Override
	public AbstractCard makeCopy() {
		return new MetalShell();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeBlock(UPGRADE_PLUS_BLOCK_AMT);
			this.upgradeMagicNumber(UPGRADE_PLUS_METALLICIZE_AMT);
		} else if (this.canUpgrade()) {
			this.megaUpgradeName();
			this.upgradeBlock(M_UPGRADE_PLUS_BLOCK_AMT);
			this.upgradeMagicNumber(M_UPGRADE_PLUS_METALLICIZE_AMT);
		}
	}
}
