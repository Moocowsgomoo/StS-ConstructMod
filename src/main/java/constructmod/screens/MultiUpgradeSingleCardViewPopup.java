package constructmod.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MultiUpgradeSingleCardViewPopup extends SingleCardViewPopup {

    public int upgradeLevel = 0;
    Field fadeColorField;
    Field nextHbField;
    Field prevHbField;
    Method renderArrowsMethod;
    Method renderTipsMethod;
    Hitbox upgradePlusHb;

    public MultiUpgradeSingleCardViewPopup(){
        super();
        this.upgradePlusHb = new Hitbox(80.0f * Settings.scale, 40.0f * Settings.scale);
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

    //@Override
    //public void open(final AbstractCard card, final CardGroup group) {
     //   super.open(card,group);
    //    this.upgradePlusHb.move(Settings.WIDTH / 2.0f + 100f, 70.0f * Settings.scale);
    //}

    /*@Override
    public void update(){
        super.update();
        if (SingleCardViewPopup.isViewingUpgrade) {
            if (this.upgradePlusHb.hovered && InputHelper.justClickedLeft) {
                this.upgradePlusHb.clickStarted = true;
            }
            if (this.upgradePlusHb.clicked || CInputActionSet.proceed.isJustPressed()) {
                CInputActionSet.topPanel.unpress();
                this.upgradePlusHb.clicked = false;
                upgradeLevel++;
            }
        }
    }*/

    //@Override
    //public void render(final SpriteBatch sb) {
    //    super.render(sb); // render upgraded card via patch
        //renderUpgradeArrows(sb);
    //}

    public void renderUpgradeArrows(final SpriteBatch sb){
        if (SingleCardViewPopup.isViewingUpgrade) {
            sb.setColor(Color.WHITE);
            sb.draw(ImageMaster.CF_LEFT_ARROW, 870.0f * Settings.scale - 16.0f, 70.0f * Settings.scale - 16.0f, 16.0f, 16.0f, 32.0f, 32.0f, Settings.scale, Settings.scale, 90.0f, 0, 0, 32, 32, false, false);
        }
        this.upgradePlusHb.render(sb);
    }
}
