package constructmod.patches;

import java.lang.reflect.Field;
import java.util.Iterator;

import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.thecity.KnowingSkull;
import com.megacrit.cardcrawl.relics.PandorasBox;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import constructmod.cards.Defend_Gold;
import constructmod.cards.Strike_Gold;

@SpirePatch(cls = "com.megacrit.cardcrawl.events.thecity.KnowingSkull", method = "ctor")
public class KnowingSkullPatch {

	@SpireInsertPatch(rloc = 5)
	public static void Insert(Object __obj_instance) {
		Field hpCost;
		try {
			KnowingSkull skull = (KnowingSkull) __obj_instance;
			hpCost = skull.getClass().getDeclaredField("hpCost");
			hpCost.setAccessible(true);
			hpCost.set(skull, 8);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}
