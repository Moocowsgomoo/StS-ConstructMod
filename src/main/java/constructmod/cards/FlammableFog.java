package constructmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;
import constructmod.ConstructMod;
import constructmod.patches.AbstractCardEnum;
import constructmod.powers.FutureTurnBlockPower;

public class FlammableFog extends AbstractConstructCard {
	public static final String ID = ConstructMod.makeID("FlammableFog");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String M_UPGRADE_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION[0];
	public static final int COST = 1;
	public static final int OVERHEAT = 5;
	public static final int BLOCK = 5;
	public static final int UPGRADE_PLUS_BLOCK = 2;
	private static final int POOL = 1;

	public String desc;

	public FlammableFog() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, CardType.SKILL,
				AbstractCardEnum.CONSTRUCTMOD, CardRarity.COMMON, CardTarget.SELF, POOL);
		this.overheat = OVERHEAT;
		this.desc = DESCRIPTION;
		this.baseBlock = this.block = BLOCK;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		// causes crash!
		//AbstractDungeon.actionManager.addToBottom(new VFXAction(new SmokeBombEffect(m.hb.cX, m.hb.cY), 0.01f));
		AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p,p,this.block));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new NextTurnBlockPower(p,this.block),this.block));
		if (this.megaUpgraded) AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new FutureTurnBlockPower(p,this.block),this.block));
	}

	/*@Override
	public void applyPowers(){
		this.baseBlock = this.overheat;
		super.applyPowers();
		this.rawDescription = EXTENDED_DESCRIPTION[0] + desc;
		initializeDescription();
	}*/

	/*@Override
	public void onMoveToDiscard(){
		this.rawDescription = desc;
		initializeDescription();
	}*/

	@Override
	public AbstractCard makeCopy() {
		return new FlammableFog();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeBlock(UPGRADE_PLUS_BLOCK);
			//this.upgradeOverheat(UPGRADE_PLUS_OVERHEAT);
		} else if (this.canUpgrade()) {
			this.megaUpgradeName();
			this.rawDescription = M_UPGRADE_DESCRIPTION;
			this.initializeDescription();
			//this.upgradeOverheat(M_UPGRADE_PLUS_OVERHEAT);
		}
	}
}
