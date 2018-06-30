   package constructmod.actions;
   
   import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
   import com.megacrit.cardcrawl.core.Settings;
   import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
   import com.megacrit.cardcrawl.powers.DexterityPower;
   import com.megacrit.cardcrawl.powers.StrengthPower;
   
   public class BackfireDamageAction extends com.megacrit.cardcrawl.actions.AbstractGameAction
   {
	   
		public BackfireDamageAction(int amount) {
			this.actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.DAMAGE;
			this.duration = Settings.ACTION_DUR_XFAST;
			this.target = AbstractDungeon.player;
			this.amount = amount;
		}
     
		public void update()
		{
			if (!AbstractDungeon.getCurrRoom().isBattleEnding() || AbstractDungeon.getCurrRoom().isBattleOver) {
				AbstractDungeon.actionManager.addToTop(new com.megacrit.cardcrawl.actions.common.DamageAction(this.target,
						new DamageInfo(this.target, this.amount, DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
			}
			this.isDone = true;
		}
   }