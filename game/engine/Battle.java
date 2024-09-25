package game.engine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

import game.engine.base.Wall;
import game.engine.dataloader.DataLoader;
import game.engine.exceptions.InsufficientResourcesException;
import game.engine.exceptions.InvalidLaneException;
import game.engine.lanes.Lane;
import game.engine.titans.Titan;
import game.engine.titans.TitanRegistry;
import game.engine.weapons.factory.WeaponFactory;

import static game.engine.BattlePhase.*;

public class Battle {

    private final static int[][] PHASES_APPROACHING_TITANS =
            {{1, 1, 1, 2, 1, 3, 4},
                    {2, 2, 2, 1, 3, 3, 4},
                    {4, 4, 4, 4, 4, 4, 4}};

    private final static int WALL_BASE_HEALTH = 10000;
    private final WeaponFactory weaponFactory;
    private final HashMap<Integer, TitanRegistry> titansArchives;
    private final ArrayList<Titan> approachingTitans;
    private final PriorityQueue<Lane> lanes;
    private final ArrayList<Lane> originalLanes;
    private int numberOfTurns;
    private int resourcesGathered;
    private BattlePhase battlePhase;
    private int numberOfTitansPerTurn;
    private int score;
    private int titanSpawnDistance;


    public int getNumberOfTurns() {
        return numberOfTurns;
    }

    public void setNumberOfTurns(int numberOfTurns) {
        this.numberOfTurns = numberOfTurns;
    }

    public int getResourcesGathered() {
        return resourcesGathered;
    }

    public void setResourcesGathered(int resourcesGathered) {
        this.resourcesGathered = resourcesGathered;
    }

    public BattlePhase getBattlePhase() {
        return battlePhase;
    }

    public void setBattlePhase(BattlePhase battlePhase) {
        this.battlePhase = battlePhase;
    }

    public int getNumberOfTitansPerTurn() {
        return numberOfTitansPerTurn;
    }

    public void setNumberOfTitansPerTurn(int numberOfTitansPerTurn) {
        this.numberOfTitansPerTurn = numberOfTitansPerTurn;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTitanSpawnDistance() {
        return titanSpawnDistance;
    }

    public void setTitanSpawnDistance(int titanSpawnDistance) {
        this.titanSpawnDistance = titanSpawnDistance;
    }

    public int[][] getPHASES_APPROACHING_TITANS() {
        return PHASES_APPROACHING_TITANS;
    }

    public int getWALL_BASE_HEALTH() {
        return WALL_BASE_HEALTH;
    }

    public WeaponFactory getWeaponFactory() {
        return weaponFactory;
    }

    public HashMap<Integer, TitanRegistry> getTitansArchives() {
        return titansArchives;
    }

    public ArrayList<Titan> getApproachingTitans() {
        return approachingTitans;
    }

    public PriorityQueue<Lane> getLanes() {
        return lanes;
    }

    public ArrayList<Lane> getOriginalLanes() {
        return originalLanes;
    }


    public Battle(int numberOfTurns, int score, int titanSpawnDistance, int initialNumOfLanes,
                  int initialResourcesPerLane) throws IOException {
        this.numberOfTurns = numberOfTurns;
        this.score = score;
        this.titanSpawnDistance = titanSpawnDistance;
        this.battlePhase = EARLY;
        this.numberOfTitansPerTurn = 1;
        resourcesGathered = initialNumOfLanes * initialResourcesPerLane;
        titansArchives = DataLoader.readTitanRegistry();
        approachingTitans = new ArrayList<Titan>();
        lanes = new PriorityQueue<Lane>();
        originalLanes = new ArrayList<Lane>();
        weaponFactory = new WeaponFactory();
        initializeLanes(initialNumOfLanes);

    }

    private void initializeLanes(int numOfLanes) {

        for (int i = 0; i < numOfLanes; i++) {
            Lane l = new Lane(new Wall(WALL_BASE_HEALTH));
            originalLanes.add(l);
            lanes.add(l);

        }
    }

    public void refillApproachingTitans() {
        approachingTitans.clear();
        switch (battlePhase) {
            case EARLY:
                for (int i = 0; i < PHASES_APPROACHING_TITANS[0].length; i++) {
                    int titanC = PHASES_APPROACHING_TITANS[0][i];
                    TitanRegistry t = titansArchives.get(titanC);
                    approachingTitans.add(t.spawnTitan(titanSpawnDistance));
                }
                break;
            case INTENSE:
                for (int i = 0; i < 7; i++) {
                    int titanC = PHASES_APPROACHING_TITANS[1][i];
                    TitanRegistry t = titansArchives.get(titanC);
                    approachingTitans.add(t.spawnTitan(titanSpawnDistance));
                }
                break;
            case GRUMBLING:
                for (int i = 0; i < 7; i++) {
                    int titanC = PHASES_APPROACHING_TITANS[2][i];
                    TitanRegistry t = titansArchives.get(titanC);
                    approachingTitans.add(t.spawnTitan(titanSpawnDistance));
                }
                break;
        }
    }

    public void purchaseWeapon(int weaponCode, Lane lane) throws InsufficientResourcesException, InvalidLaneException {
        if (lane.isLaneLost() || lane == null || !lanes.contains(lane)) {
            throw new InvalidLaneException();
        } else {
            lane.addWeapon(weaponFactory.buyWeapon(resourcesGathered, weaponCode).getWeapon());
            resourcesGathered -= weaponFactory.getWeaponShop().get(weaponCode).getPrice();
        }
        performTurn();
    }

    public void passTurn() {
        if(!isGameOver()){
            performTurn();
        }else{
            System.out.println("skipping turn");
        }

    }

    private void addTurnTitansToLane() {
        for (int i = 0; i < numberOfTitansPerTurn; i++) {
            if (approachingTitans.isEmpty()) refillApproachingTitans();
            lanes.peek().addTitan(approachingTitans.remove(0));
        }
    }



    private void moveTitans() {
        for (Lane x : lanes) {
            //if (!x.isLaneLost()) {
                x.moveLaneTitans();
            //}
        }
    }

    private int performWeaponsAttacks() {
        int out = 0;
        for (Lane x : lanes) {
            //if (!x.isLaneLost()) {
                out += x.performLaneWeaponsAttacks();
            //}
        }
        resourcesGathered += out;
        setScore(score + out);
        return out;
    }

    private int performTitansAttacks() {
        int out = 0;
        PriorityQueue<Lane> temp = new PriorityQueue<>();
        for (Lane x : lanes) {
            //if (!x.isLaneLost()) {
                out += x.performLaneTitansAttacks();
           // }
            if (!x.isLaneLost()) {
                temp.add(x);
            }
        }
        lanes.clear();
        lanes.addAll(temp);
        return out;
    }

    private void updateLanesDangerLevels() {
        for (Lane l : lanes) {
            //if (!l.isLaneLost()) {
                l.updateLaneDangerLevel();
            //}
        }
        PriorityQueue<Lane> temp = new PriorityQueue<>(lanes.size(), Comparator.comparingInt(Lane::getDangerLevel).reversed());
        temp.addAll(lanes);
        lanes.clear();
        lanes.addAll(temp);
    }
    private void finalizeTurns(){
        numberOfTurns++;
        //if(numberOfTurns < 15) battlePhase = EARLY;
        if(numberOfTurns >= 15 ) {
            battlePhase = BattlePhase.INTENSE;
        }
        if(numberOfTurns >= 30) {
            battlePhase = BattlePhase.GRUMBLING;
        }
        if(numberOfTurns > 30 && numberOfTurns % 5 == 0){
            battlePhase = BattlePhase.GRUMBLING;
            numberOfTitansPerTurn *= 2;
        }
    }
    private void performTurn(){
        moveTitans();
        performWeaponsAttacks();
        performTitansAttacks();
        addTurnTitansToLane();
        updateLanesDangerLevels();
        finalizeTurns();
        if(isGameOver()){
            System.out.println("Game Over");
        }
    }
    public boolean isGameOver() {
        boolean answer = true;
        for (Lane lane : lanes) {
            if (!lane.isLaneLost()) {
                answer = false;
                break;
            }
        }
        return answer;
    }
}
