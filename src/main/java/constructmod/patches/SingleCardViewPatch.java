package constructmod.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.unique.RegenAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import constructmod.ConstructMod;
import constructmod.cards.AbstractConstructCard;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import javassist.expr.NewExpr;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class SingleCardViewPatch {
    @SpirePatch(clz= SingleCardViewPopup.class, method=SpirePatch.CLASS)
    public static class SingleCardViewFields {

        public static final float UPGRADE_ARROW_H_OFFSET = 10.0f * Settings.scale;
        public static final float DOWNGRADE_ARROW_H_OFFSET = -20.0f * Settings.scale;

        public static SpireField<Integer> numUpgrades = new SpireField<>(()->0);
        public static SpireField<Boolean> showArrows = new SpireField<>(()->true);
        //public static SpireField<Hitbox> upgradeArrow = new SpireField<>(()->new Hitbox(250.0f * Settings.scale, 80.0f * Settings.scale));
        //public static SpireField<Hitbox> downgradeArrow = new SpireField<>(()->new Hitbox(250.0f * Settings.scale, 80.0f * Settings.scale));
    }

    @SpirePatch(clz= SingleCardViewPopup.class, method="close")
    public static class OnClose {
        public static void Postfix(SingleCardViewPopup obj){
            SingleCardViewFields.numUpgrades.set(obj,0);
        }
    }

    @SpirePatch(clz= SingleCardViewPopup.class, method="render")
    public static class RenderManyUpgrades {

        public static Field cardField;

        @SpireInsertPatch(locator = Locator.class)
        public static void Insert(SingleCardViewPopup obj, SpriteBatch sb){

            if (cardField == null){
                // get a reference to cardField - just need to do this once
                try {
                    cardField = obj.getClass().getDeclaredField("card");
                    cardField.setAccessible(true);
                }
                catch (NoSuchFieldException e){
                    e.printStackTrace();
                }
            }

            // upgrade preview card a bunch of times - this happens every frame for some reason
            try {
                // upgrade is called once after this, so upgrade one less time than expected.
                for (int i=1;i<SingleCardViewFields.numUpgrades.get(obj);i++) {
                    ((AbstractCard) (cardField.get(obj))).upgrade();
                }
            }
            catch (IllegalAccessException e){
                e.printStackTrace();
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {

                Matcher finalMatcher = new Matcher.MethodCallMatcher(
                        AbstractCard.class, "upgrade");

                return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
            }
        }
    }

    @SpirePatch(clz= SingleCardViewPopup.class, method="renderUpgradeViewToggle")
    public static class RenderCheckboxPatch{
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                @Override
                public void edit(MethodCall m) throws CannotCompileException
                {
                    if (m.getClassName().equals(SpriteBatch.class.getName()) && m.getMethodName().equals("draw")) {
                        m.replace("{$1 = " + ImageMaster.class.getName() +".CF_LEFT_ARROW;" + // change from rendering checkbox to arrow
                                " $3 = $3 + " + SingleCardViewFields.class.getName() + ".UPGRADE_ARROW_H_OFFSET; " + // move position up
                                " $10 = -90.0f;" + // rotate arrow to face upwards (pointing up/down on vertical plane)
                                "$proceed($$); " + // render upgrade arrow
                                " $3 = $3 - " + SingleCardViewFields.class.getName() + ".UPGRADE_ARROW_H_OFFSET; " + // undo the last position change
                                " $3 = $3 + " + SingleCardViewFields.class.getName() + ".DOWNGRADE_ARROW_H_OFFSET;" + // move position down
                                " $1 = " + ImageMaster.class.getName() +".CF_RIGHT_ARROW;" + // change to render the other directional arrow
                                " $proceed($$); }"); // render downgrade arrow
                    }
                }
            };
        }
    }
}
