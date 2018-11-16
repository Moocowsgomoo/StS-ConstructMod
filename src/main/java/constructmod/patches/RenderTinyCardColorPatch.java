package constructmod.patches;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import com.megacrit.cardcrawl.screens.runHistory.TinyCard;

import constructmod.ConstructMod;
import constructmod.cards.AbstractConstructCard;

public class RenderTinyCardColorPatch {
	
	@SpirePatch(clz=TinyCard.class, method = "getIconBackgroundColor")
	public static class GetIconBackgroundColor {
		public static SpireReturn<Color> Prefix(TinyCard obj, AbstractCard card) {
			if (card.color == AbstractCardEnum.CONSTRUCTMOD) {
				return SpireReturn.Return(Color.YELLOW);
			}
			return SpireReturn.Continue();
		}
	}
	
	@SpirePatch(clz=TinyCard.class, method = "getIconDescriptionColor")
	public static class GetIconDescriptionColor {
		public static SpireReturn<Color> Prefix(TinyCard obj, AbstractCard card) {
			if (card.color == AbstractCardEnum.CONSTRUCTMOD) {
				return SpireReturn.Return(Color.GOLD);
			}
			return SpireReturn.Continue();
		}
	}
	
}