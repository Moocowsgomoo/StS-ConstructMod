package constructmod.cards;

import java.lang.reflect.Method;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;

import basemod.ReflectionHacks;
import basemod.abstracts.CustomCard;

public abstract class AbstractConstructCard extends CustomCard {
	
	protected boolean hasCycled = false;
	protected boolean megaUpgraded = false;
	private boolean forcedUpgrade = false;
	
	public AbstractConstructCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target, int cardPool) {
		super(id, name, img, cost, rawDescription, type, color, rarity, target, cardPool);
	}
	
	@Override
    public boolean canUpgrade() {
		return super.canUpgrade() || forcedUpgrade ||
				(AbstractDungeon.player.hasRelic("ClockworkPhoenix") && 
						this.upgraded && 
						!this.megaUpgraded && 
						AbstractDungeon.getCurrRoom().phase != RoomPhase.COMBAT);
    }
	
	public void upgrade(boolean forcedUpgrade) {
		this.forcedUpgrade = forcedUpgrade;
		this.upgrade();
		this.forcedUpgrade = false;
	}
	
	public AbstractCard makeStatEquivalentCopy() {
        final AbstractConstructCard card = (AbstractConstructCard) this.makeCopy();
        for (int i = 0; i < this.timesUpgraded; ++i) {
            card.upgrade(true);
        }
        card.name = this.name;
        card.target = this.target;
        card.upgraded = this.upgraded;
        card.timesUpgraded = this.timesUpgraded;
        card.baseDamage = this.baseDamage;
        card.baseBlock = this.baseBlock;
        card.baseMagicNumber = this.baseMagicNumber;
        card.cost = this.cost;
        card.costForTurn = this.costForTurn;
        card.isCostModified = this.isCostModified;
        card.isCostModifiedForTurn = this.isCostModifiedForTurn;
        card.inBottleLightning = this.inBottleLightning;
        card.inBottleFlame = this.inBottleFlame;
        card.inBottleTornado = this.inBottleTornado;
        card.isSeen = this.isSeen;
        card.isLocked = this.isLocked;
        return card;
    }
	
	protected void megaUpgradeName() {
        this.upgradeName();
        this.megaUpgraded = true;
        this.initializeTitle();
    }
	
	@Override
	public void render(final SpriteBatch sb, final boolean selected) {
		if (!this.megaUpgraded) super.render(sb, selected);
		else {
			if (!Settings.hideCards) {
	            if (this.flashVfx != null) {
	                this.flashVfx.render(sb);
	            }
				boolean hovered = (boolean) ReflectionHacks.getPrivate(this, AbstractCard.class, "hovered");
				this.renderMegaCard(sb, hovered, selected);
	            this.hb.render(sb);
	        }
		}
    }
    
	@Override
    public void renderWithSelections(final SpriteBatch sb) {
        if (!this.megaUpgraded) super.renderWithSelections(sb);
        else {
        	this.renderMegaCard(sb, false, true);
        }
    }
	
	private void renderMegaCard(final SpriteBatch sb, final boolean hovered, final boolean selected) {
		
		try {
		
			Method isOnScreenMethod = AbstractCard.class.getDeclaredMethod("isOnScreen");
			isOnScreenMethod.setAccessible(true);
			Method updateGlowMethod = AbstractCard.class.getDeclaredMethod("updateGlow");
			updateGlowMethod.setAccessible(true);
			Method renderGlowMethod = AbstractCard.class.getDeclaredMethod("renderGlow",SpriteBatch.class);
			renderGlowMethod.setAccessible(true);
			Method renderImageMethod = AbstractCard.class.getDeclaredMethod("renderImage",SpriteBatch.class,boolean.class,boolean.class);
			renderImageMethod.setAccessible(true);
			Method renderTitleMethod = AbstractCard.class.getDeclaredMethod("renderTitle",SpriteBatch.class);
			renderTitleMethod.setAccessible(true);
			Method renderTypeMethod = AbstractCard.class.getDeclaredMethod("renderType",SpriteBatch.class);
			renderTypeMethod.setAccessible(true);
			Method renderDescriptionCNMethod = AbstractCard.class.getDeclaredMethod("renderDescriptionCN",SpriteBatch.class);
			renderDescriptionCNMethod.setAccessible(true);
			Method renderDescriptionMethod = AbstractCard.class.getDeclaredMethod("renderDescription",SpriteBatch.class);
			renderDescriptionMethod.setAccessible(true);
			Method renderTintMethod = AbstractCard.class.getDeclaredMethod("renderTint",SpriteBatch.class);
			renderTintMethod.setAccessible(true);
			Method renderEnergyMethod = AbstractCard.class.getDeclaredMethod("renderEnergy",SpriteBatch.class);
			renderEnergyMethod.setAccessible(true);
			Method renderBackMethod = AbstractCard.class.getDeclaredMethod("renderBack",SpriteBatch.class,boolean.class,boolean.class);
			renderBackMethod.setAccessible(true);;	
			
	        if (!Settings.hideCards) {
	        	if (!(boolean)isOnScreenMethod.invoke(this)) {
					return;
	        	}
	            if (!this.isFlipped) {
	                updateGlowMethod.invoke(this);
	                renderGlowMethod.invoke(this,sb);
	                renderImageMethod.invoke(this,sb,hovered,selected);
	                Settings.GREEN_TEXT_COLOR.set(1.0f, 0.5f, 1.0f, 1.0f); // make green text actually purple
	                renderTitleMethod.invoke(this, sb);
	                Settings.GREEN_TEXT_COLOR.set(2147418367); // ...aaand back to green. That was fun.
	                renderTypeMethod.invoke(this, sb);
	                if (Settings.lineBreakViaCharacter) {
	                    renderDescriptionCNMethod.invoke(this, sb);
	                }
	                else {
	                    renderDescriptionMethod.invoke(this, sb);
	                }
	                renderTintMethod.invoke(this, sb);
	                renderEnergyMethod.invoke(this, sb);
	            }
	            else {
	               	renderBackMethod.invoke(this,sb,hovered,selected);
	                this.hb.render(sb);
	            }
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	
}
