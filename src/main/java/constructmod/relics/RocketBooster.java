package constructmod.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

public class RocketBooster extends CustomRelic {
    public static final String ID = "RocketBooster";
    private static final String IMG = "img/relics/RocketBooster.png";
	
    public RocketBooster() {
        super(ID, new Texture(IMG), RelicTier.RARE, LandingSound.MAGICAL);
    }
    
    @Override
    public void onEnterRoom(final AbstractRoom room) {
        if (room instanceof MonsterRoomElite) {
            this.pulse = true;
            this.beginPulse();
        }
        else {
            this.pulse = false;
        }
    }
    
    @Override
    public void onVictory() {
        if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite) {
            this.flash();
            this.pulse = false;
            
            final ArrayList<AbstractCard> upgradableCards = new ArrayList<AbstractCard>();
            for (final AbstractCard c : AbstractDungeon.player.masterDeck.group) {
                if (c.canUpgrade()) {
                    upgradableCards.add(c);
                }
            }
            Collections.shuffle(upgradableCards, new Random(AbstractDungeon.miscRng.randomLong()));
            if (!upgradableCards.isEmpty()) {
            	AbstractCard c = upgradableCards.get(0);
            	c.upgrade();
                AbstractDungeon.player.bottledCardUpgradeCheck(c);
                AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy()));
                AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f));
            }
        }
    }
    
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new RocketBooster();
    }
}