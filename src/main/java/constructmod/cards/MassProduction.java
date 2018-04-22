package constructmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import constructmod.actions.MassProductionAction;
import constructmod.patches.AbstractCardEnum;

public class MassProduction extends AbstractConstructCard {
	public static final String ID = "MassProduction";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
	private static final int COST = 3;
	private static final int UPGRADE_COST = 2;
	private static final int M_UPGRADE_COST = 1;
	private static final int POOL = 1;

	public MassProduction() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, AbstractCard.CardType.SKILL,
				AbstractCardEnum.CONSTRUCTMOD, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.SELF, POOL);
		this.exhaust = true;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new MassProductionAction());
	}
	
	@Override
    public boolean canUse(final AbstractPlayer p, final AbstractMonster m) {
		for (final AbstractCard c : p.drawPile.group) {
			if (!c.rarity.equals(AbstractCard.CardRarity.RARE)) {
				return true;
			}
		}
        this.cantUseMessage = MassProduction.EXTENDED_DESCRIPTION[0];
        return false;
    }

	@Override
	public AbstractCard makeCopy() {
		return new MassProduction();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeBaseCost(UPGRADE_COST);
		} else if (this.canUpgrade()) {
			this.megaUpgradeName();
			this.upgradeBaseCost(M_UPGRADE_COST);
		}
	}
}
