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
import constructmod.powers.AgitationPower;
import constructmod.powers.BunkerPower;

public class Agitation extends AbstractConstructCard {
	public static final String ID = ConstructMod.makeID("Agitation");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	public static final String M_UPGRADE_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION[0];
	private static final int COST = 1;
	public static final int M_UPGRADE_NEW_COST = 0;
	private static final int BUFF_AMT = 1;
	private static final int POOL = 1;

	public Agitation() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, CardType.POWER,
				AbstractCardEnum.CONSTRUCTMOD, CardRarity.RARE, CardTarget.SELF, POOL);
		this.magicNumber = this.baseMagicNumber = BUFF_AMT;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new AgitationPower(p, this.magicNumber), this.magicNumber));
	}

	@Override
	public void startInPlayEffect(){
		AbstractPlayer p = AbstractDungeon.player;
		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new AgitationPower(p, this.magicNumber), this.magicNumber));
	}

	@Override
	public AbstractCard makeCopy() {
		return new Agitation();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.rawDescription = UPGRADE_DESCRIPTION;
			this.initializeDescription();
			this.isInnate = true;
		} else if (this.canUpgrade()) {
			this.megaUpgradeName();
			this.rawDescription = M_UPGRADE_DESCRIPTION;
			this.initializeDescription();
			this.upgradeBaseCost(M_UPGRADE_NEW_COST);
			this.startInPlay = true;
		}
	}
}
