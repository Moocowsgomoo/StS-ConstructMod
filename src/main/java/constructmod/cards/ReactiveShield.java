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


import basemod.abstracts.CustomCard;
import constructmod.patches.AbstractCardEnum;

public class ReactiveShield extends AbstractCycleCard {
	public static final String ID = "ReactiveShield";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	private static final int BLOCK_AMT = 8;
	private static final int UPGRADE_PLUS_BLOCK_AMT = 3;
	private static final int POOL = 1;

	public ReactiveShield() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, AbstractCard.CardType.SKILL,
				AbstractCardEnum.CONSTRUCTMOD, AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.SELF, POOL);
		this.block = this.baseBlock = BLOCK_AMT;
	}
	
	@Override
	public void atTurnStart(){
		hasCycled = false;
	}
	
	@Override
	public void triggerWhenDrawn(){
		AbstractPlayer p = AbstractDungeon.player;
		
		boolean noneAttacking = true;
		int temp = AbstractDungeon.getCurrRoom().monsters.monsters.size();
		for (int i = 0; i < temp; i++) {
			AbstractMonster targetMonster = (AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i);
			if ((!targetMonster.isDying) && (targetMonster.currentHealth > 0) && (!targetMonster.isEscaping)) {
				if ((targetMonster.intent == AbstractMonster.Intent.ATTACK) || (targetMonster.intent == AbstractMonster.Intent.ATTACK_BUFF) || (targetMonster.intent == AbstractMonster.Intent.ATTACK_DEBUFF) || (targetMonster.intent == AbstractMonster.Intent.ATTACK_DEFEND)){
					noneAttacking = false;
					break;
				}
			}
		}
		if (!noneAttacking) return;
		
		cycle();
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
	}

	@Override
	public AbstractCard makeCopy() {
		return new ReactiveShield();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeBlock(UPGRADE_PLUS_BLOCK_AMT);
		}
	}
}
