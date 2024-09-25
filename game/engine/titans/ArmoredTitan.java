package game.engine.titans;

public class ArmoredTitan extends Titan {
	public final static int TITAN_CODE = 3;

	public ArmoredTitan(int baseHealth, int baseDamage, int heightInMeters, int distanceFromBase, int speed,
			int resourcesValue, int dangerLevel) {
		super(baseHealth, baseDamage, heightInMeters, distanceFromBase, speed, resourcesValue, dangerLevel);
	}
	public int takeDamage(int damage){
		setCurrentHealth(getCurrentHealth()-(int)(0.25*damage));
		if(getCurrentHealth()>0){
			return 0;
		}
		else {
			return getResourcesValue();
		}
	}
}
