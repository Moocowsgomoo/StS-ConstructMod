package constructmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;

@SpirePatch(
        cls="com.megacrit.cardcrawl.cards.AbstractCard",
        method=SpirePatch.CLASS
)
public class WeddingRingPatch
{
    public static SpireField<Boolean> isMarried = new SpireField<>(()->false);
    public static SpireField<Boolean> copyIsMarried = new SpireField<>(()->false);
}
	
