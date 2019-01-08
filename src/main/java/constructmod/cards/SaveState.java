package constructmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import constructmod.ConstructMod;
import constructmod.patches.AbstractCardEnum;
import constructmod.powers.RetainAllPower;

public class SaveState extends AbstractConstructCard {
	public static final String ID = ConstructMod.makeID("SaveState");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	public static final String M_UPGRADE_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION[0];
	public static final int COST = 0;
	public static final int RETAIN_TURNS = 1;
	public static final int M_UPGRADE_PLUS_RETAIN_TURNS = 1;
	private static final int POOL = 1;

	private int energyGain;

	public SaveState() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, CardType.SKILL,
				AbstractCardEnum.CONSTRUCTMOD, CardRarity.COMMON, CardTarget.SELF, POOL);
		this.baseMagicNumber = this.magicNumber = RETAIN_TURNS;
		this.exhaust = true;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new RetainAllPower(p,this.magicNumber),this.magicNumber));
		if (upgraded) AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p,1));
	}
	
	@Override
	public AbstractCard makeCopy() {
		return new SaveState();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.rawDescription = UPGRADE_DESCRIPTION;
			this.initializeDescription();
		} else if (canUpgrade()) {
			this.megaUpgradeName();
			this.rawDescription = M_UPGRADE_DESCRIPTION;
			this.initializeDescription();
			this.upgradeMagicNumber(M_UPGRADE_PLUS_RETAIN_TURNS);
		}
	}
}
