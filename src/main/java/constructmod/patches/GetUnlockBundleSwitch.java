/*package constructmod.patches;

import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.unlock.AbstractUnlock;

import basemod.BaseMod;
import basemod.abstracts.CustomUnlock;
import basemod.abstracts.CustomUnlockBundle;
import constructmod.ConstructMod;

@SpirePatch(cls="com.megacrit.cardcrawl.unlock.UnlockTracker", method="getUnlockBundle")
public class GetUnlockBundleSwitch {

	public static ArrayList<AbstractUnlock> Postfix(ArrayList<AbstractUnlock> tmpBundle, Object cObj, int unlockLevel) {
		AbstractPlayer.PlayerClass chosenClass = (AbstractPlayer.PlayerClass) cObj;
		if (chosenClass == TheConstructEnum.THE_CONSTRUCT_MOD) {
			CustomUnlockBundle bundle = ConstructMod.getUnlockBundle(unlockLevel);
			if (bundle != null) {
				ArrayList<CustomUnlock> unlocks = bundle.getUnlocks();
				for (CustomUnlock unlock : unlocks) {
					tmpBundle.add(unlock);
				}
			}
		}
		return tmpBundle;
	}
	
	
}
*/