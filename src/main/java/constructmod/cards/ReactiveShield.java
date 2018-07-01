package constructmod.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MetallicizePower;

import basemod.abstracts.CustomCard;
import constructmod.patches.AbstractCardEnum;
import constructmod.powers.ReactiveShieldPower;

public class ReactiveShield extends AbstractConstructCard {
	public static final String ID = "ReactiveShield";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	private static final String M_UPGRADE_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION[0];
	private static final int COST = 2;
	private static final int POWER_BLOCK_AMT = 3;
	private static final int UPGRADE_PLUS_POWER_BLOCK_AMT = 1;
	private static final int M_UPGRADE_PLUS_POWER_BLOCK_AMT = 1;
	private static final int POOL = 1;

	public ReactiveShield() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, AbstractCard.CardType.POWER,
				AbstractCardEnum.CONSTRUCTMOD, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF, POOL);
		this.magicNumber = this.baseMagicNumber = POWER_BLOCK_AMT;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new ReactiveShieldPower(p,this.magicNumber),this.magicNumber));
        
        if (this.megaUpgraded) {
        	AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new MetallicizePower(p,1),1));
        }
        
	}

	@Override
	public AbstractCard makeCopy() {
		return new ReactiveShield();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.isInnate = true;
			this.upgradeMagicNumber(UPGRADE_PLUS_POWER_BLOCK_AMT);
			this.rawDescription = UPGRADE_DESCRIPTION;
			this.initializeDescription();
			// upgrades to cycle
		} else if (canUpgrade()) {
			this.megaUpgradeName();
			this.upgradeMagicNumber(M_UPGRADE_PLUS_POWER_BLOCK_AMT);
			this.rawDescription = M_UPGRADE_DESCRIPTION;
			this.initializeDescription();
		}
	}
}
