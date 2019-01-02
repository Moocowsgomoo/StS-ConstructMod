package constructmod.patches;

import basemod.BaseMod;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.scene.LogoFlameEffect;
import constructmod.ConstructMod;
import constructmod.cards.AbstractConstructCard;
import constructmod.relics.ClockworkPhoenix;
import constructmod.relics.Cogwheel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;

public class PhoenixBtnPatch {
	public static Field charInfoField;
	public static final Hitbox relicHitbox = new Hitbox(40.0f * Settings.scale * (0.01f + (1.0f - 0.019f)), 40.0f * Settings.scale);
	public static final Hitbox expansionHitbox = new Hitbox(40.0f * Settings.scale * (0.01f + (1.0f - 0.019f)), 40.0f * Settings.scale);
	public static AbstractRelic r;
	public static float flameTimer;
	public static final ArrayList<LogoFlameEffect> flame = new ArrayList<>();

	@SpirePatch(clz = CharacterOption.class, method = "renderRelics")
	public static class RenderBtn{
		public static void Postfix(CharacterOption obj, SpriteBatch sb) {
			if (obj.name == "The Construct" && obj.selected) {
				if (charInfoField == null) {
					try {
						charInfoField = CharacterOption.class.getDeclaredField("charInfo");
						charInfoField.setAccessible(true);
						if (((CharSelectInfo)charInfoField.get(obj)).relics.get(0) == Cogwheel.ID){
							r = RelicLibrary.getRelic(ClockworkPhoenix.ID);
						}
						else{
							r = RelicLibrary.getRelic(Cogwheel.ID);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				r.updateDescription(TheConstructEnum.THE_CONSTRUCT_MOD);
				relicHitbox.move(190.0f * Settings.scale, Settings.HEIGHT / 2.0f - 190.0f * Settings.scale);
				expansionHitbox.move(190.0f * Settings.scale, Settings.HEIGHT / 2.0f - 140.0f * Settings.scale);
				relicHitbox.render(sb);
				expansionHitbox.render(sb);
				if (UnlockTracker.getUnlockLevel(TheConstructEnum.THE_CONSTRUCT_MOD) >= 4) {
					sb.draw(ImageMaster.CHECKBOX, relicHitbox.cX - 32.0f, relicHitbox.cY - 32.0f, 32.0f, 32.0f, 64.0f, 64.0f, Settings.scale * (0.01f + (1.0f - 0.019f)), Settings.scale * (0.01f + (1.0f - 0.019f)), 0.0f, 0, 0, 64, 64, false, false);
					sb.setColor(new Color(0.0f, 0.0f, 0.0f, 0.25f));
					sb.draw(r.outlineImg, relicHitbox.cX - 64.0f, relicHitbox.cY - 64.0f, 64.0f, 64.0f, 128.0f, 128.0f, Settings.scale * 0.5f * (0.01f + (1.0f - 0.019f)), Settings.scale * 0.5f * (0.01f + (1.0f - 0.019f)), 0.0f, 0, 0, 128, 128, false, false);
					sb.setColor(Color.WHITE);
					sb.draw(r.img, relicHitbox.cX - 64.0f, relicHitbox.cY - 64.0f, 64.0f, 64.0f, 128.0f, 128.0f, Settings.scale * 0.5f * (0.01f + (1.0f - 0.019f)), Settings.scale * 0.5f * (0.01f + (1.0f - 0.019f)), 0.0f, 0, 0, 128, 128, false, false);
					FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, "Change starting relic", relicHitbox.cX + 25f * Settings.scale, relicHitbox.cY, Settings.BLUE_TEXT_COLOR);

					if (UnlockTracker.getUnlockLevel(TheConstructEnum.THE_CONSTRUCT_MOD) >= 5) {
						sb.draw(ImageMaster.CHECKBOX, expansionHitbox.cX - 32.0f, expansionHitbox.cY - 32.0f, 32.0f, 32.0f, 64.0f, 64.0f, Settings.scale * (0.01f + (1.0f - 0.019f)), Settings.scale * (0.01f + (1.0f - 0.019f)), 0.0f, 0, 0, 64, 64, false, false);
						sb.setColor(Color.ORANGE);
						if (ConstructMod.overheatedExpansion)
							sb.draw(ImageMaster.TICK, expansionHitbox.cX - 32.0f, expansionHitbox.cY - 32.0f, 32.0f, 32.0f, 64.0f, 64.0f, Settings.scale * (0.01f + (1.0f - 0.019f)), Settings.scale * (0.01f + (1.0f - 0.019f)), 0.0f, 0, 0, 64, 64, false, false);
						FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, "Enable Overheated expansion pack", expansionHitbox.cX + 25f * Settings.scale, expansionHitbox.cY, Settings.BLUE_TEXT_COLOR);
					}
				}
			}
		}
	}

	@SpirePatch(clz = CharacterOption.class, method = "updateHitbox")
	public static class UpdateHitbox{
		public static void Postfix(CharacterOption obj) {
			if (obj.name == "The Construct" && obj.selected) {
				if (UnlockTracker.getUnlockLevel(TheConstructEnum.THE_CONSTRUCT_MOD) >= 4) {
					relicHitbox.update();
					if (relicHitbox.hovered) {
						if (InputHelper.mX < 1400.0f * Settings.scale) {
							TipHelper.queuePowerTips(InputHelper.mX + 60.0f * Settings.scale, InputHelper.mY - 50.0f * Settings.scale, r.tips);
						} else {
							TipHelper.queuePowerTips(InputHelper.mX - 350.0f * Settings.scale, InputHelper.mY - 50.0f * Settings.scale, r.tips);
						}

						if (InputHelper.justClickedLeft) {
							CardCrawlGame.sound.playA("UI_CLICK_1", -0.4f);
							relicHitbox.clickStarted = true;
						}
						if (relicHitbox.clicked) {
							try {
								CharSelectInfo charInfo = (CharSelectInfo) charInfoField.get(obj);

								String tmp = r.relicId;
								r = RelicLibrary.getRelic(charInfo.relics.get(0));
								ConstructMod.logger.debug(r.name);
								charInfo.relics.remove(0);
								charInfo.relics.add(tmp);
								relicHitbox.clicked = false;
								ConstructMod.phoenixStart = (charInfo.relics.get(0) == ClockworkPhoenix.ID);
								ConstructMod.saveData();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}

				if (UnlockTracker.getUnlockLevel(TheConstructEnum.THE_CONSTRUCT_MOD) >= 5) {
					expansionHitbox.update();
					if (expansionHitbox.hovered) {
						if (InputHelper.justClickedLeft) {
							CardCrawlGame.sound.playA("UI_CLICK_1", -0.4f);
							expansionHitbox.clickStarted = true;
						}
						if (expansionHitbox.clicked) {
							try {
								expansionHitbox.clicked = false;
								ConstructMod.overheatedExpansion = !ConstructMod.overheatedExpansion;
								ConstructMod.saveData();
								ConstructMod.adjustCards();

								if (ConstructMod.overheatedExpansion) {
									CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT, false);
									CardCrawlGame.sound.playA("ATTACK_FIRE", 0.2f);
								}

							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
	}
}