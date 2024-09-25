package game.engine.weapons;

import game.engine.titans.Titan;

import java.util.PriorityQueue;

public class PiercingCannon extends Weapon {
	public final static int WEAPON_CODE = 1;

	public PiercingCannon(int baseDamage) {
		super(baseDamage);
	}

	@Override
	public int turnAttack(PriorityQueue<Titan> laneTitans) {
		int out = 0;
		PriorityQueue<Titan> temp=new PriorityQueue<>(laneTitans);
		int ite = Math.min(5, laneTitans.size());

		for(int i = 0;i<ite;i++){
			Titan t = temp.poll();
			out += attack(t);
			if(t.isDefeated()){
				laneTitans.remove(t);
			}
		}
//		laneTitans.clear();
//		laneTitans.addAll(temp);
		return out;
	}
}
