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
	private static final String M_UPGRADE_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION[0];
	private static final int COST = 2;
	private static final int M_UPGRADE_NEW_COST = 3;
	private static final int POOL = 1;

	public MemoryTap() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, AbstractCard.CardType.SKILL,
				AbstractCardEnum.CONSTRUCTMOD, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.NONE, POOL);
		this.exhaust = true;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		
		for (int i=0;i<(this.megaUpgraded?2:1);i++) {
			
			AbstractCard c1;
			do {
				c1 = CardLibrary.getColorSpecificCard(AbstractPlayer.PlayerClass.IRONCLAD, AbstractDungeon.cardRandomRng).makeCopy();
			} while (c1.rarity == CardRarity.BASIC);
	        c1.setCostForTurn(-99);
	        if (this.upgraded) c1.upgrade();
			AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c1));
			
			AbstractCard c2;
			do {
				c2 = CardLibrary.getColorSpecificCard(AbstractPlayer.PlayerClass.THE_SILENT, AbstractDungeon.cardRandomRng).makeCopy();
			} while (c2.rarity == CardRarity.BASIC);
	        c2.setCostForTurn(-99);
	        if (this.upgraded) c2.upgrade();
			AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c2));
			
			AbstractCard c3;
			do {
				c3 = CardLibrary.getColorSpecificCard(AbstractPlayer.PlayerClass.DEFECT, AbstractDungeon.cardRandomRng).makeCopy();
			} while (c3.rarity == CardRarity.BASIC);
	        c3.setCostForTurn(-99);
	        if (this.upgraded) c3.upgrade();
			AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c3));
			
		}
		
	}

	@Override
	public AbstractCard makeCopy() {
		return new MemoryTap();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.rawDescription = UPGRADE_DESCRIPTION;
			this.initializeDescription();
		} else if (this.canUpgrade()) {
			this.megaUpgradeName();
			this.rawDescription = M_UPGRADE_DESCRIPTION;
			this.initializeDescription();
			this.upgradeBaseCost(M_UPGRADE_NEW_COST);
		}
	}
}
