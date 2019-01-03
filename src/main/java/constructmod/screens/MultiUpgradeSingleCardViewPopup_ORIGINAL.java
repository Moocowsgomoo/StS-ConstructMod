package constructmod.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MultiUpgradeSingleCardViewPopup_ORIGINAL extends SingleCardViewPopup {

    public AbstractCard originalCard;
    public AbstractCard copy;
    public int upgradeLevel = 0;
    Field fadeColorField;
    Field nextHbField;
    Field prevHbField;
    Method renderArrowsMethod;
    Method renderTipsMethod;

    public MultiUpgradeSingleCardViewPopup_ORIGINAL(){
        super();
        try{
            fadeColorField = SingleCardViewPopup.class.getDeclaredField("fadeColor");
            fadeColorField.setAccessible(true);
            prevHbField = SingleCardViewPopup.class.getDeclaredField("prevHb");
            prevHbField.setAccessible(true);
            nextHbField = SingleCardViewPopup.class.getDeclaredField("nextHb");
            nextHbField.setAccessible(true);
            renderArrowsMethod = SingleCardViewPopup.class.getDeclaredMethod("renderArrows", SpriteBatch.class);
            renderArrowsMethod.setAccessible(true);
            renderTipsMethod = SingleCardViewPopup.class.getDeclaredMethod("renderTips", SpriteBatch.class);
            renderTipsMethod.setAccessible(true);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void open(final AbstractCard card, final CardGroup group) {
        this.originalCard = card;
        makeNewCopy();
        super.open(card,group);
    }

    public void makeNewCopy(){
        this.copy = originalCard.makeStatEquivalentCopy();
        copy.current_x = copy.target_x = Settings.WIDTH*0.5f;
        copy.current_y = copy.target_y = Settings.HEIGHT*0.5f;
        copy.drawScale = copy.targetDrawScale = 2f;
    }

    @Override
    public void render(final SpriteBatch sb) {
        if (copy.timesUpgraded < upgradeLevel && copy.canUpgrade()) {
            copy.upgrade();
            this.copy.displayUpgrades();
        }
        else if (copy.timesUpgraded > upgradeLevel){
            makeNewCopy();
            while (copy.timesUpgraded < upgradeLevel && copy.canUpgrade()){
                copy.upgrade();
            }
            this.copy.displayUpgrades();
        }
        try {
            sb.setColor((Color)(fadeColorField.get(this)));

            sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0f, 0.0f, Settings.WIDTH, Settings.HEIGHT);
            sb.setColor(Color.WHITE);
            this.renderCardBack(sb);
            copy.render(sb);
            renderArrowsMethod.invoke(this,sb);
            renderTipsMethod.invoke(this,sb);
            //this.cardHb.render(sb);
            Hitbox hb = (Hitbox)nextHbField.get(this);
            if (hb != null) hb.render(sb);
            hb = (Hitbox)prevHbField.get(this);
            if (hb != null) hb.render(sb);
            /*if (this.allowUpgradePreview()) {
                this.renderUpgradeViewToggle(sb);
                if (Settings.isControllerMode) {
                    sb.draw(CInputActionSet.proceed.getKeyImg(), Settings.WIDTH / 2.0f - 48.0f - 155.0f * Settings.scale, -48.0f + 67.0f * Settings.scale, 48.0f, 48.0f, 64.0f, 64.0f, Settings.scale, Settings.scale, 0.0f, 0, 0, 64, 64, false, false);
                }
            }*/
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
