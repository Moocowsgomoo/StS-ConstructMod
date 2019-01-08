package constructmod;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import basemod.*;
import basemod.interfaces.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import constructmod.localization.ChallengeStrings;
import constructmod.patches.PhoenixBtnPatch;
import constructmod.potions.MegaPotion;
import constructmod.potions.ShiftPotion;
import constructmod.powers.AbstractOnDrawPower;
import constructmod.ui.HeatMeter;
import constructmod.variables.GatlingGunVariable;
import constructmod.variables.OverheatVariable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import basemod.helpers.RelicType;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import constructmod.cards.*;
import constructmod.relics.*;

import java.util.Properties;

import constructmod.characters.TheConstruct;
import constructmod.patches.AbstractCardEnum;
import constructmod.patches.TheConstructEnum;

@SpireInitializer
public class ConstructMod implements PostInitializeSubscriber, EditCardsSubscriber, EditRelicsSubscriber,
	EditStringsSubscriber, EditCharactersSubscriber, EditKeywordsSubscriber, PostDrawSubscriber,
		PreMonsterTurnSubscriber, RenderSubscriber, OnStartBattleSubscriber {
	
	public static final Logger logger = LogManager.getLogger(ConstructMod.class.getName());
			
    private static final String MODNAME = "Construct Mod";
    private static final String AUTHOR = "Moocowsgomoo";
    private static final String DESCRIPTION = "New Character: The Construct!";
	
	private static final Color CONSTRUCT_MOD_COLOR = CardHelper.getColor(170.0f, 150.0f, 50.0f);

	public static HeatMeter heatBar;

	public static Properties constructDefaults = new Properties();
	public static boolean phoenixStart = false;
	public static boolean contentSharing_relics = true;
	public static boolean contentSharing_potions = true;
	public static boolean overheatedExpansion = false;

	public static final String PROP_PHOENIX_START = "phoenixStart";
	public static final String PROP_RELIC_SHARING = "contentSharing";
	public static final String PROP_POTION_SHARING = "contentSharing_potions";
	public static final String PROP_OVERHEATED_BETA = "overheatedBETA";
	public static final String PROP_CHALLENGE_LEVEL = "challengeLevel";

	public static int marriedCard1 = -1;
	public static int marriedCard2 = -1;
	public static Texture ringIconTexture;

	public static int challengeLevel = 0;

	public static int cyclesThisTurn = 0;
	public static final int CYCLES_BEFORE_FASTMODE = 20;

	public static final ArrayList<AbstractCard> cores = new ArrayList<>();
	public static ArrayList<AbstractRelic> shareableRelics = new ArrayList<>();
	public static final ArrayList<AbstractRelic> challengeRelics = new ArrayList<>();
	public static final ArrayList<AbstractCard> expansionCards1 = new ArrayList<>();
	public static ArrayList<AbstractRelic> expansionRelics1 = new ArrayList<>();

	public static final boolean isReplayLoaded = Loader.isModLoaded("ReplayTheSpireMod");
	public static final boolean isInfiniteLoaded = Loader.isModLoaded("infinitespire");

	static
	{
		// CROSSOVER: Add reverse magic number tags so ring of chaos works better.
		if (isReplayLoaded) {
			logger.info("Construct | Detected Replay The Spire");
		}
		// CROSSOVER: Nothing yet, but planning to add quests.
		if (isInfiniteLoaded) {
			logger.info("Construct | Detected Infinite Spire");
		}
	}
    
    public ConstructMod() {
        BaseMod.subscribe(this);
		
		logger.info("creating the color " + AbstractCardEnum.CONSTRUCTMOD.toString());
        BaseMod.addColor(AbstractCardEnum.CONSTRUCTMOD,
        		CONSTRUCT_MOD_COLOR, CONSTRUCT_MOD_COLOR, CONSTRUCT_MOD_COLOR, CONSTRUCT_MOD_COLOR, CONSTRUCT_MOD_COLOR, CONSTRUCT_MOD_COLOR, CONSTRUCT_MOD_COLOR,
        		"img/512/bg_attack_gold.png", "img/512/bg_skill_gold.png",
        		"img/512/bg_power_gold.png", "img/512/card_gold_orb.png",
				"img/1024/bg_attack_gold.png", "img/1024/bg_skill_gold.png",
        		"img/1024/bg_power_gold.png", "img/1024/card_gold_orb.png");
				
		Settings.isDailyRun = false;
        Settings.isTrial = false;
        Settings.isDemo = false;

		constructDefaults.setProperty(PROP_PHOENIX_START, "FALSE");
		constructDefaults.setProperty(PROP_RELIC_SHARING, "TRUE");
		constructDefaults.setProperty(PROP_POTION_SHARING, "TRUE");
		constructDefaults.setProperty(PROP_OVERHEATED_BETA, "FALSE");
		constructDefaults.setProperty(PROP_CHALLENGE_LEVEL, "0");
		loadConfigData();
    }
    
    public static void initialize() {
        ConstructMod mod = new ConstructMod();
    }
    
    public void receivePostInitialize() {

		//CardCrawlGame.cardPopup = new MultiUpgradeSingleCardViewPopup();

		if (UnlockTracker.unlockProgress.getInteger(TheConstructEnum.THE_CONSTRUCT_MOD+"UnlockLevel") < 3){
			UnlockTracker.unlockProgress.putInteger(TheConstructEnum.THE_CONSTRUCT_MOD + "UnlockLevel", 3);
			UnlockTracker.unlockProgress.putInteger(TheConstructEnum.THE_CONSTRUCT_MOD + "Progress", 0);
			UnlockTracker.unlockProgress.putInteger(TheConstructEnum.THE_CONSTRUCT_MOD + "CurrentCost", 500);
			UnlockTracker.unlockProgress.flush();
		}

        // Mod badge
        Texture badgeTexture = new Texture(Gdx.files.internal("img/ConstructModBadge.png"));

		heatBar = new HeatMeter();
        
        ModPanel settingsPanel = new ModPanel();
		ModLabeledToggleButton contentSharingBtn = new ModLabeledToggleButton("Allow other characters to encounter some Construct relics.",
				350.0f, 650.0f, Settings.CREAM_COLOR, FontHelper.charDescFont,
				contentSharing_relics, settingsPanel, (label) -> {}, (button) -> {
					contentSharing_relics = button.enabled;
					adjustRelics();
					saveData();
				});
		ModLabeledToggleButton contentSharingPotionsBtn = new ModLabeledToggleButton("Allow other characters to encounter some Construct potions. (REQUIRES RESTART)",
				350.0f, 600.0f, Settings.CREAM_COLOR, FontHelper.charDescFont,
				contentSharing_potions, settingsPanel, (label) -> {}, (button) -> {
			contentSharing_potions = button.enabled;
			//adjustPotions();
			saveData();
		});
		ModButton unlockAllButton = new ModButton(350.0f,480.0f,settingsPanel,(btn)->{
			UnlockTracker.unlockProgress.putInteger(TheConstructEnum.THE_CONSTRUCT_MOD + "UnlockLevel", 5);
			UnlockTracker.unlockProgress.flush();
			PhoenixBtnPatch.shouldRefreshUnlocks = true;
			CardCrawlGame.sound.playA("UNLOCK_PING", -0.1f);
		});
		ModLabel unlockAllTxt = new ModLabel("Set Unlock level to MAX. Unlocks appear on the character select screen.",470.0f, 540.0f,FontHelper.charDescFont,settingsPanel,(me)->{});

		UIStrings challengeStrings = CardCrawlGame.languagePack.getUIString("constructChallengeLevels");

		ModLabel challengeIntroTxt1 = new ModLabel("Experimental future levels for Challenge Mode. These are subject to change.",350.0f, 430.0f,FontHelper.charDescFont,settingsPanel,(me)->{});
		ModLabel challengeIntroTxt2 = new ModLabel("Currently 4 levels exist. The focus is on polishing these and eventually adding a 5th.",350.0f, 400.0f,FontHelper.charDescFont,settingsPanel,(me)->{});
		ModLabel challengeLabelTxt = new ModLabel("Challenge Level:",350.0f, 350.0f,settingsPanel,(me)->{});
		ModLabel challengeLevelTxt = new ModLabel(""+challengeLevel,650.0f, 350.0f,settingsPanel,(me)->{});
		ModLabel challengeDescTxt = new ModLabel(challengeStrings.TEXT[challengeLevel],400.0f, 300.0f,FontHelper.charDescFont,settingsPanel,(me)->{});
		ModButton challengeLeftBtn = new ModButton(605.0f, 340.0f, ImageMaster.loadImage("img/tinyLeftArrow.png"),settingsPanel,(me)->{
			if (challengeLevel > 0) challengeLevel--;
			challengeLevelTxt.text = "" + challengeLevel;
			challengeDescTxt.text = challengeStrings.TEXT[challengeLevel];
			resetCharSelect();
			saveData();
		});
		ModButton challengeRightBtn = new ModButton(665.0f, 340.0f, ImageMaster.loadImage("img/tinyRightArrow.png"),settingsPanel,(me)->{
			if (challengeLevel < 4) challengeLevel++;
			challengeLevelTxt.text = "" + challengeLevel;
			challengeDescTxt.text = challengeStrings.TEXT[challengeLevel];
			resetCharSelect();
			saveData();
		});

        settingsPanel.addUIElement(contentSharingBtn);
        settingsPanel.addUIElement(contentSharingPotionsBtn);
		if (UnlockTracker.getUnlockLevel(TheConstructEnum.THE_CONSTRUCT_MOD) < 5){
			settingsPanel.addUIElement(unlockAllButton);
			settingsPanel.addUIElement(unlockAllTxt);
		}
        settingsPanel.addUIElement(challengeIntroTxt1);
        settingsPanel.addUIElement(challengeIntroTxt2);
        settingsPanel.addUIElement(challengeLevelTxt);
        settingsPanel.addUIElement(challengeLabelTxt);
        settingsPanel.addUIElement(challengeDescTxt);
        settingsPanel.addUIElement(challengeLeftBtn);
        settingsPanel.addUIElement(challengeRightBtn);
        
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);
        
        ringIconTexture = new Texture("img/512/card_ring_icon.png");

        if (contentSharing_potions) {
			BaseMod.addPotion(ShiftPotion.class, Color.CHARTREUSE.cpy(), Color.RED.cpy(), null, ShiftPotion.POTION_ID);
		}
		else{
			BaseMod.addPotion(ShiftPotion.class, Color.CHARTREUSE.cpy(), Color.RED.cpy(), null, ShiftPotion.POTION_ID, TheConstructEnum.THE_CONSTRUCT_MOD);
		}
		BaseMod.addPotion(MegaPotion.class, Color.PURPLE.cpy(), Color.VIOLET.cpy(), Color.PURPLE.cpy(), MegaPotion.POTION_ID, TheConstructEnum.THE_CONSTRUCT_MOD);
    }
    
    public static void saveData() {
    	try {
    		SpireConfig config = new SpireConfig("ConstructMod", "ConstructSaveData", constructDefaults);
    		config.setBool(PROP_PHOENIX_START, phoenixStart);
    		config.setBool(PROP_RELIC_SHARING, contentSharing_relics);
			config.setBool(PROP_POTION_SHARING, contentSharing_potions);
    		config.setBool(PROP_OVERHEATED_BETA, overheatedExpansion);
    		config.setInt(PROP_CHALLENGE_LEVEL, challengeLevel);
    		if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(WeddingRing.ID)) {
    			config.setInt("marriedCard1", AbstractDungeon.player.masterDeck.group.indexOf(((WeddingRing)AbstractDungeon.player.getRelic(WeddingRing.ID)).card1));
    			config.setInt("marriedCard2", AbstractDungeon.player.masterDeck.group.indexOf(((WeddingRing)AbstractDungeon.player.getRelic(WeddingRing.ID)).card2));
    		}
    		config.save();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public static void clearData() {
    	marriedCard1 = marriedCard2 = -1;
    	saveData();
    }
    
    public static void loadConfigData() {
    	try {
    		logger.info("ConstructMod | Loading Config Preferences...");
	    	SpireConfig config = new SpireConfig("ConstructMod", "ConstructSaveData", constructDefaults);
			config.load();
			phoenixStart = config.getBool(PROP_PHOENIX_START);
			contentSharing_relics = config.getBool(PROP_RELIC_SHARING);
			contentSharing_potions = config.getBool(PROP_POTION_SHARING);
			overheatedExpansion = config.getBool(PROP_OVERHEATED_BETA);
			challengeLevel = config.getInt(PROP_CHALLENGE_LEVEL);
    	}
    	catch(Exception e) {
    		e.printStackTrace();
			clearData();
    	}
    }
    
    public static void loadRingData() {
    	logger.info("ConstructMod | Loading Ring Data...");
    	try {
			SpireConfig config = new SpireConfig("ConstructMod", "ConstructSaveData", constructDefaults);
			config.load();
			
			marriedCard1 = config.getInt("marriedCard1");
			marriedCard2 = config.getInt("marriedCard2");
		
		} catch (Exception e) {
			e.printStackTrace();
			clearData();
		}
    	
}
    
    @SuppressWarnings("unchecked")
    private void resetCharSelect() {
    	((ArrayList<CharacterOption>)ReflectionHacks.getPrivate(CardCrawlGame.mainMenuScreen.charSelectScreen,CharacterSelectScreen.class, "options")).clear();
    	CardCrawlGame.mainMenuScreen.charSelectScreen.initialize();
    }
    
    public void receiveEditKeywords() {
    	final String[] cycle = {"cycle", "cycles"};
    	BaseMod.addKeyword(cycle, "When drawn, discard this and draw a new card. Only works once per turn.");
    	final String[] blur = {"blur"};
    	BaseMod.addKeyword(blur, "Your Block is carried over between turns.");
    	final String[] metallicize = {"metallicize"};
    	BaseMod.addKeyword(metallicize, "Gain Block at the end of each turn.");
    	final String[] pArmor = {"plated armor"}; // not working b/c it's two words?
    	BaseMod.addKeyword(pArmor, "Gain Block at the end of each turn. Reduced when you take unblocked damage.");
    	final String[] slimed = {"slimed"};
    	BaseMod.addKeyword(slimed, "Slimed is a status card that costs [R] to exhaust.");
    	final String[] cores = {"cores"};
    	BaseMod.addKeyword(cores, "Cores are cards that #yCycle and apply a small bonus effect.");
    	final String[] eggs = {"egg","eggs"};
    	BaseMod.addKeyword(eggs, "Eggs are relics that automatically upgrade cards when you acquire them.");
    	final String[] megaUpgrade = {"mega-upgrade","mega-upgraded"};
    	BaseMod.addKeyword(megaUpgrade, "A second upgrade that makes Construct cards even more powerful.");
		final String[] overheat = {"overheat","[#ff9900]overheat","[#ff9900]overheats","[#ff9900]overheated", "[#ff9900]overheat:","overheats","overheated"};
		BaseMod.addKeyword(overheat, "When too many cards #yCycle in one turn, transform this card into a #yBurn for this combat.");
    }
	
	public void receiveEditCharacters() {
		logger.info("begin editing characters");
		
		logger.info("add " + TheConstructEnum.THE_CONSTRUCT_MOD.toString());
		BaseMod.addCharacter(new TheConstruct("The Construct", TheConstructEnum.THE_CONSTRUCT_MOD),
				"img/charSelect/constructButton.png", "img/charSelect/constructPortrait.jpg",
				TheConstructEnum.THE_CONSTRUCT_MOD);
		
		logger.info("done editing characters");
	}
	
	public void receiveEditStrings() {
		String language = "eng";
		if (Settings.language == Settings.GameLanguage.KOR) language = "kor";
        BaseMod.loadCustomStringsFile(RelicStrings.class, "localization/"+language+"/ConstructMod-RelicStrings.json");
        BaseMod.loadCustomStringsFile(CardStrings.class, "localization/"+language+"/ConstructMod-CardStrings.json");
		BaseMod.loadCustomStringsFile(PotionStrings.class, "localization/"+language+"/ConstructMod-PotionStrings.json");
		BaseMod.loadCustomStringsFile(PowerStrings.class, "localization/"+language+"/ConstructMod-PowerStrings.json");
		BaseMod.loadCustomStringsFile(UIStrings.class, "localization/"+language+"/ConstructMod-UIStrings.json");

	}
	
	public void receiveEditCards() {

    	BaseMod.addDynamicVariable(new OverheatVariable());
    	BaseMod.addDynamicVariable(new GatlingGunVariable());

		// Add cards
		logger.info("Adding Construct Cards");
		// BASIC
		addCard(new Strike_Gold());
		addCard(new Defend_Gold());
		addCard(new HeatedStrike());
		addCard(new HeatedDefend());
		addCard(new AttackMode());
		addCard(new DefenseMode());
		
		// COMMON
		//	Attacks (13/11)+2
		addCard(new Boost()); 		// atk, block
		addCard(new ScrapCannon());	// atk, exhaust
		addCard(new SweepLaser());	// atk
		addCard(new Versatility());	// atk, block (intent)
		addCard(new SuppressiveFire());// atk, defense -> UNCOMMON, replace with draw?
		addCard(new Backfire());	// atk
		addCard(new HeavyBolt());	// atk
		addCard(new FierceBash());	// atk, (intent)
		addCard(new FocusedBeam()); // atk
		addCard(new Accumulate()); 	// atk, copy
		addCard(new SyphonStrike());// atk, draw
		addCard(new ClusterMines());// atk, block-broken
		addCard(new ShiftStrike()); // atk, str/dex
		
		//	Skills (7/7)+2
		addCard(new Reinforce());	// block
		addCard(new Anticipate());	// block
		addCard(new Forcefield());  // block, retain
		addCard(new ModeShift()); 	// modes, draw
		addCard(new Analyze()); 	// draw/energy next turn
		addCard(new VentSteam());	// debuff, exhaust
		addCard(new SaveState());	// retain, draw
		
		//	Powers (1/1)
		addCard(new Autoturret());	// atk from cycle
		
		// UNCOMMON
		// 	Attacks(12/11) +2
		addCard(new ChargeShot());	// atk (retain)
		addCard(new CripplingShot());//atk, debuff
		addCard(new Electrocute()); // atk, debuff
		addCard(new FlakBarrage());	// atk (cycle)
		addCard(new Chainstrike()); // atk, cost reduction
		addCard(new CriticalHit()); // atk (cycle)
		addCard(new UnbalancingBlast()); // atk (card manip)
		addCard(new Tumble()); 		// atk, draw
		addCard(new OmegaCannon()); // atk, str-based
		addCard(new QuickAttack()); // atk, dex
		addCard(new PowerUp()); 	// atk, +basic cards
		addCard(new Antimatter());	// atk
		addCard(new Missile());		// atk, cycle
		
		//	Skills (18/17)+1
		addCard(new OneWayMirror());// block
		addCard(new Disrupt());		// block
		addCard(new Impenetrable());// block
		addCard(new Hazardproof()); // block, buff
		addCard(new MetalShell());	// block
		addCard(new ShiftGuard()); 	// block, str/dex
		addCard(new Dampening());	// block, draw, anti-cycle
		
		//addCard(new ReactiveShield2()); // block (cycle)
		//Trip Mine
		//-addCard(new Lockdown());	// block, draw
		//-Save State (use power since retain isn't cleared on discard?)
		
		//addCard(new Disorient());	// debuff
		addCard(new LaserCore());	// cycle, atk
		addCard(new FlameCore());	// cycle, atk
		addCard(new ScopeCore());	// cycle, debuff
		addCard(new ForceCore()); 	// cycle, buff
		addCard(new GuardCore());	// cycle, block
		addCard(new Backup());		// copy
		addCard(new BatteryAcid()); // energy
		addCard(new HardReboot());	// exhaust
		addCard(new Isolate()); 	// cycle, buff
		addCard(new Stasis()); 		// copy, mega-upgrade
		addCard(new ElectricArmor());//block-based
		addCard(new Multistage());	// attack combos, X-cost
		//addCard(new BubbleShield());// block-based
		
		// 	Powers (8/7)+1
		addCard(new Synchronize());	// copy-based
		addCard(new Enhance());		// upgrade
		addCard(new Overclock()); 	// burn, draw
		addCard(new Overcharge()); 	// burn, energy
		addCard(new ReactiveShield());// block-based
		addCard(new Zapper()); // defensive, stat-based
		addCard(new PointDefense()); // block;
		addCard(new Failsafe());	 // anti-status
		//-addCard(new Overpower());	// burn, stats
		//-addCard(new Disruptor());  	// block
		
		// RARE
		//	Attacks (5/5)+1
		addCard(new HyperBeam());	// atk
		addCard(new GoldenBullet());// atk
		addCard(new ShieldBurst());	// atk from block --> UNCOMMON?
		addCard(new GatlingGun());	// atk, X-cost
		addCard(new HammerDown());	// atk, modes
		//	Skills (6/7)+2
		addCard(new MassProduction());//copy
		addCard(new HastyRepair()); // heal
		addCard(new ClockworkEgg());// egg
		addCard(new BatteryCore());	// energy
		addCard(new MemoryTap());	// cards from other classes
		addCard(new Reserves()); 	// cycle, draw/energy at low HP
		//	Powers (7/5)+1
		addCard(new SiegeForm());	// buff, atk-based
		addCard(new SpinDrive()); 	// cards
		addCard(new Bunker()); 		// block --> RARE? (synergy with retain, which are mostly c/u), can't be copied!
		addCard(new PanicFire()); 	// atk & exhaust from cycle
		addCard(new LongRangeLance());
		addCard(new ShieldGenerator());//defensive
        addCard(new ShiftingStance());//swap stats
		//-addCard(new CoreStorm()); 	// atk from cycle --> Bring back old Panic Fire, now that orbs copy again?

		// MISC.
		//addCard(new CoreShard()); // status, cycle

		//ATK (-1 rare)
		// common
		addHeatCard(new PhosphorStorm());//atk, overheat 5
		addHeatCard(new Rollout());		// atk, cycle synergy, overheat 10(15)
		// uncommon
		addHeatCard(new BlazingSpeed());// atk, overheat 5
		addHeatCard(new MoltenSmash());	// atk, burns
		// rare
		addHeatCard(new DarkFlames());	// atk, energy, exhausted burns
		//addHeatCard(new Flamethrower());// atk, cycle?, burn synergy

		//SKL (+1 uncommon, rare)
		//common
		addHeatCard(new FlammableFog());// block, overheat 5
		addHeatCard(new CreateCores());	// overheat 10(15)
		//uncommon
		addHeatCard(new FlashFreeze());	// block, overheat counter/synergy
		addHeatCard(new OilSpill());	// overheat 5, overheat synergy
		addHeatCard(new NuclearCore());	// cycle, overheat 10
		addHeatCard(new Implosion());	// burn synergy, play
		//rare
		addHeatCard(new Supernova());	// anti-status
		addHeatCard(new Afterburners());// multi-play, burns

		//PWR (-1 uncommon)
		//uncommon
		addHeatCard(new Agitation());	// overheat synergy
		//addHeatCard(new Failsafe());	 // anti-status
		//rare
        addHeatCard(new Meltdown()); 	// burn, damage
		//addHeatCard(new SunScreen());	// anti-status
		//sunscreen

		if (overheatedExpansion) cores.add(new NuclearCore()); // can be randomly generated only if expansion is enabled

		// cores that can be randomly generated
		cores.add(new FlameCore());
		cores.add(new LaserCore());
		cores.add(new ScopeCore());
		cores.add(new ForceCore());
		cores.add(new GuardCore());
		cores.add(new BatteryCore());
	}

	public static void adjustCards(){

    	if (!overheatedExpansion) {
    		for (AbstractCard c : expansionCards1){
				CardLibrary.cards.remove(c.cardID);
				BaseMod.decrementCardCount(c.color);
				CardLibrary.totalCardCount--;
			}
			cores.removeIf((core)->core instanceof NuclearCore);
			CardCrawlGame.mainMenuScreen.cardLibraryScreen.initialize();
		}
		else{
			for (AbstractCard c : expansionCards1){
				CardLibrary.add(c);
				BaseMod.incrementCardCount(c.color);
				CardLibrary.totalCardCount++;
			}
			cores.add(new NuclearCore()); // can be randomly generated only if expansion is enabled
			CardCrawlGame.mainMenuScreen.cardLibraryScreen.initialize();
		}
	}

	public static void addCard(AbstractCard card){
    	BaseMod.addCard(card);
    	UnlockTracker.unlockCard(card.cardID);
	}

	public static void addHeatCard(AbstractCard card){
    	if (overheatedExpansion) {
			BaseMod.addCard(card);
			UnlockTracker.unlockCard(card.cardID);
		}
		expansionCards1.add(card);
	}

	public static AbstractCard getRandomCore(){
		return cores.get(AbstractDungeon.cardRandomRng.random(0,cores.size()-1)).makeCopy();
	}
	
	public void receiveEditRelics() {
		logger.info("Adding Construct Relics");
		BaseMod.addRelicToCustomPool(new Cogwheel(), AbstractCardEnum.CONSTRUCTMOD);
		BaseMod.addRelicToCustomPool(new MasterCore(), AbstractCardEnum.CONSTRUCTMOD);
		BaseMod.addRelicToCustomPool(new ClockworkPhoenix(), AbstractCardEnum.CONSTRUCTMOD);
		BaseMod.addRelicToCustomPool(new MegaBattery(), AbstractCardEnum.CONSTRUCTMOD);
		BaseMod.addRelicToCustomPool(new PurpleEmber(), AbstractCardEnum.CONSTRUCTMOD);
		BaseMod.addRelicToCustomPool(new LongRangeLanceRelic(), AbstractCardEnum.CONSTRUCTMOD);
		BaseMod.addRelicToCustomPool(new ExtraLongRangeLanceRelic(), AbstractCardEnum.CONSTRUCTMOD);

		shareableRelics.add(new FoamFinger());
		shareableRelics.add(new ClawGrip());
		shareableRelics.add(new WeddingRing());
		shareableRelics.add(new BoolHorns());
		shareableRelics.add(new RocketBooster());

		expansionRelics1.add(new IceCubes());

		addSharedRelics();
		addHeatRelics();

		challengeRelics.add(new Challenge1());
		challengeRelics.add(new Challenge2());
		challengeRelics.add(new Challenge3());
		challengeRelics.add(new Challenge4());
		challengeRelics.add(new Challenge5());
		for (AbstractRelic relic : challengeRelics){
			BaseMod.addRelicToCustomPool(relic, AbstractCardEnum.CONSTRUCTMOD);
		}
	}

	public static void adjustRelics(){
		// remove all shareable relics wherever they are, then re-add them.
		// assuming right now that there are no overheated expansion relics shared by other characters.
		for (AbstractRelic relic : shareableRelics){
			BaseMod.removeRelic(relic);
			BaseMod.removeRelicFromCustomPool(relic,AbstractCardEnum.CONSTRUCTMOD);
		}
		for (AbstractRelic relic : expansionRelics1){
			BaseMod.removeRelicFromCustomPool(relic,AbstractCardEnum.CONSTRUCTMOD);
		}
		addSharedRelics();
		addHeatRelics();
	}

	public static void addSharedRelics(){
		for (AbstractRelic relic : shareableRelics){
			if (contentSharing_relics) BaseMod.addRelic(relic, RelicType.SHARED);
			else BaseMod.addRelicToCustomPool(relic, AbstractCardEnum.CONSTRUCTMOD);
		}
	}

	public static void addHeatRelics(){
		for (AbstractRelic relic : expansionRelics1){
			if (overheatedExpansion) BaseMod.addRelicToCustomPool(relic, AbstractCardEnum.CONSTRUCTMOD);
		}
	}

	@Override
	public void receivePostDraw(AbstractCard card){
        if (AbstractDungeon.player != null){
            for (AbstractPower p:AbstractDungeon.player.powers){
                if (p instanceof AbstractOnDrawPower) ((AbstractOnDrawPower) p).onDrawCard(card);
            }
			for (AbstractMonster m:AbstractDungeon.getMonsters().monsters){
				for (AbstractPower p:m.powers){
					if (p instanceof AbstractOnDrawPower) ((AbstractOnDrawPower) p).onDrawCard(card);
				}
			}
        }
    }

    @Override
	public void receiveRender(SpriteBatch sb){
		if (AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT &&
				AbstractDungeon.player != null && !AbstractDungeon.isScreenUp){
			heatBar.render(sb);
		}
	}

    @Override
	public boolean receivePreMonsterTurn(AbstractMonster m){
    	resetCycles();
    	return true;
	}

	@Override
	public void receiveOnBattleStart(AbstractRoom room){
		resetCycles();
	}

	public static void incrementCycles(){
		cyclesThisTurn++;
		heatBar.onIncrementCycles();
	}

	public static void resetCycles(){
		cyclesThisTurn = 0;
		heatBar.onResetCycles();
	}

	public static boolean hasChallengeActive(int num){
		if (AbstractDungeon.player == null || num <= 0) return false;
		for (int i=num-1;i<challengeRelics.size();i++){
			if (AbstractDungeon.player.hasRelic(challengeRelics.get(i).relicId)){
				return true;
			}
		}
		return false;
	}

	public static String getAllChallengeDescriptionsUpTo(int num){
		UIStrings scStrings = CardCrawlGame.languagePack.getUIString("constructShortChallengeLevels");
		if (num > scStrings.TEXT.length) num = scStrings.TEXT.length;
		String msg = "";
		for (int i=1;i<=num;i++){
			if (i>1) msg += " NL ";
			msg += scStrings.TEXT[i];
			//msg += i + ": " + SHORT_CHALLENGE_STRINGS[i];
		}
		return msg;
	}

	public static boolean areCyclesFast(){
    	return cyclesThisTurn >= CYCLES_BEFORE_FASTMODE;
	}
	
	public static String makeID(String baseText) {
		return "construct:" + baseText;
	}

	public static String makeRelicImg(String baseText) {
		baseText = baseText.replace("construct:","");
		return "img/constructRelics/" + baseText + ".png";
	}

	public static String makeRelicOutlineImg(String baseText) {
		baseText = baseText.replace("construct:","");
		return "img/constructRelics/outline/" + baseText + ".png";
	}

	public static void setPowerImages(AbstractPower power, String powerID){
		powerID = powerID.replace("construct:","");
		power.region128 = new TextureAtlas.AtlasRegion(new Texture("img/constructPowers/128/" +(powerID)+".png"), 0, 0, 128, 128);
		power.region48 = new TextureAtlas.AtlasRegion(new Texture("img/constructPowers/48/" +(powerID)+".png"), 0, 0, 48, 48);
	}

	public static void setPowerImages(AbstractPower power){
		setPowerImages(power,power.ID);
	}
}