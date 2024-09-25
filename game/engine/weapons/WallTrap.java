package game.engine.weapons;

import game.engine.titans.Titan;

import java.util.PriorityQueue;

public class WallTrap extends Weapon {
	public final static int WEAPON_CODE = 4;

	public WallTrap(int baseDamage) {
		super(baseDamage);
	}

	public int turnAttack(PriorityQueue<Titan> laneTitans) {
		int out=0;
		Titan t =laneTitans.peek();
		if(t.isDefeated()) {
			return out;
		}
		else{
			if (t.hasReachedTarget()) {
				out += attack(t);
				if (t.isDefeated()) {
					removeTitan(laneTitans, t);
				}
			}
		}
		return out;


	}


}
