package constructmod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import constructmod.ConstructMod;

public class Challenge2 extends CustomRelic {
    public static final String ID = ConstructMod.makeID("Challenge2");
    private static final String IMG = "img/constructRelics/Challenge2.png";

    public Challenge2() {
        super(ID, new Texture(IMG), RelicTier.SPECIAL, LandingSound.CLINK);
    }

    /*@Override
    public void onMasterDeckChange(){
        super.onMasterDeckChange();
        boolean upgradedDefend = false;
        boolean upgradedStrike = false;
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group){
            if (c instanceof HeatedDefend && c.upgraded){
                upgradedDefend = true;
                break;
            }
        }
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group){
            if (c instanceof HeatedStrike && c.upgraded){
                upgradedStrike = true;
                break;
            }
        }
        if (!upgradedDefend) {
            for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
                if (c instanceof HeatedDefend) {
                    c.upgrade();
                    break;
                }
            }
        }
        if (!upgradedStrike) {
            for (AbstractCard c : AbstractDungeon.player.masterDeck.group){
                if (c instanceof HeatedStrike){
                    c.upgrade();
                    break;
                }
            }
        }
    }*/

    @Override
    public String getUpdatedDescription() {
        return ConstructMod.getAllChallengeDescriptionsUpTo(2);
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new Challenge2();
    }
}