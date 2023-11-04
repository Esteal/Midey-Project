package fr.midey.OnePieceCraftSkills.Utils;

import java.util.Random;

public class ChanceToUpgradeSkill {

	public static boolean chanceToUpgradeSkill(double pourcentage) {
		Random random = new Random();
        int chance = random.nextInt(100);
        if (chance < pourcentage)
        	return true;
        return false;
	}
}
