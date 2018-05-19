package constructmod.cards;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import basemod.abstracts.CustomCard;
import constructmod.patches.AbstractCardEnum;

public class OrbGenesis extends CustomCard {
	public static final String ID = "OrbGenesis";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	private static final int COST = 1;
	private static final int ADD_ORBS = 2;
	private static final int UPGRADE_ADD_ORBS = 1;
	private static final int POOL = 1;
	
	private final ArrayList<AbstractCard> list = new ArrayList<AbstractCard>();

	public OrbGenesis() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, AbstractCard.CardType.SKILL,
				AbstractCardEnum.CONSTRUCTMOD, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.SELF, POOL);
		this.magicNumber = this.baseMagicNumber = ADD_ORBS;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		
		list.clear();
		list.add(AbstractDungeon.commonCardPool.findCardByName("FlameCore").makeCopy());
	    list.add(AbstractDungeon.commonCardPool.findCardByName("LaserCore").makeCopy());
	    list.add(AbstractDungeon.commonCardPool.findCardByName("GuardCore").makeCopy());
	    list.add(AbstractDungeon.uncommonCardPool.findCardByName("ScopeCore").makeCopy());
	    list.add(AbstractDungeon.uncommonCardPool.findCardByName("BatteryCore").makeCopy());
	    /*if (upgraded) {
	    	for (int i=0;i<list.size();i++) {
				list.get(i).upgrade();
			}
	    }*/
		
	    for (int i=0;i<this.magicNumber;i++) {
	    	AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(
					p,p,list.get(AbstractDungeon.cardRandomRng.random(list.size() - 1)),1,true,true));
	    }
	}

	@Override
	public AbstractCard makeCopy() {
		return new OrbGenesis();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPGRADE_ADD_ORBS);
			//this.rawDescription = UPGRADE_DESCRIPTION;
			//this.initializeDescription();
		}
	}
}
