package game.engine.interfaces;

import game.engine.titans.Titan;

public interface Mobil {
	int getDistance();
	void setDistance(int distance);
	  int getSpeed();
	 void setSpeed(int speed);
	default boolean hasReachedTarget(){
		if (getDistance()<=0){
			return true;
		}
		else{
			return false;
		}
	}
	default boolean move(){
		setDistance(getDistance()-getSpeed());
		if(getDistance()<0){
			setDistance(0);
		}
		return hasReachedTarget();

	}

}
