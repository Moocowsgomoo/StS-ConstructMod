package constructmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import constructmod.ConstructMod;
import constructmod.actions.MultistageAction;
import constructmod.patches.AbstractCardEnum;

public class Multistage extends AbstractConstructCard {
	public static final String ID = ConstructMod.makeID("Multistage");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = -1;
	private static final int TURNS = 2;
	private static final int UPGRADE_PLUS_TURNS = 1;
	private static final int M_UPGRADE_PLUS_TURNS = 2;
	private static final int POOL = 1;

	public Multistage() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, CardType.SKILL,
				AbstractCardEnum.CONSTRUCTMOD, CardRarity.RARE, CardTarget.NONE, POOL);
		this.magicNumber = this.baseMagicNumber = TURNS;
		this.exhaust = true;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new MultistageAction(p, this.freeToPlayOnce, this.energyOnUse, this.magicNumber));
	}

	@Override
	public AbstractCard makeCopy() {
		return new Multistage();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPGRADE_PLUS_TURNS);
		} else if (this.canUpgrade()) {
			this.megaUpgradeName();
			this.upgradeMagicNumber(M_UPGRADE_PLUS_TURNS);
		}
	}
}
