package constructmod.cards;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;


import basemod.abstracts.CustomCard;
import constructmod.ConstructMod;
import constructmod.actions.GainMaxHPAction;
import constructmod.patches.AbstractCardEnum;

public class HastyRepair extends AbstractConstructCard {
	public static final String ID = ConstructMod.makeID("HastyRepair");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String M_UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	private static final int COST = 1;
	private static final int HEAL_AMT = 12;
	private static final int MAX_HP_GAIN_AMT = -2;
	//private static final int M_UPGRADE_PLUS_MAX_HP_GAIN_AMT = 1;
	private static final int UPGRADE_HEAL_AMT = 4;
	private static final int POOL = 1;
	
	private int maxHpGain;

	public HastyRepair() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, AbstractCard.CardType.SKILL,
				AbstractCardEnum.CONSTRUCTMOD, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.SELF, POOL);
		this.magicNumber = this.baseMagicNumber = HEAL_AMT;
		this.maxHpGain = MAX_HP_GAIN_AMT;
		this.exhaust = true;
		this.tags.add(CardTags.HEALING);
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new HealAction(p,p,this.magicNumber));
		AbstractDungeon.actionManager.addToBottom(new GainMaxHPAction(p,p,maxHpGain));
		if (megaUpgraded) AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(this.makeCopy()));
	}

	@Override
	public AbstractCard makeCopy() {
		return new HastyRepair();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPGRADE_HEAL_AMT);
		} else if (this.canUpgrade()) {
			this.megaUpgradeName();
			this.rawDescription = M_UPGRADE_DESCRIPTION;
			this.initializeDescription();
		}
	}
}
