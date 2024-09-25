package game.engine.titans;

public class ColossalTitan extends Titan {
	public final static int TITAN_CODE = 4;

	public ColossalTitan(int baseHealth, int baseDamage, int heightInMeters, int distanceFromBase, int speed,
			int resourcesValue, int dangerLevel) {
		super(baseHealth, baseDamage, heightInMeters, distanceFromBase, speed, resourcesValue, dangerLevel);
	}
	public boolean move(){
		setDistance(getDistance()-getSpeed());
		if(getDistance()<0){
			setDistance(0);
		}
		setSpeed(getSpeed()+1);
		return hasReachedTarget();

	}

}
