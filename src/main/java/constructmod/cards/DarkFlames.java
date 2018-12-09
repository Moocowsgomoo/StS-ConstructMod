package constructmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import constructmod.ConstructMod;
import constructmod.patches.AbstractCardEnum;
import constructmod.powers.FlashFreezePower;

public class DarkFlames extends AbstractConstructCard {
	public static final String ID = ConstructMod.makeID("DarkFlames");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	public static final String M_UPGRADE_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION[0];
	public static final int COST = -1;
	private static final int POOL = 1;

	public DarkFlames() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, UPGRADE_DESCRIPTION, CardType.POWER,
				AbstractCardEnum.CONSTRUCTMOD, CardRarity.UNCOMMON, CardTarget.SELF, POOL);
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new FlashFreezePower(p,this.magicNumber),this.magicNumber));
	}

	@Override
	public void applyPowers(){
		super.applyPowers();
		if (megaUpgraded) this.retain = true;
	}

	@Override
	public AbstractCard makeCopy() {
		return new DarkFlames();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			//this.rawDescription = this.UPGRADE_DESCRIPTION;
			//this.upgradeMagicNumber(UPGRADE_PLUS_FREEZE_TURNS);
			//this.initializeDescription();
			this.isInnate = true;
		} else if (this.canUpgrade()) {
			this.megaUpgradeName();
			this.rawDescription = this.M_UPGRADE_DESCRIPTION;
			this.initializeDescription();
			//this.upgradeMagicNumber(M_UPGRADE_PLUS_FREEZE_TURNS);
			this.retain = true;
		}
	}
}
