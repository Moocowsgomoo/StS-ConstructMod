package constructmod;

import java.util.ArrayList;

import basemod.interfaces.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.powers.AbstractPower;
import constructmod.powers.AbstractOnDrawPower;
import constructmod.powers.SynchronizePower;
import constructmod.variables.GatlingGunVariable;
import constructmod.variables.OverheatVariable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import basemod.BaseMod;
import basemod.ModLabel;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.ReflectionHacks;
import basemod.helpers.RelicType;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import constructmod.cards.*;
import constructmod.relics.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import constructmod.characters.TheConstruct;
import constructmod.patches.AbstractCardEnum;
import constructmod.patches.TheConstructEnum;

@SpireInitializer
public class ConstructMod implements PostInitializeSubscriber, EditCardsSubscriber, EditRelicsSubscriber,
	EditStringsSubscriber, EditCharactersSubscriber, EditKeywordsSubscriber, SetUnlocksSubscriber, PostDrawSubscriber {
	
	public static final Logger logger = LogManager.getLogger(ConstructMod.class.getName());
			
    private static final String MODNAME = "Construct Mod";
    private static final String AUTHOR = "Moocowsgomoo";
    private static final String DESCRIPTION = "New Character: The Construct!";
	
	private static final Color CONSTRUCT_MOD_COLOR = CardHelper.getColor(170.0f, 150.0f, 50.0f);

	public static Properties constructDefaults = new Properties();
	public static boolean phoenixStart = false;
	public static boolean contentSharing = true;
	public static boolean overheatedExpansion = false;
	public static int marriedCard1 = -1;
	public static int marriedCard2 = -1;
	public static Texture ringIconTexture;

	public static final ArrayList<AbstractCard> cores = new ArrayList<>();
    
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

		constructDefaults.setProperty("phoenixStart", "FALSE");
		constructDefaults.setProperty("contentSharing", "TRUE");
		constructDefaults.setProperty("overheatedBETA", "FALSE");
		loadConfigData();
    }
    
    public static void initialize() {
        ConstructMod mod = new ConstructMod();
    }
    
    public void receivePostInitialize() {
        // Mod badge
        Texture badgeTexture = new Texture(Gdx.files.internal("img/ConstructModBadge.png"));
        
        ModPanel settingsPanel = new ModPanel();
        
        ModLabel buttonLabel = new ModLabel("Reach EXP Level 1 as the Construct to unlock!", 350.0f, 700.0f, settingsPanel, (me)->{});
        ModLabel buttonLabel2 = new ModLabel("(Restart the game if it doesn't show up)", 350.0f, 650.0f, settingsPanel, (me)->{});
        
        ModLabeledToggleButton phoenixBtn = new ModLabeledToggleButton("Starting Relic: Clockwork Phoenix",
        		350.0f, 700.0f, Settings.CREAM_COLOR, FontHelper.charDescFont,
        		phoenixStart, settingsPanel, (label) -> {}, (button) -> {
        			phoenixStart = button.enabled;
        			saveData();
        			resetCharSelect();
        		});
		ModLabeledToggleButton contentSharingBtn = new ModLabeledToggleButton("Enable Construct relics for other characters (REQUIRES RESTART)",
				350.0f, 600.0f, Settings.CREAM_COLOR, FontHelper.charDescFont,
				contentSharing, settingsPanel, (label) -> {}, (button) -> {
					contentSharing = button.enabled;
					saveData();
				});
		ModLabeledToggleButton overheatedExpansionBtn = new ModLabeledToggleButton("Untested expansion cards - please tell me what you think! (REQUIRES RESTART)",
				350.0f, 550.0f, Settings.CREAM_COLOR, FontHelper.charDescFont,
				overheatedExpansion, settingsPanel, (label) -> {}, (button) -> {
			overheatedExpansion = button.enabled;
			saveData();
		});
        if (UnlockTracker.getUnlockLevel(TheConstructEnum.THE_CONSTRUCT_MOD) >= 1) settingsPanel.addUIElement(phoenixBtn);
        else {
        	if (phoenixStart) {
        		phoenixStart = false;
        		saveData();
        	}
        	settingsPanel.addUIElement(buttonLabel);
        	settingsPanel.addUIElement(buttonLabel2);
        }
        settingsPanel.addUIElement(contentSharingBtn);
        settingsPanel.addUIElement(overheatedExpansionBtn);
        
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);
        
        ringIconTexture = new Texture("img/512/card_ring_icon.png");
    }
    
    public static void saveData() {
    	try {
    		SpireConfig config = new SpireConfig("ConstructMod", "ConstructSaveData", constructDefaults);
    		config.setBool("phoenixStart", phoenixStart);
    		config.setBool("contentSharing", contentSharing);
    		config.setBool("overheatedBETA", overheatedExpansion);
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
			phoenixStart = config.getBool("phoenixStart");
			contentSharing = config.getBool("contentSharing");
			overheatedExpansion = config.getBool("overheatedBETA");
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
    	final String[] thorns = {"thorns"};
    	BaseMod.addKeyword(thorns, "Enemies take damage when they attack you.");
    	final String[] blur = {"blur"};
    	BaseMod.addKeyword(blur, "Your Block is carried over between turns.");
    	final String[] metallicize = {"metallicize"};
    	BaseMod.addKeyword(metallicize, "Gain Block at the end of each turn.");
    	final String[] pArmor = {"plated armor"}; // not working b/c it's two words?
    	BaseMod.addKeyword(pArmor, "Gain Block at the end of each turn. Reduced when you take unblocked damage.");
    	final String[] slimed = {"slimed"};
    	BaseMod.addKeyword(slimed, "Slimed is a status card that costs [R] to exhaust.");
    	final String[] cores = {"cores"};
    	BaseMod.addKeyword(cores, "Cores are cards that cycle and apply a small bonus effect.");
    	final String[] eggs = {"egg","eggs"};
    	BaseMod.addKeyword(eggs, "Eggs are relics that automatically upgrade cards when you acquire them.");
    	final String[] megaUpgrade = {"mega-upgrade","mega-upgraded"};
    	BaseMod.addKeyword(megaUpgrade, "A second upgrade that makes a card even more powerful.");
		final String[] overheat = {"overheat","[#ff9900]overheat","[#ff9900]overheats","[#ff9900]overheated", "[#ff9900]overheat:","overheats","overheated"};
		BaseMod.addKeyword(overheat, "Counts down whenever ANY card #ycycles. When it hits #b0, transform this card into a #yBurn for this combat.");
    }
	
	public void receiveEditCharacters() {
		logger.info("begin editing characters");
		
		logger.info("add " + TheConstructEnum.THE_CONSTRUCT_MOD.toString());
		BaseMod.addCharacter(new TheConstruct("The Construct", TheConstructEnum.THE_CONSTRUCT_MOD), AbstractCardEnum.CONSTRUCTMOD,
				"img/charSelect/constructButton.png", "img/charSelect/constructPortrait.jpg",
				TheConstructEnum.THE_CONSTRUCT_MOD);
		
		logger.info("done editing characters");
	}
	
	@Override
	public void receiveSetUnlocks() {
		/*UnlockTracker.addCard("Overclock");
		UnlockTracker.addCard("Overcharge");
		UnlockTracker.addCard("Meltdown");
		//UnlockTracker.addCardUnlockToList(map, key, unlock);
		BaseMod.addUnlockBundle(new CustomUnlockBundle("Overclock","Overcharge","Meltdown"), TheConstructEnum.THE_CONSTRUCT_MOD, 0);
		
		UnlockTracker.addRelic("MasterCore");
		UnlockTracker.addRelic("FoamFinger");
		UnlockTracker.addRelic("ClawGrip");
		BaseMod.addUnlockBundle(new CustomUnlockBundle("MasterCore","FoamFinger","ClawGrip"), TheConstructEnum.THE_CONSTRUCT_MOD, 1);*/
		
	}
	
	public void receiveEditStrings() {
		
		// RelicStrings
        String relicStrings = Gdx.files.internal("localization/ConstructMod-RelicStrings.json").readString(
        		String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);
		// CardStrings
        String cardStrings = Gdx.files.internal("localization/ConstructMod-CardStrings.json").readString(
        		String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(CardStrings.class, cardStrings);
	}
	
	public void receiveEditCards() {

    	BaseMod.addDynamicVariable(new OverheatVariable());
    	BaseMod.addDynamicVariable(new GatlingGunVariable());

		// Add cards
		logger.info("Adding Construct Cards");
		// BASIC
		addCard(new Strike_Gold());
		addCard(new Defend_Gold());
		addCard(new AttackMode());
		addCard(new DefenseMode());
		
		// COMMON
		//	Attacks (11/11)+2
		addCard(new Boost()); 		// atk, block
		addCard(new ScrapCannon());	// atk, exhaust
		addCard(new SweepLaser());	// atk
		addCard(new Versatility());	// atk, block (intent)
		addCard(new SuppressiveFire());// atk, defense -> UNCOMMON, replace with draw?
		addCard(new Backfire());	// atk
		addCard(new HeavyBolt());	// atk
		addCard(new ShiftStrike()); // atk, str/dex
		addCard(new FierceBash());	// atk, (intent)
		addCard(new FocusedBeam()); // atk
		addCard(new Accumulate()); 	// atk, copy
		
		//	Skills (7/7)+2
		addCard(new MetalShell());	// block
		addCard(new Anticipate());	// block
		addCard(new ShiftGuard());	// block, str/dex
		addCard(new Forcefield());  // block, retain
		
		addCard(new ModeShift()); 	// modes, draw
		addCard(new Analyze()); 	// draw/energy next turn
		addCard(new VentSteam());	// debuff, exhaust
		
		//	Powers (1/1)
		addCard(new Autoturret());	// atk from cycle
		
		// UNCOMMON
		// 	Attacks(11/11) +2
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
		
		//	Skills (16/17)+1
		addCard(new OneWayMirror());// block
		addCard(new Reinforce());	// block
		addCard(new Disrupt());		// block
		addCard(new Impenetrable());// block
		addCard(new Hazardproof()); // block, buff
		
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
		//addCard(new BubbleShield());// block-based
		
		// 	Powers (8/7)+1
		addCard(new Synchronize());	// copy-based
		addCard(new Enhance());		// upgrade
		addCard(new Overclock()); 	// burn, draw
		addCard(new Overcharge()); 	// burn, energy
		addCard(new ShieldGenerator());//defensive --> RARE? (synergy with Orb Assault as it currently is, Shield Burst; but can't be copied!)
		addCard(new ReactiveShield());// block-based
		addCard(new Zapper()); // defensive, stat-based
		addCard(new PointDefense()); // block
		//-addCard(new Overpower());	// burn, stats
		//-addCard(new Disruptor());  	// block
		
		// RARE
		//	Attacks (6/5)+1
		addCard(new HyperBeam());	// atk
		addCard(new GoldenBullet());// atk
		addCard(new ShieldBurst());	// atk from block --> UNCOMMON?
		addCard(new GatlingGun());	// atk, X-cost
		addCard(new HammerDown());	// atk, modes
		addCard(new Antimatter());	// atk
		//	Skills (6/7)+2
		addCard(new MassProduction());//copy
		addCard(new HastyRepair()); // heal
		addCard(new ClockworkEgg());// egg
		addCard(new BatteryCore());	// energy
		addCard(new MemoryTap());	// cards from other classes
		addCard(new Reserves()); 	// cycle, draw/energy at low HP
		//	Powers (5/5)+1
		addCard(new SiegeForm());	// buff, atk-based
		addCard(new SpinDrive()); 	// cards
		addCard(new Bunker()); 		// block --> RARE? (synergy with retain, which are mostly c/u), can't be copied!
		addCard(new Meltdown()); 	// burn, damage
		addCard(new PanicFire()); 	// atk & exhaust from cycle
		//-addCard(new CoreStorm()); 	// atk from cycle --> Bring back old Panic Fire, now that orbs copy again?

		// MISC.
		//addCard(new CoreShard()); // status, cycle

		if (overheatedExpansion){
			addCard(new PhosphorStorm());//atk, overheat
			addCard(new Missile());		// atk, cycle
			addCard(new Coolant());		// block, cooling
			addCard(new CreateCores());	// cores, overheat
			addCard(new OilSpill());	// overheat, overheat synergy
			addCard(new Rollout());		// atk, cycle synergy, overheat
			addCard(new Flamethrower());// atk, cycle?, burn synergy
			addCard(new FlammableFog());// overheat, cooling synergy
			addCard(new Failsafe());	 // anti-status
			addCard(new BlazingSpeed());// atk, overheat
			addCard(new NuclearCore());	// cycle, overheat
			addCard(new Implosion());	// burn synergy, play
			addCard(new Agitation());	// overheat synergy
		}

		cores.add(new FlameCore());
		cores.add(new LaserCore());
		cores.add(new ScopeCore());
		cores.add(new ForceCore());
		cores.add(new GuardCore());
		cores.add(new BatteryCore());
		cores.add(new NuclearCore());
	}

	public void addCard(AbstractCard card){
    	BaseMod.addCard(card);
    	UnlockTracker.unlockCard(card.cardID);
	}

	public static AbstractCard getRandomCore(){
		return cores.get(AbstractDungeon.cardRandomRng.random(0,cores.size()-1)).makeCopy();
	}
	
	public void receiveEditRelics() {
		logger.info("Adding Construct Relics");
		if (contentSharing) {
			BaseMod.addRelic(new FoamFinger(), RelicType.SHARED);
			BaseMod.addRelic(new ClawGrip(), RelicType.SHARED);
			BaseMod.addRelic(new RocketBooster(), RelicType.SHARED);
			BaseMod.addRelic(new WeddingRing(), RelicType.SHARED);
		}
		else{
			BaseMod.addRelicToCustomPool(new FoamFinger(), AbstractCardEnum.CONSTRUCTMOD);
			BaseMod.addRelicToCustomPool(new ClawGrip(), AbstractCardEnum.CONSTRUCTMOD);
			BaseMod.addRelicToCustomPool(new RocketBooster(), AbstractCardEnum.CONSTRUCTMOD);
			BaseMod.addRelicToCustomPool(new WeddingRing(), AbstractCardEnum.CONSTRUCTMOD);
		}
		BaseMod.addRelicToCustomPool(new Cogwheel(), AbstractCardEnum.CONSTRUCTMOD);
		BaseMod.addRelicToCustomPool(new MasterCore(), AbstractCardEnum.CONSTRUCTMOD);
		BaseMod.addRelicToCustomPool(new ClockworkPhoenix(), AbstractCardEnum.CONSTRUCTMOD);
		BaseMod.addRelicToCustomPool(new MegaBattery(), AbstractCardEnum.CONSTRUCTMOD);
		BaseMod.addRelicToCustomPool(new PurpleEmber(), AbstractCardEnum.CONSTRUCTMOD);
	}

	@Override
	public void receivePostDraw(AbstractCard card){
        if (AbstractDungeon.player != null){
            for (AbstractPower p:AbstractDungeon.player.powers){
                if (p instanceof AbstractOnDrawPower) ((AbstractOnDrawPower) p).onDrawCard(card);
            }
        }
    }
	
	public static String makeID(String baseText) {
		return "construct:" + baseText;
	}
}