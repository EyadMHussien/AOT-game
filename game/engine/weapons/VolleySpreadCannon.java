package game.engine.weapons;

import game.engine.titans.Titan;

import java.util.PriorityQueue;

public class VolleySpreadCannon extends Weapon {
	public final static int WEAPON_CODE = 3;
	private final int minRange;
	private final int maxRange;

	public int getMinRange() {
		return minRange;
	}

	public int getMaxRange() {
		return maxRange;
	}

	public VolleySpreadCannon(int baseDamage, int minRange, int maxRange) {
		super(baseDamage);
		this.maxRange = maxRange;
		this.minRange = minRange;
	}
	public int turnAttack(PriorityQueue<Titan> laneTitans) {
		int out = 0;
		PriorityQueue<Titan> tmp = new PriorityQueue<>(laneTitans);
		//for (Titan t : laneTitans) {
		while(!tmp.isEmpty()){
				Titan target = tmp.poll();
				if (target.getDistance() >= minRange && target.getDistance() <= maxRange) {
					out += attack(target);
					if (target.isDefeated()) {
						laneTitans.remove(target);
					}
				}
			}
		return out;
	}




}
