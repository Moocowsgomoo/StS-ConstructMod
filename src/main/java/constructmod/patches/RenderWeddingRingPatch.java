package constructmod.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import constructmod.ConstructMod;
import constructmod.relics.WeddingRing;

@SpirePatch(cls="com.megacrit.cardcrawl.cards.AbstractCard", method = "render", paramtypes={"com.badlogic.gdx.graphics.g2d.SpriteBatch","boolean"})
public class RenderWeddingRingPatch {
	public static void Postfix(AbstractCard obj, final SpriteBatch sb, boolean selected) {
		if (WeddingRingPatch.isMarried.get(obj) && AbstractDungeon.player.hasRelic(WeddingRing.ID)) {
			sb.draw(ConstructMod.ringIconTexture, obj.current_x - 256.0f, obj.current_y - 256.0f, 256.0f, 256.0f, 512.0f, 512.0f, 
					obj.drawScale * Settings.scale, obj.drawScale * Settings.scale, obj.angle,0,0,512,512,false,false);
        }
	}
}