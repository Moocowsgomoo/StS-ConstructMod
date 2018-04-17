   package constructmod.actions;
   
   import com.megacrit.cardcrawl.characters.AbstractPlayer;
   import com.megacrit.cardcrawl.core.AbstractCreature;
   import com.megacrit.cardcrawl.core.CardCrawlGame;
   import com.megacrit.cardcrawl.core.Settings;
   import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
   import com.megacrit.cardcrawl.localization.UIStrings;
   
   public class GainGoldAction extends com.megacrit.cardcrawl.actions.AbstractGameAction
   {	
		private AbstractPlayer target;
		private AbstractCreature source;
		private int goldAmount;
   
		public GainGoldAction(AbstractPlayer target, AbstractCreature source, int goldAmount) {
			this.actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.SPECIAL;
			this.duration = Settings.ACTION_DUR_XFAST;
			this.target = target;
			this.source = source;
			this.goldAmount = goldAmount;
		}
     
		public void update()
		{
			com.megacrit.cardcrawl.core.CardCrawlGame.sound.play("GOLD_JINGLE");
			if (this.target.gold < -this.goldAmount) {
				this.goldAmount = -this.target.gold;
			}
			this.target.gold += this.goldAmount;
			this.isDone = true;
		}
   }