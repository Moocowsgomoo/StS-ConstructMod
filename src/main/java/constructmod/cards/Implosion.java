package constructmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import constructmod.ConstructMod;
import constructmod.actions.ImplosionAction;
import constructmod.actions.ImplosionExhaustPileAction;
import constructmod.patches.AbstractCardEnum;

public class Implosion extends AbstractConstructCard {
	public static final String ID = ConstructMod.makeID("Implosion");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String M_UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	private static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
	private static final int COST = 2;
	public static final int UPGRADE_NEW_COST = 1;
	public static final int M_UPGRADE_NEW_COST = 0;
	private static final int POOL = 1;

	public String desc;

	public Implosion() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, CardType.SKILL,
				AbstractCardEnum.CONSTRUCTMOD, CardRarity.UNCOMMON, CardTarget.NONE, POOL);
		desc = DESCRIPTION;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new ImplosionExhaustPileAction());
	}

	@Override
	public void applyPowers(){
		super.applyPowers();

		if (this.megaUpgraded) this.retain = true;

		this.rawDescription = EXTENDED_DESCRIPTION[0] + getBurnInPile(AbstractDungeon.player.exhaustPile)+ EXTENDED_DESCRIPTION[1]+desc;
		initializeDescription();
	}

	@Override
	public void onMoveToDiscard(){
		this.rawDescription = desc;
		initializeDescription();
	}

	public int getBurnInPile(CardGroup group){
		int count = 0;
		for (AbstractCard c:group.group){
			if (c.cardID == Burn.ID) count++;
		}
		return count;
	}

	@Override
	public AbstractCard makeCopy() {
		return new Implosion();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeBaseCost(UPGRADE_NEW_COST);
		} else if (this.canUpgrade()) {
			this.megaUpgradeName();
			this.upgradeBaseCost(M_UPGRADE_NEW_COST);
			this.desc = this.rawDescription = M_UPGRADE_DESCRIPTION;
			this.initializeDescription();
			this.retain = true;
		}
	}
}
