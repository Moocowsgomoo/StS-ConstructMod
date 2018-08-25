package constructmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

import basemod.abstracts.CustomCard;
import constructmod.ConstructMod;
import constructmod.patches.AbstractCardEnum;

public class Versatility extends AbstractConstructCard {
	public static final String ID = ConstructMod.makeID("Versatility");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String M_UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	private static final int COST = 1;
	private static final int ATTACK_DMG = 7;
	private static final int UPGRADE_PLUS_ATTACK_DMG = 3;
	private static final int BLOCK_AMT = 7;
	private static final int UPGRADE_PLUS_BLOCK_AMT = 3;
	private static final int M_UPGRADE_PLUS_ATTACK_DMG = 1;
	private static final int M_UPGRADE_PLUS_BLOCK_AMT = 1;
	private static final int POOL = 1;

	public Versatility() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, AbstractCard.CardType.ATTACK,
				AbstractCardEnum.CONSTRUCTMOD, AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.SELF_AND_ENEMY, POOL);
		this.damage = this.baseDamage = ATTACK_DMG;
		this.block = this.baseBlock = BLOCK_AMT;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		
		// fx
		//AbstractDungeon.actionManager.addToBottom(new SFXAction("THUNDERCLAP", 0.05f));
		//AbstractDungeon.actionManager.addToBottom(new VFXAction(new LightningEffect(m.drawX, m.drawY), 0.05f));
		
		// damage OR block
		if (m.intent.equals(AbstractMonster.Intent.ATTACK) || m.intent.equals(AbstractMonster.Intent.ATTACK_BUFF) || 
				m.intent.equals(AbstractMonster.Intent.ATTACK_DEBUFF) || m.intent.equals(AbstractMonster.Intent.ATTACK_DEFEND)) {
			AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p,p,this.block));
		}
		else {
			AbstractDungeon.actionManager.addToBottom(new DamageAction((AbstractCreature) m, 
					new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
		}
		
		if (this.megaUpgraded) AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p,2));
		
	}

	@Override
	public AbstractCard makeCopy() {
		return new Versatility();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeDamage(UPGRADE_PLUS_ATTACK_DMG);
			this.upgradeBlock(UPGRADE_PLUS_BLOCK_AMT);
		} else if (this.canUpgrade()) {
			this.megaUpgradeName();
			this.rawDescription = DESCRIPTION + M_UPGRADE_DESCRIPTION;
			this.initializeDescription();
			this.upgradeDamage(M_UPGRADE_PLUS_ATTACK_DMG);
			this.upgradeBlock(M_UPGRADE_PLUS_BLOCK_AMT);
		}
	}
}
