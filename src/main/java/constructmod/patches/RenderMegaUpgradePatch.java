package constructmod.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;

import constructmod.ConstructMod;
import constructmod.cards.AbstractConstructCard;

public class RenderMegaUpgradePatch {
	
	@SpirePatch(clz = AbstractCard.class, method = "renderTitle")
	public static class ChangeTextColorToPurple{
		public static void Prefix(AbstractCard obj, SpriteBatch sb) {
			if (obj instanceof AbstractConstructCard && ((AbstractConstructCard)obj).megaUpgraded) {
				Settings.GREEN_TEXT_COLOR.set(1.0f, 0.5f, 1.0f, 1.0f);
			}
		}
	}
	
	@SpirePatch(clz = AbstractCard.class, method = "renderTitle")
	public static class ResetTextColor{
		public static void Postfix(AbstractCard obj, SpriteBatch sb) {
			Settings.GREEN_TEXT_COLOR.set(2147418367);
		}
	}
	
}