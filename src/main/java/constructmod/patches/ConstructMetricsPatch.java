package constructmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.metrics.Metrics;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.screens.DeathScreen;
import constructmod.ConstructMod;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ConstructMetricsPatch {

    // AAAH I HAVE NO IDEA WHAT I'M DOING

    /*@SpirePatch(clz=Metrics.class, method="sendPost", paramtypez = {String.class, String.class})
    public static class SendPostPatch {

        public static void Prefix(Metrics __instance, @ByRef String[] url, String fileName) {
            if (AbstractDungeon.player.chosenClass == TheConstructEnum.THE_CONSTRUCT_MOD) {
                url[0] = "http://localhost:8000";
                ConstructMod.logger.debug("Sending Metrics!!!");
            }
        }
    }

    @SpirePatch(clz= DeathScreen.class, method="shouldUploadMetricData")
    public static class shouldUploadMetricData {

        public static boolean Postfix(boolean __retVal) {
            if (AbstractDungeon.player.chosenClass == TheConstructEnum.THE_CONSTRUCT_MOD) {
                __retVal = Settings.UPLOAD_DATA;
            }
            return __retVal;
        }
    }

    @SpirePatch(clz=Metrics.class, method="run")
    public static class RunPatch {

        public static void Postfix(Metrics __instance) {
            if (__instance.type == Metrics.MetricRequestType.UPLOAD_METRICS && AbstractDungeon.player.chosenClass == TheConstructEnum.THE_CONSTRUCT_MOD) {
                try {
                    Method m = Metrics.class.getDeclaredMethod("gatherAllDataAndSend", boolean.class, boolean.class, MonsterGroup.class);
                    m.setAccessible(true);
                    m.invoke(__instance, __instance.death, __instance.trueVictory, __instance.monsters);
                } catch (NoSuchMethodException| InvocationTargetException |IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }*/
}