package game.engine.weapons.factory;

import java.io.IOException;
import java.util.HashMap;

import game.engine.dataloader.DataLoader;
import game.engine.exceptions.InsufficientResourcesException;
import game.engine.weapons.WeaponRegistry;

public class WeaponFactory {

	private final HashMap<Integer, WeaponRegistry> weaponShop;

	public WeaponFactory() throws IOException {
		weaponShop = DataLoader.readWeaponRegistry();
	}

	public HashMap<Integer, WeaponRegistry> getWeaponShop() {
		return weaponShop;
	}

	public FactoryResponse buyWeapon(int resources, int weaponCode) throws InsufficientResourcesException{
		if(resources < weaponShop.get(weaponCode).getPrice()){
			throw new InsufficientResourcesException(resources);
		}
		WeaponRegistry weapon = weaponShop.get(weaponCode);
		int remainingResources = resources - weapon.getPrice();
		return new FactoryResponse(weapon.buildWeapon(), remainingResources);
	}
	public void addWeaponToShop(int code, int price){
		weaponShop.put(code, new WeaponRegistry(code, price));
	}
	public void addWeaponToShop(int code, int price, int damage, String name){
		weaponShop.put(code, new WeaponRegistry(code, price, damage, name));
	}
	public void addWeaponToShop(int code, int price, int damage, String name, int minRange, int maxRange){
		weaponShop.put(code, new WeaponRegistry(code, price, damage, name, minRange, maxRange));
	}
}
