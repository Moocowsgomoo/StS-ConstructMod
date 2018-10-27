package constructmod.actions;

import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import constructmod.cards.AbstractCycleCard;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.DamageAction;

import java.util.*;

public class TumbleFollowupAction extends AbstractGameAction
{
	
	private final AbstractPlayer p = AbstractDungeon.player;
	public static int hits = 0;
	
    public TumbleFollowupAction(int dmg, AbstractMonster m) {
        this.duration = Settings.ACTION_DUR_FASTER;
        this.amount = dmg;
        this.target = m;
    }

    public static void onCycleCard(AbstractCard card){
        if (ConstructTumbleAction.drawnCards.contains(card)) {
            card.flash();
            hits++;
        }
    }
    
    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FASTER) {
            for (;hits>0;hits--) {
                AbstractDungeon.actionManager.addToTop(new DamageAction(this.target, new DamageInfo(p, this.amount), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            }
            ConstructTumbleAction.drawnCards.clear();
        }
        this.tickDuration();
    }
}
