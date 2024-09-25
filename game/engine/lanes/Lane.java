package game.engine.lanes;

import java.util.*;

import game.engine.base.Wall;
import game.engine.titans.Titan;
import game.engine.weapons.Weapon;

public class Lane implements Comparable<Lane> {

    private final PriorityQueue<Titan> titans;
    private final ArrayList<Weapon> weapons;
    private final Wall laneWall;
    private int dangerLevel;

    public Lane(Wall laneWall) {
        this.laneWall = laneWall;
        titans = new PriorityQueue<Titan>();
        weapons = new ArrayList<Weapon>();
        dangerLevel = 0;
    }

    public int getDangerLevel() {
        return dangerLevel;
    }

    public void setDangerLevel(int dangerLevel) {
        this.dangerLevel = dangerLevel;
    }

    public Wall getLaneWall() {
        return laneWall;
    }

    public PriorityQueue<Titan> getTitans() {
        return titans;
    }

    public ArrayList<Weapon> getWeapons() {
        return weapons;
    }

    @Override
    public int compareTo(Lane L) {

        return this.dangerLevel - L.dangerLevel;
    }

    public void addTitan(Titan titan) {
        titans.add(titan);
    }

    public void addWeapon(Weapon weapon) {
        weapons.add(weapon);
    }

    public void moveLaneTitans() {
        //PriorityQueue<Titan> temp=new PriorityQueue<>();
        if(titans.isEmpty())return;
        PriorityQueue<Titan> temp=new PriorityQueue<>();
        while(!titans.isEmpty()){
            Titan t = titans.poll();
            t.move();
            temp.offer(t);
        }
        titans.clear();
        titans.addAll(temp);

    }

    public int performLaneTitansAttacks() {
        int out = 0;
        for (Titan t : titans) {
            if (t.hasReachedTarget()) {
                out += t.attack(laneWall);
            }
        }
        return out;
    }

    public int performLaneWeaponsAttacks() {
        int out = 0;
        PriorityQueue<Titan> temp = new PriorityQueue<>();
        for (Titan titan : titans) {
            for (Weapon w : weapons) {
                out += w.turnAttack(titans);
                if (titan.isDefeated()) {
                    break;
                }
            }
            if (!titan.isDefeated()) {
                temp.add(titan);
            }
        }
        titans.clear();
        titans.addAll(temp);
        return out;
    }

    public boolean isLaneLost() {
        return laneWall.getCurrentHealth() <= 0;

    }

    public void updateLaneDangerLevel(){
        int dng=0;
        for(Titan t:titans){
                dng+=t.getDangerLevel();
        }
        dangerLevel = dng;
    }

}
