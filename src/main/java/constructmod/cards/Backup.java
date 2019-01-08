package constructmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import constructmod.ConstructMod;
import constructmod.actions.BackupAction;
import constructmod.patches.AbstractCardEnum;

public class Backup extends AbstractConstructCard {
	public static final String ID = ConstructMod.makeID("Backup");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	private static final String EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION[0];
	private static final String M_UPGRADE_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION[1];
	private static final int COST = 1;
	private static final int NUM_COPIES = 2;
	private static final int M_UPGRADE_NEW_COST = 0;
	private static final int POOL = 1;

	public Backup() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, AbstractCard.CardType.SKILL,
				AbstractCardEnum.CONSTRUCTMOD, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF, POOL);
		this.magicNumber = this.baseMagicNumber = NUM_COPIES;
		if (upgraded) this.retain = true;
		this.exhaust = true;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new BackupAction(p,this.magicNumber,false));
	}
	
	@Override
    public boolean canUse(final AbstractPlayer p, final AbstractMonster m) {
		
		if (!super.canUse(p, m)) return false;
		
		for (final AbstractCard c : p.hand.group) {
			if (!c.equals(this) && !c.rarity.equals(AbstractCard.CardRarity.RARE)) {
				return true;
			}
		}
        this.cantUseMessage = Backup.EXTENDED_DESCRIPTION;
        return false;
    }
	
	@Override
	public void applyPowers(){
		super.applyPowers();
		if (upgraded) this.retain = true;
	}

	@Override
	public AbstractCard makeCopy() {
		return new Backup();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.rawDescription = UPGRADE_DESCRIPTION;
			this.initializeDescription();
			this.retain = true;
		} else if (this.canUpgrade()) {
			this.megaUpgradeName();
			this.upgradeBaseCost(M_UPGRADE_NEW_COST);
			this.exhaust = false;
			this.rawDescription = M_UPGRADE_DESCRIPTION;
			this.initializeDescription();
		}
	}
}
