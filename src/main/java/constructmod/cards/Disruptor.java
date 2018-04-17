package constructmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import basemod.abstracts.CustomCard;
import constructmod.patches.AbstractCardEnum;
import constructmod.powers.AutoturretPower;
import constructmod.powers.DisruptorPower;
import constructmod.powers.PanicFirePower;
import constructmod.powers.SiegeFormPower;
import constructmod.powers.SpinDrivePower;

public class Disruptor extends CustomCard {
	public static final String ID = "Disruptor";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	private static final int UPGRADE_NEW_COST = 0;
	private static final int POOL = 1;

	public Disruptor() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, AbstractCard.CardType.POWER,
				AbstractCardEnum.CONSTRUCT_MOD_COLOR, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF, POOL);
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DisruptorPower(p)));
	}

	@Override
	public AbstractCard makeCopy() {
		return new Disruptor();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			//this.upgradeMagicNumber(UPGRADE_POWER_DAMAGE);
			this.upgradeBaseCost(UPGRADE_NEW_COST);
		}
	}
}
