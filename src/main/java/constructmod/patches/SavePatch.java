package constructmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;

import constructmod.ConstructMod;

// Patch copied from Infinite Spire (https://github.com/GraysonnG/InfiniteSpire)
public class SavePatch {
	@SpirePatch(cls = "com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue", method = "loadSaveFile")
	public static class LoadGame {
		
		public static void Prefix(AbstractPlayer.PlayerClass pClass) {
			ConstructMod.loadData();
		}
		
		public static void Prefix(String filePath) {
			ConstructMod.loadData();
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
		public static void Prefix(AbstractPlayer.PlayerClass pClass) {
			ConstructMod.clearData();
		}
	}
}