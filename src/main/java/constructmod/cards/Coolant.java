package constructmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import constructmod.ConstructMod;
import constructmod.actions.CoolantAction;
import constructmod.patches.AbstractCardEnum;

public class Coolant extends AbstractConstructCard {
	public static final String ID = ConstructMod.makeID("Coolant");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	public static final int COST = 1;
	public static final int BLOCK = 8;
	public static final int COOLING = 2;
	public static final int M_UPGRADE_PLUS_COOLING = 4;
	private static final int POOL = 1;

	public Coolant() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, CardType.SKILL,
				AbstractCardEnum.CONSTRUCTMOD, CardRarity.COMMON, CardTarget.SELF, POOL);
		this.block = this.baseBlock = BLOCK;
		this.magicNumber = this.baseMagicNumber = COOLING;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
		AbstractDungeon.actionManager.addToBottom(new CoolantAction(this.magicNumber));
	}

	@Override
	public AbstractCard makeCopy() {
		return new Coolant();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.rawDescription = this.UPGRADE_DESCRIPTION;
			this.initializeDescription();
			this.isInnate = true;
		} else if (this.canUpgrade()) {
			this.megaUpgradeName();
			this.upgradeMagicNumber(M_UPGRADE_PLUS_COOLING);
		}
	}
}
