package game.engine.weapons;
import game.engine.titans.Titan;
import game.engine.interfaces.Attacker;

import java.util.PriorityQueue;

public abstract class Weapon implements Attacker {

	private final int baseDamage;

	public Weapon(int baseDamage) {
		this.baseDamage = baseDamage;
	}

	public int getDamage() {
		return baseDamage;
	}

	public abstract int turnAttack(PriorityQueue<Titan> laneTitans);
	public void removeTitan(PriorityQueue<Titan> laneTitans,Titan t){
		laneTitans.remove(t);

	}

}
