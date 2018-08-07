package constructmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import constructmod.cards.AbstractConstructCard;

@SpirePatch(cls="com.megacrit.cardcrawl.helpers.CardLibrary", method = "getCopy", paramtypes = {"java.lang.String","int","int"})
public class SaveMegaUpgradesPatch {
    public static AbstractCard Postfix(AbstractCard retVal, final String key, final int upgradeTime, final int misc) {
        while (retVal.timesUpgraded < upgradeTime && retVal instanceof AbstractConstructCard){
        	((AbstractConstructCard)retVal).upgrade(true);
        }
        return retVal;
    }
}