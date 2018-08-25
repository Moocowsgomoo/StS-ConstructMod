package constructmod.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ArtifactPower;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import constructmod.ConstructMod;
import constructmod.actions.ClawGripAction;
import constructmod.cards.AbstractConstructCard;
import constructmod.patches.AbstractCardEnum;

public class MegaBattery extends CustomRelic {
    public static final String ID = ConstructMod.makeID("MegaBattery");
    private static final String IMG = "img/constructRelics/MegaBattery.png";
	
    public MegaBattery() {
        super(ID, new Texture(IMG), RelicTier.COMMON, LandingSound.SOLID);
    }
    
    @Override
    public void onEquip() {
        final ArrayList<AbstractCard> upgradableCards = new ArrayList<AbstractCard>();
        for (final AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c instanceof AbstractConstructCard && ((AbstractConstructCard)c).canUpgrade(true)) {
                upgradableCards.add(c);
            }
        }
        Collections.shuffle(upgradableCards, new Random(AbstractDungeon.miscRng.randomLong()));
        if (!upgradableCards.isEmpty()) {
        	AbstractCard c = upgradableCards.get(0);
        	
        	for (int i=0;i<5;i++) {
        		((AbstractConstructCard)c).upgrade(true);
        	}
            AbstractDungeon.player.bottledCardUpgradeCheck(c);
            AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy()));
            AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f));
        }
    }
    
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new MegaBattery();
    }
}