package constructmod.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;

import basemod.abstracts.CustomCard;
import constructmod.ConstructMod;
import constructmod.patches.AbstractCardEnum;

public class ReactiveShield2 extends AbstractCycleCard {
	public static final String ID = ConstructMod.makeID("ReactiveShield2");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	private static final String EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION[0];
	private static final int COST = 1;
	private static final int BLOCK_INC_AMT = 4;
	private static final int M_UPGRADE_PLUS_BLOCK_INC_AMT = 3;
	private static final int POOL = 1;
	
	private String desc;

	public ReactiveShield2() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, AbstractCard.CardType.SKILL,
				AbstractCardEnum.CONSTRUCTMOD, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF, POOL);
		this.block = this.baseBlock = BLOCK_INC_AMT;
		this.desc = DESCRIPTION;
	}
	
	@Override
	public boolean canCycle() {
		int count = 0;
        for (final AbstractMonster mon : AbstractDungeon.getMonsters().monsters) {
            if (!mon.isDeadOrEscaped()) {
                ++count;
            }
        }
		return super.canCycle() && 
				this.upgraded &&
				count <= 1;
	}
	
	/*@Override
	public void applyPowers(){
		
		int count = 0;
        for (final AbstractMonster mon : AbstractDungeon.getMonsters().monsters) {
            if (!mon.isDeadOrEscaped()) {
                ++count;
            }
        }
		this.baseBlock = this.magicNumber * count;
		
		super.applyPowers();
		
		this.rawDescription = desc + EXTENDED_DESCRIPTION;
		initializeDescription();
	}
	
	@Override
    public void onMoveToDiscard() {
        this.rawDescription = desc;
        this.initializeDescription();
    }*/

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
        for (final AbstractMonster mon : AbstractDungeon.getMonsters().monsters) {
            if (!mon.isDeadOrEscaped()) {
            	AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
            }
        }
	}

	@Override
	public AbstractCard makeCopy() {
		return new ReactiveShield2();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			desc = UPGRADE_DESCRIPTION;
			this.rawDescription = desc;
			this.initializeDescription();
			// upgrades to cycle
		} else if (canUpgrade()) {
			this.megaUpgradeName();
			this.upgradeMagicNumber(M_UPGRADE_PLUS_BLOCK_INC_AMT);
		}
	}
}
