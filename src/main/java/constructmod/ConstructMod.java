package constructmod;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import basemod.BaseMod;
import basemod.ModLabel;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.ReflectionHacks;
import basemod.abstracts.CustomUnlockBundle;
import basemod.helpers.RelicType;
import basemod.interfaces.EditCharactersSubscriber;
import basemod.interfaces.EditKeywordsSubscriber;
import basemod.interfaces.EditCardsSubscriber;
import basemod.interfaces.EditRelicsSubscriber;
import basemod.interfaces.EditStringsSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import basemod.interfaces.SetUnlocksSubscriber;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import constructmod.cards.*;
import constructmod.relics.*;
import java.nio.charset.StandardCharsets;

import constructmod.characters.TheConstruct;
import constructmod.patches.AbstractCardEnum;
import constructmod.patches.TheConstructEnum;

@SpireInitializer
public class ConstructMod implements PostInitializeSubscriber, EditCardsSubscriber, EditRelicsSubscriber,
	EditStringsSubscriber, EditCharactersSubscriber, EditKeywordsSubscriber, SetUnlocksSubscriber {
	
	public static final Logger logger = LogManager.getLogger(ConstructMod.class.getName());
			
    private static final String MODNAME = "Construct Mod";
    private static final String AUTHOR = "Moocowsgomoo";
    private static final String DESCRIPTION = "New Character: The Construct!";
	
	private static final Color CONSTRUCT_MOD_COLOR = CardHelper.getColor(170.0f, 150.0f, 50.0f);
	
	public static boolean phoenixStart = false;
    
    public ConstructMod() {
        BaseMod.subscribe(this);
		
		logger.info("creating the color " + AbstractCardEnum.CONSTRUCTMOD.toString());
        BaseMod.addColor(AbstractCardEnum.CONSTRUCTMOD.toString(),
        		CONSTRUCT_MOD_COLOR, CONSTRUCT_MOD_COLOR, CONSTRUCT_MOD_COLOR, CONSTRUCT_MOD_COLOR, CONSTRUCT_MOD_COLOR, CONSTRUCT_MOD_COLOR, CONSTRUCT_MOD_COLOR,
        		"img/512/bg_attack_gold.png", "img/512/bg_skill_gold.png",
        		"img/512/bg_power_gold.png", "img/512/card_gold_orb.png",
				"img/1024/bg_attack_gold.png", "img/1024/bg_skill_gold.png",
        		"img/1024/bg_power_gold.png", "img/1024/card_gold_orb.png");
				
		Settings.isDailyRun = false;
        Settings.isTrial = false;
        Settings.isDemo = false;
    }
    
    public static void initialize() {
        ConstructMod mod = new ConstructMod();
    }
    
    public void receivePostInitialize() {
        // Mod badge
        Texture badgeTexture = new Texture(Gdx.files.internal("img/ConstructModBadge.png"));
        
        phoenixStart = BaseMod.maybeGetBoolean("Phoenix");
        
        ModPanel settingsPanel = new ModPanel();
        
        ModLabel buttonLabel = new ModLabel("Reach EXP Level 1 as the Construct to unlock!", 350.0f, 600.0f, settingsPanel, (me)->{});
        
        ModLabeledToggleButton phoenixBtn = new ModLabeledToggleButton("Starting Relic: Clockwork Phoenix",
        		350.0f, 600.0f, Settings.CREAM_COLOR, FontHelper.charDescFont,
        		phoenixStart, settingsPanel, (label) -> {}, (button) -> {
        			phoenixStart = button.enabled;
        			BaseMod.maybeSetBoolean("Phoenix", phoenixStart);
        			resetCharSelect();
        		});
        if (UnlockTracker.getUnlockLevel(TheConstructEnum.THE_CONSTRUCT_MOD) >= 1) settingsPanel.addUIElement(phoenixBtn);
        else {
        	if (phoenixStart) {
        		phoenixStart = false;
        		BaseMod.maybeSetBoolean("Phoenix", false);
        	}
        	settingsPanel.addUIElement(buttonLabel);
        }
        
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);
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
    	final String[] vol = {"broken"};
    	BaseMod.addKeyword(vol, "Broken cores are unplayable status cards. When one cycles, you take 1 damage.");
    	final String[] cores = {"cores"};
    	BaseMod.addKeyword(cores, "Cores are cards that cycle and apply a small bonus effect.");
    	final String[] eggs = {"egg","eggs"};
    	BaseMod.addKeyword(eggs, "Eggs are relics that automatically upgrade cards when you acquire them.");
    	final String[] megaUpgrade = {"mega-upgrade","mega-upgraded"};
    	BaseMod.addKeyword(megaUpgrade, "A second upgrade that makes a card even more powerful.");
    }
	
	public void receiveEditCharacters() {
		logger.info("begin editing characters");
		
		logger.info("add " + TheConstructEnum.THE_CONSTRUCT_MOD.toString());
		BaseMod.addCharacter(TheConstruct.class, "The Construct", "Construct class string",
				AbstractCardEnum.CONSTRUCTMOD.toString(), "The Construct",
				"img/charSelect/constructButton.png", "img/charSelect/constructPortrait.jpg",
				TheConstructEnum.THE_CONSTRUCT_MOD.toString());
		
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
		// Add cards
		logger.info("Adding Construct Cards");
		// BASIC
		BaseMod.addCard(new Strike_Gold());
		BaseMod.addCard(new Defend_Gold());
		BaseMod.addCard(new AttackMode());
		BaseMod.addCard(new DefenseMode());
		
		// COMMON
		//	Attacks (12/11)
		BaseMod.addCard(new Boost()); 		// atk, block
		BaseMod.addCard(new ScrapCannon());	// atk, exhaust
		BaseMod.addCard(new SweepLaser());	// atk
		BaseMod.addCard(new Versatility());	// atk, block (intent)
		BaseMod.addCard(new SuppressiveFire());// atk, defense
		BaseMod.addCard(new Backfire());	// atk
		BaseMod.addCard(new HeavyBolt());	// atk
		BaseMod.addCard(new ShiftStrike()); // atk, str/dex
		BaseMod.addCard(new FierceBash());	// atk, (intent)
		BaseMod.addCard(new FocusedBeam()); // atk
		BaseMod.addCard(new Accumulate()); 	// atk, copy
		
		//	Skills (7/7)
		BaseMod.addCard(new MetalShell());	// block
		BaseMod.addCard(new Anticipate());	// block
		BaseMod.addCard(new ShiftGuard());	// block, str/dex
		BaseMod.addCard(new Forcefield());  // block, retain
		
		BaseMod.addCard(new ModeShift()); 	// modes, draw
		BaseMod.addCard(new Analyze()); 	// draw/energy next turn
		BaseMod.addCard(new VentSteam());	// debuff, exhaust
		
		//	Powers (1/1)
		BaseMod.addCard(new Autoturret());	// atk from cycle
		
		// UNCOMMON
		// 	Attacks(10/11)
		BaseMod.addCard(new ChargeShot());	// atk (retain)
		BaseMod.addCard(new CripplingShot());//atk, debuff
		BaseMod.addCard(new Electrocute()); // atk, debuff
		BaseMod.addCard(new FlakBarrage());	// atk (cycle)
		BaseMod.addCard(new Chainstrike()); // atk, cost reduction
		BaseMod.addCard(new CriticalHit()); // atk (cycle)
		BaseMod.addCard(new UnbalancingBlast()); // atk (card manip)
		BaseMod.addCard(new Tumble()); 		// atk, draw
		BaseMod.addCard(new OmegaCannon()); // atk, str-based
		BaseMod.addCard(new QuickAttack()); // atk, dex
		
		//	Skills (17/17)
		BaseMod.addCard(new OneWayMirror());// block
		BaseMod.addCard(new Reinforce());	// block
		BaseMod.addCard(new Disrupt());		// block
		BaseMod.addCard(new Impenetrable());// block
		//BaseMod.addCard(new ReactiveShield()); // block (cycle)
		//Trip Mine
		//-BaseMod.addCard(new Lockdown());	// block, draw
		//-Save State (use power since retain isn't cleared on discard?)
		
		BaseMod.addCard(new Disorient());	// debuff
		BaseMod.addCard(new LaserCore());	// cycle, atk
		BaseMod.addCard(new FlameCore());	// cycle, atk
		BaseMod.addCard(new ScopeCore());	// cycle, debuff
		BaseMod.addCard(new ForceCore()); 	// cycle, buff
		BaseMod.addCard(new GuardCore());	// cycle, block
		BaseMod.addCard(new Backup());		// copy
		BaseMod.addCard(new BatteryAcid()); // energy
		BaseMod.addCard(new Reboot());	 	// exhaust
		BaseMod.addCard(new Isolate()); 	// cycle, buff
		BaseMod.addCard(new Stasis()); 		// copy, mega-upgrade
		
		// 	Powers (7/7)
		BaseMod.addCard(new Synchronize());	// copy-based
		BaseMod.addCard(new Enhance());		// upgrade
		BaseMod.addCard(new ElectricArmor());//block-based
		BaseMod.addCard(new BubbleShield());// block-based
		BaseMod.addCard(new Overclock()); 	// burn, draw
		BaseMod.addCard(new Overcharge()); 	// burn, energy
		BaseMod.addCard(new ShieldGenerator());//defensive --> RARE? (synergy with Orb Assault as it currently is, Shield Burst; but can't be copied!)
		//-BaseMod.addCard(new Overpower());	// burn, stats
		//-BaseMod.addCard(new Disruptor());  	// block
		
		// RARE
		//	Attacks (6/5)
		BaseMod.addCard(new HyperBeam());	// atk
		BaseMod.addCard(new GoldenBullet());// atk
		BaseMod.addCard(new ShieldBurst());	// atk from block
		BaseMod.addCard(new GatlingGun());	// atk, X-cost
		BaseMod.addCard(new HammerDown());	// atk, modes
		BaseMod.addCard(new Antimatter());	// atk
		//	Skills (7/7)
		BaseMod.addCard(new MassProduction());//copy
		BaseMod.addCard(new HastyRepair()); // heal
		BaseMod.addCard(new ClockworkEgg());// egg
		BaseMod.addCard(new Hazardproof()); // block, buff
		BaseMod.addCard(new BatteryCore());	// energy
		BaseMod.addCard(new MemoryTap());	// cards from other classes
		BaseMod.addCard(new Reserves()); 	// cycle, draw/energy at low HP
		//	Powers (5/5)
		BaseMod.addCard(new SiegeForm());	// buff, atk-based
		BaseMod.addCard(new SpinDrive()); 	// cards
		BaseMod.addCard(new Bunker()); 		// block --> RARE? (synergy with retain, which are mostly c/u), can't be copied!
		BaseMod.addCard(new Meltdown()); 	// burn, damage
		BaseMod.addCard(new PanicFire()); 	// atk & exhaust from cycle
		//-BaseMod.addCard(new CoreStorm()); 	// atk from cycle --> Bring back old Panic Fire, now that orbs copy again?
		
		// MISC.
		//BaseMod.addCard(new CoreShard()); // status, cycle
	}
	
	public void receiveEditRelics() {
		logger.info("Adding Construct Relics");
		BaseMod.addRelic(new FoamFinger(), RelicType.SHARED);
		BaseMod.addRelic(new ClawGrip(), RelicType.SHARED);
		BaseMod.addRelic(new RocketBooster(), RelicType.SHARED);
		BaseMod.addRelicToCustomPool(new Cogwheel(), AbstractCardEnum.CONSTRUCTMOD.toString());
		BaseMod.addRelicToCustomPool(new MasterCore(), AbstractCardEnum.CONSTRUCTMOD.toString());
		BaseMod.addRelicToCustomPool(new ClockworkPhoenix(), AbstractCardEnum.CONSTRUCTMOD.toString());
		BaseMod.addRelicToCustomPool(new MegaBattery(), AbstractCardEnum.CONSTRUCTMOD.toString());
		BaseMod.addRelicToCustomPool(new PurpleEmber(), AbstractCardEnum.CONSTRUCTMOD.toString());
	}
}