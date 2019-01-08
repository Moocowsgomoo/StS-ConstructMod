package constructmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import constructmod.ConstructMod;
import constructmod.actions.OverheatAction;
import constructmod.patches.AbstractCardEnum;

import java.util.ArrayList;

public class MoltenSmash extends AbstractConstructCard {
	public static final String ID = ConstructMod.makeID("MoltenSmash");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 2;
	private static final int VALUE = 15;
	private static final int UPGRADE_PLUS_VALUE = 3;
	private static final int M_UPGRADE_PLUS_VALUE = 4;
	private static final int POOL = 1;

	public AbstractCard leftCard;
	public AbstractCard rightCard;

	public MoltenSmash() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, CardType.ATTACK,
				AbstractCardEnum.CONSTRUCTMOD, CardRarity.UNCOMMON, CardTarget.ENEMY, POOL);
		this.damage = this.baseDamage = VALUE;
		this.block = this.baseBlock = VALUE;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new DamageAction(m,
					new DamageInfo(p, this.damage, this.damageTypeForTurn),
					AbstractGameAction.AttackEffect.BLUNT_HEAVY));
		AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
		if (this.leftCard != null){
			AbstractDungeon.actionManager.addToBottom(new OverheatAction(this.leftCard,false));
		}
		if (this.rightCard != null){
			AbstractDungeon.actionManager.addToBottom(new OverheatAction(this.rightCard,false));
		}
	}

	@Override
	public void applyPowers(){
		super.applyPowers();
		leftCard = null;
		rightCard = null;
		ArrayList<AbstractCard> hand = AbstractDungeon.player.hand.group;
		int i = hand.indexOf(this);
		if (i==-1) return;
		// we could expand this to hit cards in draw/discard piles too if played from those piles,
		// but I feel like that would be unclear. May change later.

		if (i > 0) leftCard = hand.get(i-1);
		if (i < hand.size()-1) rightCard = hand.get(i+1);
	}

	@Override
	public void calculateCardDamage(AbstractMonster m){
		super.calculateCardDamage(m);

		leftCard = null;
		rightCard = null;
		ArrayList<AbstractCard> hand = AbstractDungeon.player.hand.group;
		int i = hand.indexOf(this);
		if (i==-1) return;
		if (i > 0) leftCard = hand.get(i-1);
		if (i < hand.size()-1) rightCard = hand.get(i+1);
	}

	@Override
	public AbstractCard makeCopy() {
		return new MoltenSmash();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeDamage(UPGRADE_PLUS_VALUE);
			this.upgradeBlock(UPGRADE_PLUS_VALUE);
		} else if (this.canUpgrade()) {
			this.megaUpgradeName();
			this.upgradeDamage(M_UPGRADE_PLUS_VALUE);
			this.upgradeBlock(M_UPGRADE_PLUS_VALUE);
		}
	}
}
