package constructmod.cards;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;


import constructmod.patches.AbstractCardEnum;

public class MemoryTap extends AbstractConstructCard {
	public static final String ID = "MemoryTap";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	private static final int COST = 3;
	private static final int UPGRADE_NEW_COST = 2;
	//private static final int M_UPGRADE_NEW_COST = 2;
	private static final int POOL = 1;

	public MemoryTap() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, AbstractCard.CardType.SKILL,
				AbstractCardEnum.CONSTRUCTMOD, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.NONE, POOL);
		this.exhaust = true;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		
		final AbstractCard c1 = CardLibrary.getColorSpecificCard(AbstractPlayer.PlayerClass.IRONCLAD, AbstractDungeon.cardRandomRng).makeCopy();
        c1.setCostForTurn(-99);
        if (this.megaUpgraded) c1.upgrade();
		AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c1));
		
		final AbstractCard c2 = CardLibrary.getColorSpecificCard(AbstractPlayer.PlayerClass.THE_SILENT, AbstractDungeon.cardRandomRng).makeCopy();
        c2.setCostForTurn(-99);
        if (this.megaUpgraded) c2.upgrade();
		AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c2));
		
		final AbstractCard c3 = CardLibrary.getColorSpecificCard(AbstractPlayer.PlayerClass.DEFECT, AbstractDungeon.cardRandomRng).makeCopy();
        c3.setCostForTurn(-99);
        if (this.megaUpgraded) c3.upgrade();
		AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c3));
		
		// quietly gain an orb slot, since defect cards would kind of suck if you didn't.
		AbstractDungeon.actionManager.addToBottom(new IncreaseMaxOrbAction(1));
		
	}

	@Override
	public AbstractCard makeCopy() {
		return new MemoryTap();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeBaseCost(UPGRADE_NEW_COST);
		} else if (this.canUpgrade()) {
			this.megaUpgradeName();
			this.rawDescription = UPGRADE_DESCRIPTION;
			this.initializeDescription();
		}
	}
}
