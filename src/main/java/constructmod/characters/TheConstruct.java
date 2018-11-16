package constructmod.characters;

import java.sql.Time;
import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import basemod.abstracts.CustomPlayer;
import basemod.animations.SpriterAnimation;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.characters.Defect;
import com.megacrit.cardcrawl.characters.Ironclad;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.city.Vampires;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.screens.stats.CharStat;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import constructmod.ConstructMod;
import constructmod.cards.*;
import constructmod.patches.AbstractCardEnum;
import constructmod.patches.TheConstructEnum;
import constructmod.relics.ClockworkPhoenix;
import constructmod.relics.Cogwheel;

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

	@Override
	public String getTitle(AbstractPlayer.PlayerClass playerClass){
		return "the Construct";
	}

	@Override
	public AbstractCard.CardColor getCardColor() { return AbstractCardEnum.CONSTRUCTMOD;}

	@Override
	public TextureAtlas.AtlasRegion getOrb() {
		return AbstractCard.orb_red;
	}

	@Override
	public String getSpireHeartText(){
		return "You enter self-destruct mode...";
	}

	@Override
	public Color getSlashAttackColor() {
		return Color.GOLD;
	}

	@Override
	public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
		return new AbstractGameAction.AttackEffect[] { AbstractGameAction.AttackEffect.BLUNT_LIGHT, AbstractGameAction.AttackEffect.BLUNT_LIGHT, AbstractGameAction.AttackEffect.BLUNT_LIGHT, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.BLUNT_HEAVY, AbstractGameAction.AttackEffect.FIRE };
	}

	@Override
	public String getVampireText() {
		return Vampires.DESCRIPTIONS[5];
	}

	@Override
	public Color getCardRenderColor(){
		return CardHelper.getColor(170.0f, 150.0f, 50.0f);
	}

	@Override
	public Color getCardTrailColor(){
		return CardHelper.getColor(170.0f, 150.0f, 50.0f);
	}

	@Override
	public AbstractCard getStartCardForEvent(){
		return new ModeShift();
	}

	@Override
	public int getAscensionMaxHPLoss() {
		return 5;
	}

	@Override
	public BitmapFont getEnergyNumFont() {
		return FontHelper.energyNumFontRed;
	}

	@Override
	public void doCharSelectScreenSelectEffect() {
		CardCrawlGame.sound.playV("AUTOMATON_ORB_SPAWN", 1.75f);
		CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT, true);
	}

	@Override
	public String getCustomModeCharacterButtonSoundKey() {
		return "AUTOMATON_ORB_SPAWN";
	}

	@Override
	public String getLocalizedCharacterName() {
		return "The Construct";
	}

	@Override
	public void refreshCharStat() {
		this.charStat = new CharStat(this);
	}

	@Override
	public AbstractPlayer newInstance() {
		return new TheConstruct(this.name, this.chosenClass);
	}

	public ArrayList<String> getStartingDeck() {
		ArrayList<String> retVal = new ArrayList<>();
		retVal.add(Strike_Gold.ID);
		retVal.add(Strike_Gold.ID);
		retVal.add(Strike_Gold.ID);
		retVal.add(Strike_Gold.ID);
		retVal.add(Strike_Gold.ID);
		retVal.add(Defend_Gold.ID);
		retVal.add(Defend_Gold.ID);
		retVal.add(Defend_Gold.ID);
		retVal.add(Defend_Gold.ID);
		retVal.add(Defend_Gold.ID);
		retVal.add(AttackMode.ID);
		retVal.add(DefenseMode.ID);
		return retVal;
	}
	
	public ArrayList<String> getStartingRelics() {
		ArrayList<String> retVal = new ArrayList<>();
		if (ConstructMod.phoenixStart) {
			retVal.add(ClockworkPhoenix.ID);
			UnlockTracker.markRelicAsSeen(ClockworkPhoenix.ID);
		}
		else {
			retVal.add(Cogwheel.ID);
			UnlockTracker.markRelicAsSeen(Cogwheel.ID);
		}
		return retVal;
	}
	
	public CharSelectInfo getLoadout() {
		return new CharSelectInfo("The Construct", "Ancient machinery given new purpose by Neow. NL Reconfigures itself to adapt to any situation.",
				85, 85, 0, 99, 5,
			this, getStartingRelics(), getStartingDeck(), false);
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
