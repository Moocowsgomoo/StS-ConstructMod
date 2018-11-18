package constructmod.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import constructmod.ConstructMod;
import constructmod.powers.OilSpillPower;
import constructmod.relics.WeddingRing;

@SpirePatch(clz= Burn.class, method = "use")
public class OnBurnDamagePatch {
	public static void Postfix(Burn obj, AbstractPlayer p, AbstractMonster monster) {
		if (AbstractDungeon.getMonsters() == null) return;
		for (AbstractMonster m : AbstractDungeon.getMonsters().monsters){
			if (m.hasPower(OilSpillPower.POWER_ID)) ((OilSpillPower)m.getPower(OilSpillPower.POWER_ID)).onBurnDamage();
		}
	}
}