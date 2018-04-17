   package constructmod.actions;
   
   import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
   import com.megacrit.cardcrawl.core.AbstractCreature;
   import com.megacrit.cardcrawl.core.Settings;
   import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
   import com.megacrit.cardcrawl.powers.DexterityPower;
   import com.megacrit.cardcrawl.powers.StrengthPower;
   
   public class DoubleStatsAction extends com.megacrit.cardcrawl.actions.AbstractGameAction
   {	
		private AbstractCreature target;
   
		public DoubleStatsAction(AbstractCreature target) {
			this.actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.SPECIAL;
			this.duration = Settings.ACTION_DUR_XFAST;
			this.target = target;
		}
     
		public void update()
		{
			if (target.hasPower("Strength")) {
				final int str = target.getPower("Strength").amount;
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
						target,target,new StrengthPower(target,str),str));
			}
			if (target.hasPower("Dexterity")) {
				final int dex = target.getPower("Dexterity").amount;
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
						target,target,new DexterityPower(target, dex),dex));
			}
			this.isDone = true;
		}
   }