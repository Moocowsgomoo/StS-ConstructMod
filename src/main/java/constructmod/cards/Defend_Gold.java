package constructmod.cards;

import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;

import constructmod.ConstructMod;
import constructmod.patches.AbstractCardEnum;

public class Defend_Gold extends AbstractCycleCard {
	public static final String ID = ConstructMod.makeID("Defend_Gold");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	private static final int BLOCK_AMT = 5;
	private static final int UPGRADE_PLUS_BLOCK = 3;
	private static final int M_UPGRADE_PLUS_BLOCK = 6;
	private static final int POOL = 1;

	public Defend_Gold() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, AbstractCard.CardType.SKILL,
				AbstractCardEnum.CONSTRUCTMOD, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.SELF, POOL);
		this.baseBlock = BLOCK_AMT;
		this.tags.add(BaseModCardTags.BASIC_DEFEND);
	}
	
	@Override
	public boolean canCycle() {
		return super.canCycle() && 
				AbstractDungeon.player.hasPower(DexterityPower.POWER_ID) && 
				AbstractDungeon.player.getPower(DexterityPower.POWER_ID).amount < 0;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
	}

	@Override
	public AbstractCard makeCopy() {
		return new Defend_Gold();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeBlock(UPGRADE_PLUS_BLOCK);
		} else if (this.canUpgrade()){
			this.megaUpgradeName();
			this.upgradeBlock(M_UPGRADE_PLUS_BLOCK);
		}
	}
}
