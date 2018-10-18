package constructmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.events.beyond.SensoryStone;
import constructmod.cards.AbstractConstructCard;

import java.util.ArrayList;

@SpirePatch(clz= SensoryStone.class, method="getRandomMemory")
public class SensoryStonePatch {
	@SpireInsertPatch(rloc=2, localvars = {"memories"})
	public static void Insert(SensoryStone obj, ArrayList<String> memories) {
		final String MEMORY_TEXT =
						"#y~LONELINESS.~ NL " +
						" NL " +
						"When the first one fell, they built two more to take its place. NL " +
						"They #ralways make backups. #y~...Don't~ #y~they?~ NL " +
						" NL " +
						"#yPAIRED #yGEOMETRY: 2,947,238,001 NL " +
						"#yBYRD #yASPIRANT: 1,024,985,212,998 NL " +
						"#yPYRAMIDAL #yCONSTRUCT: ~1~ NL " +
						" NL " +
						"There are, of course, legends of others who cannot be replaced. NL " +
						"#rA #rmasked #rdemon. #gA #gdeadly #gwraith. #bAn #becho #bof #bthe #bpast. NL " +
						"And the one behind it all... NL " +
						"@[#3333FF]\"BEST@ @[#3333FF]TWO@ @[#3333FF]OUT@ @[#3333FF]OF@ @[#3333FF]THREE?\"@";
		memories.add(MEMORY_TEXT);
	}
}
