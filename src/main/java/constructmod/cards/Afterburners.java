package constructmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import constructmod.ConstructMod;
import constructmod.patches.AbstractCardEnum;
import constructmod.powers.AfterburnersPower;

public class Afterburners extends AbstractConstructCard {
	public static final String ID = ConstructMod.makeID("Afterburners");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	public static final String M_UPGRADE_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION[0];
	private static final int COST = 2;
	private static final int PLAY_TIMES = 3;
	//public static final int UPGRADE_PLUS_PLAY_TIMES = 1;
	public static final int M_UPGRADE_PLUS_PLAY_TIMES = 1;
	public static final int BURNS = 3;
	//public static final int UPGRADE_NEW_COST = 2;
	public static final int UPGRADED_BURNS = 2;
	private static final int POOL = 1;

	public Afterburners() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, CardType.SKILL,
				AbstractCardEnum.CONSTRUCTMOD, CardRarity.RARE, CardTarget.SELF, POOL);
		this.baseMagicNumber = this.magicNumber = PLAY_TIMES;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Burn(),(this.upgraded?UPGRADED_BURNS:BURNS),true,true));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new AfterburnersPower(p,this.magicNumber), this.magicNumber));
	}

	@Override
	public AbstractCard makeCopy() {
		return new Afterburners();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			//this.upgradeBaseCost(UPGRADE_NEW_COST);
			this.rawDescription = UPGRADE_DESCRIPTION;
			this.initializeDescription();
			//this.upgradeMagicNumber(UPGRADE_PLUS_PLAY_TIMES);
		} else if (this.canUpgrade()) {
			this.megaUpgradeName();
			//this.rawDescription = M_UPGRADE_DESCRIPTION;
			//this.initializeDescription();
			this.upgradeMagicNumber(M_UPGRADE_PLUS_PLAY_TIMES);
		}
	}
}
