package constructmod.characters;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import basemod.abstracts.CustomPlayer;
import basemod.animations.SpriterAnimation;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.characters.Defect;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import constructmod.ConstructMod;
import constructmod.patches.TheConstructEnum;

public class TheConstruct extends CustomPlayer{
	public static final int ENERGY_PER_TURN = 3;
	
	
	public TheConstruct(String name, PlayerClass setClass) {
		super(name, setClass, null, null, null, new SpriterAnimation("img/char/construct/anim/Construct.scml"));
		
		initializeClass(null, "img/char/construct/shoulder2.png", "img/char/construct/shoulder.png", "img/char/construct/corpse.png",
				getLoadout(), 20.0F, -10.0F, 220.0F, 290.0F, new EnergyManager(ENERGY_PER_TURN));
		
		//loadAnimation("img/char/construct/skeleton.atlas", "img/char/construct/skeleton.json", 1.0F);
		
		//AnimationState.TrackEntry e = this.state.setAnimation(0, "animation", true);
		//e.setTime(e.getEndTime() * MathUtils.random());
	}

	public static ArrayList<String> getStartingDeck() {
		ArrayList<String> retVal = new ArrayList<>();
		retVal.add("Strike_Gold");
		retVal.add("Strike_Gold");
		retVal.add("Strike_Gold");
		retVal.add("Strike_Gold");
		retVal.add("Strike_Gold");
		retVal.add("Defend_Gold");
		retVal.add("Defend_Gold");
		retVal.add("Defend_Gold");
		retVal.add("Defend_Gold");
		retVal.add("Defend_Gold");
		retVal.add("AttackMode");
		retVal.add("DefenseMode");
		return retVal;
	}
	
	public static ArrayList<String> getStartingRelics() {
		ArrayList<String> retVal = new ArrayList<>();
		if (ConstructMod.phoenixStart) {
			retVal.add("ClockworkPhoenix");
			UnlockTracker.markRelicAsSeen("ClockworkPhoenix");
		}
		else {
			retVal.add("Cogwheel");
			UnlockTracker.markRelicAsSeen("Cogwheel");
		}
		return retVal;
	}
	
	public static CharSelectInfo getLoadout() {
		return new CharSelectInfo("The Construct", "Ancient machinery given new purpose by Neow. NL Reconfigures itself to adapt to any situation.",
				85, 85, 0, 99, 5,
			TheConstructEnum.THE_CONSTRUCT_MOD, getStartingRelics(), Defect.getStartingDeck(), false);
	}
	
	/*public void onCycle(AbstractCard card) {
		for (final AbstractPower p : this.powers) {
            p.onDrawOrDiscard();
        }
        for (final AbstractRelic r : this.relics) {
            r.onDrawOrDiscard();
        }
	}*/
	
}
