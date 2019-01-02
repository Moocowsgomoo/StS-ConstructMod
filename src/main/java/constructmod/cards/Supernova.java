package constructmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import constructmod.ConstructMod;
import constructmod.actions.ImplosionExhaustPileAction;
import constructmod.actions.SupernovaAction;
import constructmod.patches.AbstractCardEnum;

public class Supernova extends AbstractConstructCard {
	public static final String ID = ConstructMod.makeID("Supernova");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String M_UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
	private static final int COST = 3;
	public static final int UPGRADE_NEW_COST = 2;
	public static final int M_UPGRADE_NEW_COST = 1;
	private static final int POOL = 1;

	public String desc;

	public Supernova() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, CardType.SKILL,
				AbstractCardEnum.CONSTRUCTMOD, CardRarity.RARE, CardTarget.NONE, POOL);
		desc = DESCRIPTION;
		exhaust = true;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new SupernovaAction(this.megaUpgraded));
	}

	/*@Override
	public void onMoveToDiscard(){
		this.rawDescription = desc = (megaUpgraded?M_UPGRADE_DESCRIPTION:DESCRIPTION);
		initializeDescription();
	}

	public int getStatusCount(){
		int count = 0;
		for (AbstractCard c : AbstractDungeon.player.drawPile.group){
			if (c.type == CardType.STATUS) count++;
		}
		for (AbstractCard c : AbstractDungeon.player.hand.group){
			if (c.type == CardType.STATUS) count++;
		}
		for (AbstractCard c : AbstractDungeon.player.discardPile.group){
			if (c.type == CardType.STATUS) count++;
		}
		return count;
	}*/

	@Override
	public AbstractCard makeCopy() {
		return new Supernova();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeBaseCost(UPGRADE_NEW_COST);
		} else if (this.canUpgrade()) {
			this.megaUpgradeName();
			this.desc = this.rawDescription = M_UPGRADE_DESCRIPTION;
			this.initializeDescription();
		}
	}
}
