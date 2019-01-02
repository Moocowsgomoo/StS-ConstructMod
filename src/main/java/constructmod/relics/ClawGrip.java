package constructmod.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import constructmod.ConstructMod;
import constructmod.actions.ClawGripAction;

public class ClawGrip extends CustomRelic {
    public static final String ID = ConstructMod.makeID("ClawGrip");
    public static final String IMG = ConstructMod.makeRelicImg(ID);
    public static final String OUTLINE_IMG = ConstructMod.makeRelicOutlineImg(ID);
    
    public AbstractCard card;
	
    public ClawGrip() {
        super(ID, new Texture(IMG),new Texture(OUTLINE_IMG), RelicTier.RARE, LandingSound.FLAT);
    }
    
    @Override
    public void atTurnStart() {
    	if (card == null) return;
    	
        card.setCostForTurn(card.costForTurn-1);
        card.flash();
    }
 
    @Override
    public void onPlayerEndTurn() {
        AbstractDungeon.actionManager.addToTop(new ClawGripAction(this));
    }
    
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new ClawGrip();
    }
}