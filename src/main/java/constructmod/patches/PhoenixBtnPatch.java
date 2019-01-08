package constructmod.patches;

import basemod.BaseMod;
import basemod.ReflectionHacks;
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
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.scene.LogoFlameEffect;
import constructmod.ConstructMod;
import constructmod.cards.AbstractConstructCard;
import constructmod.characters.TheConstruct;
import constructmod.relics.ClockworkPhoenix;
import constructmod.relics.Cogwheel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class PhoenixBtnPatch {
	public static Field charInfoField;
	public static final Hitbox relicHitbox = new Hitbox(40.0f * Settings.scale * (0.01f + (1.0f - 0.019f)), 40.0f * Settings.scale);
	public static final Hitbox expansionHitbox = new Hitbox(40.0f * Settings.scale * (0.01f + (1.0f - 0.019f)), 40.0f * Settings.scale);
	public static final Hitbox challengeDownHitbox = new Hitbox(40.0f * Settings.scale * (0.01f + (1.0f - 0.019f)), 40.0f * Settings.scale);
	public static final Hitbox challengeUpHitbox = new Hitbox(40.0f * Settings.scale * (0.01f + (1.0f - 0.019f)), 40.0f * Settings.scale);

	public static AbstractRelic r;
	public static final ArrayList<PowerTip> expansionTips = new ArrayList<>();
	public static final ArrayList<PowerTip> challengeTips = new ArrayList<>();
	public static boolean shouldRefreshUnlocks = false;

	public static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("constructCharScreenText");

	@SpirePatch(clz = CharacterOption.class, method = "renderRelics")
	public static class RenderBtn{
		public static void Postfix(CharacterOption obj, SpriteBatch sb) {
			if (obj.name == TheConstruct.charStrings.NAMES[1] && obj.selected) {
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
				if (shouldRefreshUnlocks){
					ReflectionHacks.setPrivate(obj,obj.getClass(),"unlocksRemaining",5-UnlockTracker.getUnlockLevel(TheConstructEnum.THE_CONSTRUCT_MOD));
					shouldRefreshUnlocks = false;
				}
				r.updateDescription(TheConstructEnum.THE_CONSTRUCT_MOD);
				relicHitbox.move(190.0f * Settings.scale, Settings.HEIGHT / 2.0f - 190.0f * Settings.scale);
				expansionHitbox.move(190.0f * Settings.scale, Settings.HEIGHT / 2.0f - 140.0f * Settings.scale);
				//challengeDownHitbox.move(170.0f * Settings.scale, Settings.HEIGHT / 2.0f - 260.0f * Settings.scale);
				challengeDownHitbox.move(190.0f * Settings.scale, Settings.HEIGHT / 2.0f - 240.0f * Settings.scale);
				//challengeUpHitbox.move(220.0f * Settings.scale, Settings.HEIGHT / 2.0f - 260.0f * Settings.scale);
				relicHitbox.render(sb);
				expansionHitbox.render(sb);
				challengeDownHitbox.render(sb);
				//challengeUpHitbox.render(sb);

				sb.setColor(Color.WHITE);
				//sb.draw(ImageMaster.CF_LEFT_ARROW, challengeDownHitbox.cX - 64.0f, challengeDownHitbox.cY - 64.0f, 64.0f, 64.0f, 128.0f, 128.0f, Settings.scale * 0.5f * (0.01f + (1.0f - 0.019f)), Settings.scale * 0.5f * (0.01f + (1.0f - 0.019f)), 0.0f, 0, 0, 64, 64, false, false);
				//sb.draw(ImageMaster.CF_RIGHT_ARROW, challengeUpHitbox.cX - 64.0f, challengeUpHitbox.cY - 64.0f, 64.0f, 64.0f, 128.0f, 128.0f, Settings.scale * 0.5f * (0.01f + (1.0f - 0.019f)), Settings.scale * 0.5f * (0.01f + (1.0f - 0.019f)), 0.0f, 0, 0, 64, 64, false, false);
				//FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, "Challenge Mode", challengeUpHitbox.cX + 25f * Settings.scale, challengeUpHitbox.cY + 20f * Settings.scale, Settings.BLUE_TEXT_COLOR);
				sb.draw(ImageMaster.CHECKBOX, challengeDownHitbox.cX - 32.0f, challengeDownHitbox.cY - 32.0f, 32.0f, 32.0f, 64.0f, 64.0f, Settings.scale * (0.01f + (1.0f - 0.019f)), Settings.scale * (0.01f + (1.0f - 0.019f)), 0.0f, 0, 0, 64, 64, false, false);
				if (ConstructMod.challengeLevel > 0)
					sb.draw(ImageMaster.TICK, challengeDownHitbox.cX - 32.0f, challengeDownHitbox.cY - 32.0f, 32.0f, 32.0f, 64.0f, 64.0f, Settings.scale * (0.01f + (1.0f - 0.019f)), Settings.scale * (0.01f + (1.0f - 0.019f)), 0.0f, 0, 0, 64, 64, false, false);
				FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, uiStrings.TEXT[0], challengeDownHitbox.cX + 25f * Settings.scale, challengeDownHitbox.cY, Settings.BLUE_TEXT_COLOR);

				if (UnlockTracker.getUnlockLevel(TheConstructEnum.THE_CONSTRUCT_MOD) >= 4) {
					sb.draw(ImageMaster.CHECKBOX, relicHitbox.cX - 32.0f, relicHitbox.cY - 32.0f, 32.0f, 32.0f, 64.0f, 64.0f, Settings.scale * (0.01f + (1.0f - 0.019f)), Settings.scale * (0.01f + (1.0f - 0.019f)), 0.0f, 0, 0, 64, 64, false, false);
					sb.setColor(new Color(0.0f, 0.0f, 0.0f, 0.25f));
					sb.draw(r.outlineImg, relicHitbox.cX - 64.0f, relicHitbox.cY - 64.0f, 64.0f, 64.0f, 128.0f, 128.0f, Settings.scale * 0.5f * (0.01f + (1.0f - 0.019f)), Settings.scale * 0.5f * (0.01f + (1.0f - 0.019f)), 0.0f, 0, 0, 128, 128, false, false);
					sb.setColor(Color.WHITE);
					sb.draw(r.img, relicHitbox.cX - 64.0f, relicHitbox.cY - 64.0f, 64.0f, 64.0f, 128.0f, 128.0f, Settings.scale * 0.5f * (0.01f + (1.0f - 0.019f)), Settings.scale * 0.5f * (0.01f + (1.0f - 0.019f)), 0.0f, 0, 0, 128, 128, false, false);
					FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, uiStrings.TEXT[1], relicHitbox.cX + 25f * Settings.scale, relicHitbox.cY, Settings.BLUE_TEXT_COLOR);

					if (UnlockTracker.getUnlockLevel(TheConstructEnum.THE_CONSTRUCT_MOD) >= 5) {
						sb.draw(ImageMaster.CHECKBOX, expansionHitbox.cX - 32.0f, expansionHitbox.cY - 32.0f, 32.0f, 32.0f, 64.0f, 64.0f, Settings.scale * (0.01f + (1.0f - 0.019f)), Settings.scale * (0.01f + (1.0f - 0.019f)), 0.0f, 0, 0, 64, 64, false, false);
						sb.setColor(Color.ORANGE);
						if (ConstructMod.overheatedExpansion)
							sb.draw(ImageMaster.TICK, expansionHitbox.cX - 32.0f, expansionHitbox.cY - 32.0f, 32.0f, 32.0f, 64.0f, 64.0f, Settings.scale * (0.01f + (1.0f - 0.019f)), Settings.scale * (0.01f + (1.0f - 0.019f)), 0.0f, 0, 0, 64, 64, false, false);
						FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, uiStrings.TEXT[2], expansionHitbox.cX + 25f * Settings.scale, expansionHitbox.cY, Settings.BLUE_TEXT_COLOR);
					}
				}
			}
		}
	}

	@SpirePatch(clz = CharacterOption.class, method = "updateHitbox")
	public static class UpdateHitbox{
		public static void Postfix(CharacterOption obj) {
			if (obj.name == TheConstruct.charStrings.NAMES[1] && obj.selected) {
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

								ConstructMod.logger.debug("BLUE" + Settings.BLUE_TEXT_COLOR.r);
								ConstructMod.logger.debug("BLUE" + Settings.BLUE_TEXT_COLOR.g);
								ConstructMod.logger.debug("BLUE" + Settings.BLUE_TEXT_COLOR.b);

								ConstructMod.logger.debug("RED" + Settings.RED_TEXT_COLOR.r);
								ConstructMod.logger.debug("RED" + Settings.RED_TEXT_COLOR.g);
								ConstructMod.logger.debug("RED" + Settings.RED_TEXT_COLOR.b);

								ConstructMod.logger.debug("GREEN" + Settings.GREEN_TEXT_COLOR.r);
								ConstructMod.logger.debug("GREEN" + Settings.GREEN_TEXT_COLOR.g);
								ConstructMod.logger.debug("GREEN" + Settings.GREEN_TEXT_COLOR.b);

								ConstructMod.logger.debug("YELLOW" + Settings.LIGHT_YELLOW_COLOR.r);
								ConstructMod.logger.debug("YELLOW" + Settings.LIGHT_YELLOW_COLOR.g);
								ConstructMod.logger.debug("YELLOW" + Settings.LIGHT_YELLOW_COLOR.b);


								CharSelectInfo charInfo = (CharSelectInfo) charInfoField.get(obj);

								String tmp = r.relicId;
								r = RelicLibrary.getRelic(charInfo.relics.get(0));
								ConstructMod.logger.debug(r.name);
								charInfo.relics.remove(0);
								charInfo.relics.add(0,tmp);
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

						if (expansionTips.isEmpty()){
							expansionTips.add(new PowerTip(uiStrings.TEXT[3],uiStrings.TEXT[4]));
						}
						if (InputHelper.mX < 1400.0f * Settings.scale) {
							TipHelper.queuePowerTips(InputHelper.mX + 60.0f * Settings.scale, InputHelper.mY - 50.0f * Settings.scale, expansionTips);
						} else {
							TipHelper.queuePowerTips(InputHelper.mX - 350.0f * Settings.scale, InputHelper.mY - 50.0f * Settings.scale, expansionTips);
						}

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
								ConstructMod.adjustRelics();

								if (ConstructMod.overheatedExpansion) {
									CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT, true);
									CardCrawlGame.sound.playA("ATTACK_FIRE", 0.2f);
								}

							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
				challengeDownHitbox.update();
				if (challengeDownHitbox.hovered) {

					if (challengeTips.isEmpty()){
						challengeTips.add(new PowerTip(uiStrings.TEXT[0],uiStrings.TEXT[5] + uiStrings.TEXT[6]));
					}
					if (InputHelper.mX < 1400.0f * Settings.scale) {
						TipHelper.queuePowerTips(InputHelper.mX + 60.0f * Settings.scale, InputHelper.mY - 50.0f * Settings.scale, challengeTips);
					} else {
						TipHelper.queuePowerTips(InputHelper.mX - 350.0f * Settings.scale, InputHelper.mY - 50.0f * Settings.scale, challengeTips);
					}

					if (InputHelper.justClickedLeft) {
						CardCrawlGame.sound.playA("UI_CLICK_1", -0.4f);
						challengeDownHitbox.clickStarted = true;
					}
					if (challengeDownHitbox.clicked) {
						try {
							CharSelectInfo charInfo = (CharSelectInfo) charInfoField.get(obj);

							challengeDownHitbox.clicked = false;
							if (ConstructMod.challengeLevel == 0){
								ConstructMod.challengeLevel = 1;
								charInfo.relics.add(1,ConstructMod.challengeRelics.get(ConstructMod.challengeLevel-1).relicId);
							}
							else{
								charInfo.relics.remove(1);
								ConstructMod.challengeLevel = 0;
							}
							ConstructMod.saveData();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				/*challengeDownHitbox.update();
				if (challengeDownHitbox.hovered) {

					if (challengeTips.isEmpty()){
						challengeTips.add(new PowerTip("Challenge Mode","Challenge Mode modifies your character-specific cards and items for a more difficult climb."));
					}
					if (InputHelper.mX < 1400.0f * Settings.scale) {
						TipHelper.queuePowerTips(InputHelper.mX + 60.0f * Settings.scale, InputHelper.mY - 50.0f * Settings.scale, challengeTips);
					} else {
						TipHelper.queuePowerTips(InputHelper.mX - 350.0f * Settings.scale, InputHelper.mY - 50.0f * Settings.scale, challengeTips);
					}

					if (InputHelper.justClickedLeft) {
						CardCrawlGame.sound.playA("UI_CLICK_1", -0.4f);
						challengeDownHitbox.clickStarted = true;
					}
					if (challengeDownHitbox.clicked) {
						try {
							CharSelectInfo charInfo = (CharSelectInfo) charInfoField.get(obj);

							challengeDownHitbox.clicked = false;
							if (ConstructMod.challengeLevel > 0){
								charInfo.relics.remove(1);
								ConstructMod.challengeLevel = ConstructMod.challengeLevel-1;
								if (ConstructMod.challengeLevel > 0) charInfo.relics.add(1,ConstructMod.challengeRelics.get(ConstructMod.challengeLevel-1).relicId);
							}
							ConstructMod.saveData();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				challengeUpHitbox.update();
				if (challengeUpHitbox.hovered) {

					if (challengeTips.isEmpty()){
						challengeTips.add(new PowerTip("Challenge Mode","Challenge Mode modifies your character-specific cards and items for a more difficult climb."));
					}
					if (InputHelper.mX < 1400.0f * Settings.scale) {
						TipHelper.queuePowerTips(InputHelper.mX + 60.0f * Settings.scale, InputHelper.mY - 50.0f * Settings.scale, challengeTips);
					} else {
						TipHelper.queuePowerTips(InputHelper.mX - 350.0f * Settings.scale, InputHelper.mY - 50.0f * Settings.scale, challengeTips);
					}

					if (InputHelper.justClickedLeft) {
						CardCrawlGame.sound.playA("UI_CLICK_1", -0.4f);
						challengeUpHitbox.clickStarted = true;
					}
					if (challengeUpHitbox.clicked) {
						try {
							CharSelectInfo charInfo = (CharSelectInfo) charInfoField.get(obj);

							challengeUpHitbox.clicked = false;
							if (ConstructMod.challengeLevel < 3){
								if (charInfo.relics.size() > 1) charInfo.relics.remove(1);
								ConstructMod.challengeLevel = ConstructMod.challengeLevel+1;
								charInfo.relics.add(1,ConstructMod.challengeRelics.get(ConstructMod.challengeLevel-1).relicId);
							}
							ConstructMod.saveData();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}*/
			}
		}
	}
}