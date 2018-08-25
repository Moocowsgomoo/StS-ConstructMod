package constructmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;

import constructmod.ConstructMod;
import constructmod.patches.AbstractCardEnum;

public class Impenetrable extends AbstractConstructCard {
	public static final String ID = ConstructMod.makeID("Impenetrable");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	public static final String M_UPGRADE_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION[0];
	private static final int COST = 3;
	private static final int BLOCK_AMT = 25;
	private static final int ENERGIZE_AMT = 1;
	private static final int UPGRADE_PLUS_ENERGIZE_AMT = 1;
	private static final int POOL = 1;

	public Impenetrable() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, AbstractCard.CardType.SKILL,
				AbstractCardEnum.CONSTRUCTMOD, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF, POOL);
		this.block = this.baseBlock = BLOCK_AMT;
		this.magicNumber = this.baseMagicNumber = ENERGIZE_AMT;
		this.exhaust = true;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p,p,this.block));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new EnergizedPower(p, this.magicNumber), this.magicNumber));
		if (this.megaUpgraded)
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new NextTurnBlockPower(p,this.block),this.block));
	}
	
	@Override
	public AbstractCard makeCopy() {
		return new Impenetrable();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.rawDescription = UPGRADE_DESCRIPTION;
			this.initializeDescription();
			this.upgradeMagicNumber(UPGRADE_PLUS_ENERGIZE_AMT);
		} else if (canUpgrade()) {
			this.megaUpgradeName();
			this.rawDescription = M_UPGRADE_DESCRIPTION;
			this.initializeDescription();
		}
	}
}
