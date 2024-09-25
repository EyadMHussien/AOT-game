package game.engine.weapons;

import game.engine.titans.Titan;

import java.util.PriorityQueue;

public class SniperCannon extends Weapon {
	public final static int WEAPON_CODE = 2;

	public SniperCannon(int baseDamage) {
		super(baseDamage);
	}

//	public int turnAttack(PriorityQueue<Titan> laneTitans) {
//		int out = 0;
//		if (!laneTitans.isEmpty()) {
//			Titan t = laneTitans.peek();
//			t.takeDamage(getDamage());
//			if (t.isDefeated()) {
//				laneTitans.poll();
//				out += t.getResourcesValue();
//			}
//		} else {
//			out += attack(laneTitans.peek());
//			if (laneTitans.peek().isDefeated()) {
//				removeTitan(laneTitans, laneTitans.peek());
//			}
//
//		}
//			return out;
//
//
//	}
public int turnAttack(PriorityQueue<Titan> laneTitans) {
	int out = 0;
	if (!laneTitans.isEmpty()) {
		Titan t = laneTitans.peek();
		if (t != null) {
			t.takeDamage(getDamage());
			if (t.isDefeated()) {
				laneTitans.poll();
				out += t.getResourcesValue();
			}

		}
		}
//	 else {
//
//	}
	return out;
}


}