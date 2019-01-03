package constructmod.cards;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import constructmod.ConstructMod;
import constructmod.patches.AbstractCardEnum;
import constructmod.powers.PanicFirePower;

public class PanicFire extends AbstractConstructCard {
	public static final String ID = ConstructMod.makeID("PanicFire");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final String M_UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	private static final int COST = 2;
	private static final int POWER_DAMAGE = 8;
	private static final int UPGRADE_POWER_DAMAGE = 4;
	private static final int POOL = 1;
	
	private static final ArrayList<AbstractCard> cards = new ArrayList<AbstractCard>();

	public PanicFire() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, AbstractCard.CardType.POWER,
				AbstractCardEnum.CONSTRUCTMOD, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.SELF, POOL);
		this.magicNumber = this.baseMagicNumber = POWER_DAMAGE;
		
		cards.add(new FlameCore());
        cards.add(new LaserCore());
        cards.add(new ScopeCore());
        cards.add(new ForceCore());
        cards.add(new GuardCore());
        cards.add(new BatteryCore());
		
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PanicFirePower(p, this.magicNumber), this.magicNumber));
		
		if (this.megaUpgraded) {
			for (int i=0;i<3;i++) {
		    	AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(ConstructMod.getRandomCore(),1,true,true));
	    	}
		}
	}

	@Override
	public AbstractCard makeCopy() {
		return new PanicFire();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPGRADE_POWER_DAMAGE);
		} else if (this.canUpgrade()) {
			this.megaUpgradeName();
			this.rawDescription = DESCRIPTION + M_UPGRADE_DESCRIPTION;
			this.initializeDescription();
		}
	}
}
