package com.park;

import com.park.carPark;

public abstract class Boys {
	abstract boolean pushACar(String IDcard, String carNumber);
	abstract carPark getAnEmptySpace();
	abstract Object popAcar(String IDcard);
	abstract int getEmptySpace();
	abstract int getParkNum();
	abstract String whoAmI();
	abstract int getTotalParklots();
	abstract String reporting();
}
