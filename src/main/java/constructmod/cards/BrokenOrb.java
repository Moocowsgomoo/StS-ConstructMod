package constructmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ThornsPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.AbstractPower;


import basemod.abstracts.CustomCard;
import constructmod.ConstructMod;
import constructmod.patches.AbstractCardEnum;

public class BrokenOrb extends AbstractCycleCard {
	public static final String ID = "BrokenOrb";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 0;
	private static final int DMG = 1;
	//private static final int UPGRADE_PLUS_HP_DMG = 2;
	private static final int POOL = 4;

	public BrokenOrb() {
		super(ID, NAME, "img/cards/"+"OrbAssault"+".png", COST, DESCRIPTION, AbstractCard.CardType.STATUS,
				CardColor.COLORLESS, AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.NONE, POOL);
		this.baseMagicNumber = this.magicNumber = DMG;
	}
	
	@Override
	public void atTurnStart(){
		hasCycled = false;
	}
	
	@Override
	public void triggerWhenDrawn(){
		AbstractPlayer p = AbstractDungeon.player;
		
		if (p.hasPower("Evolve") && !p.hasPower("No Draw")) {
            p.getPower("Evolve").flash();
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, p.getPower("Evolve").amount));
        }
		
		if (hasCycled) return;
		
		flash();
		
		AbstractDungeon.actionManager.addToBottom(new DamageAction(
				p, new DamageInfo(p, this.magicNumber, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
		
		cycle();
		//if (upgraded) AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(this.makeStatEquivalentCopy(),1));
		//AbstractDungeon.player.onCycle(this);
	}
	
	/*@Override
    public boolean canUse(final AbstractPlayer p, final AbstractMonster m) {
    	return true;
    }*/

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		if (p.hasRelic("Medical Kit")) {
            this.useMedicalKit(p);
        }
		/*else {
			AbstractDungeon.actionManager.addToBottom(new DamageAction(
					p, new DamageInfo(p, this.magicNumber, DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.FIRE));
		}*/
		//if (upgraded) AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(this.makeStatEquivalentCopy(),1));
	}

	@Override
	public AbstractCard makeCopy() {
		return new BrokenOrb();
	}

	@Override
	public void upgrade() {
	}
}
