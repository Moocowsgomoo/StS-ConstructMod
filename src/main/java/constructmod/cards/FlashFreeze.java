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
import constructmod.actions.CoolantAction;
import constructmod.patches.AbstractCardEnum;
import constructmod.powers.FlashFreezePower;

public class FlashFreeze extends AbstractConstructCard {
	public static final String ID = ConstructMod.makeID("FlashFreeze");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	public static final String M_UPGRADE_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION[0];
	public static final int COST = 1;
	public static final int BLOCK = 10;
	public static final int FREEZE_TURNS = 2;
	public static final int UPGRADE_PLUS_FREEZE_TURNS = 1;
	public static final int M_UPGRADE_PLUS_FREEZE_TURNS = 2;
	private static final int POOL = 1;

	public FlashFreeze() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, UPGRADE_DESCRIPTION, CardType.SKILL,
				AbstractCardEnum.CONSTRUCTMOD, CardRarity.UNCOMMON, CardTarget.SELF, POOL);
		this.block = this.baseBlock = BLOCK;
		this.magicNumber = this.baseMagicNumber = FREEZE_TURNS;
		this.exhaust = true;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new FlashFreezePower(p,this.magicNumber),this.magicNumber));
	}

	@Override
	public void applyPowers(){
		if (megaUpgraded) this.retain = true;
	}

	@Override
	public AbstractCard makeCopy() {
		return new FlashFreeze();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			//this.rawDescription = this.UPGRADE_DESCRIPTION;
			this.upgradeMagicNumber(UPGRADE_PLUS_FREEZE_TURNS);
			//this.initializeDescription();
			this.isInnate = true;
		} else if (this.canUpgrade()) {
			this.megaUpgradeName();
			this.rawDescription = this.M_UPGRADE_DESCRIPTION;
			this.initializeDescription();
			this.upgradeMagicNumber(M_UPGRADE_PLUS_FREEZE_TURNS);
			this.retain = true;
		}
	}
}
