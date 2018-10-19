package constructmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;

import constructmod.ConstructMod;

// Patch copied from Infinite Spire (https://github.com/GraysonnG/InfiniteSpire)
public class SavePatch {
	/*@SpirePatch(cls = "com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue", method = "loadSaveFile", paramtypes = {"com.megacrit.cardcrawl.characters.AbstractPlayer$PlayerClass"})
	public static class LoadGame {
		
		public static void Prefix(AbstractPlayer.PlayerClass pClass) {
			ConstructMod.loadRingData();
		}
	}*/
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue", method = "loadSaveFile", paramtypes = {"java.lang.String"})
	public static class LoadGameByString{
		
		public static void Prefix(String filePath) {
			ConstructMod.loadRingData();
		}
	}
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue", method = "save")
	public static class SaveGame {
		
		public static void Prefix(SaveFile save) {
			ConstructMod.saveData();
		}
	}
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue", method = "deleteSave")
	public static class DeleteSave {
		public static void Prefix(AbstractPlayer player) {
			ConstructMod.clearData();
		}
	}
}