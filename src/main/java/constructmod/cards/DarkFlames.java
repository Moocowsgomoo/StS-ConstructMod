package constructmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ScreenOnFireEffect;
import constructmod.ConstructMod;
import constructmod.actions.MakeTempCardInExhaustAction;
import constructmod.patches.AbstractCardEnum;

public class DarkFlames extends AbstractConstructCard {
	public static final String ID = ConstructMod.makeID("DarkFlames");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	public static final String M_UPGRADE_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION[0];
	public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
	public static final int DAMAGE = 3;
	public static final int ADD_BURNS = 0;
	public static final int M_UPGRADE_PLUS_ADD_BURNS = 2;
	public static final int COST = 1;
	private static final int POOL = 1;

	public String desc;

	public DarkFlames() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, CardType.ATTACK,
				AbstractCardEnum.CONSTRUCTMOD, CardRarity.RARE, CardTarget.ENEMY, POOL);
		this.magicNumber = this.baseMagicNumber = ADD_BURNS;
		this.damage = this.baseDamage = DAMAGE;
		desc = DESCRIPTION;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new VFXAction(new ScreenOnFireEffect(),0.2f));
		int burnCount = getBurnInPile(AbstractDungeon.player.exhaustPile) + this.magicNumber;
		AbstractDungeon.actionManager.addToBottom(new MakeTempCardInExhaustAction(new Burn(),this.magicNumber));
		for (int i = 0;i<burnCount;i++){
			AbstractDungeon.actionManager.addToBottom(new DamageAction(m,new DamageInfo(p,this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.FIRE));
			AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
		}
	}

	@Override
	public void applyPowers(){
		super.applyPowers();
		if (this.magicNumber > 0){
			this.rawDescription = EXTENDED_DESCRIPTION[1] + getBurnInPile(AbstractDungeon.player.exhaustPile) + EXTENDED_DESCRIPTION[2] + this.magicNumber + EXTENDED_DESCRIPTION[3] + desc;
		}
		else{
			this.rawDescription = EXTENDED_DESCRIPTION[1] + getBurnInPile(AbstractDungeon.player.exhaustPile) + EXTENDED_DESCRIPTION[3] + desc;
		}
		initializeDescription();
	}

	@Override
	public void onMoveToDiscard(){
		this.rawDescription = DESCRIPTION;
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
		return new DarkFlames();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			desc = this.rawDescription = this.UPGRADE_DESCRIPTION;
			this.initializeDescription();
			this.exhaust = false;
		} else if (this.canUpgrade()) {
			this.megaUpgradeName();
			this.upgradeMagicNumber(M_UPGRADE_PLUS_ADD_BURNS);
			desc = this.rawDescription = this.M_UPGRADE_DESCRIPTION;
			this.initializeDescription();
		}
	}
}
