package game.engine.interfaces;

public interface Attackee {
	
	int getCurrentHealth();
	void setCurrentHealth(int health);
	int getResourcesValue();
	default boolean isDefeated(){
		if (getCurrentHealth()<=0){
			return true;
		}
		else {
			return false;
		}
	}
	default int takeDamage(int damage){
		setCurrentHealth(getCurrentHealth()-damage);
		if(getCurrentHealth()>0){
			return 0;
		}
		else {
			return getResourcesValue();
		}
	}
}
