package constructmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;

import constructmod.ConstructMod;
import constructmod.patches.AbstractCardEnum;

public class BubbleShield extends AbstractCycleCard {
	public static final String ID = ConstructMod.makeID("BubbleShield");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	public static final String M_UPGRADE_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION[0];
	private static final int COST = 0;
	private static final int PLUS_DEX = 0;
	private static final int UPGRADE_PLUS_DEX = 1;
	private static final int M_UPGRADE_PLUS_DEX = 1;
	private static final int POOL = 1;

	public BubbleShield() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, AbstractCard.CardType.SKILL,
				AbstractCardEnum.CONSTRUCTMOD, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF, POOL);
		this.baseMagicNumber = this.magicNumber = PLUS_DEX;
		this.exhaust = true;
	}
	
	@Override
	public boolean canCycle() {
		AbstractPlayer p = AbstractDungeon.player;
		return super.canCycle() &&
				(!p.hasPower("Dexterity") || p.getPower("Dexterity").amount <= 0);
				
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		if (!p.hasPower("Dexterity")) return;
		
		int dex = p.getPower("Dexterity").amount + this.magicNumber;
		if (megaUpgraded) AbstractDungeon.actionManager.addToBottom(
				new ApplyPowerAction(p, p, new DexterityPower(p, this.magicNumber), this.magicNumber));
		if (dex > 0){
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PlatedArmorPower(p, dex), dex));
		}
	}

	@Override
	public AbstractCard makeCopy() {
		return new BubbleShield();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPGRADE_PLUS_DEX);
			this.rawDescription = UPGRADE_DESCRIPTION;
			this.initializeDescription();
		} else if (this.canUpgrade()) {
			this.megaUpgradeName();
			this.upgradeMagicNumber(M_UPGRADE_PLUS_DEX);
			this.rawDescription = M_UPGRADE_DESCRIPTION;
			this.initializeDescription();
		}
	}
}
