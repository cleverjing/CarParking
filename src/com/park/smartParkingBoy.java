package com.park;

import com.park.parkingBoy;

public class smartParkingBoy extends parkingBoy {
	public smartParkingBoy(int parklots, int[] count) {
		super(parklots, count);
	}
	
	@Override
	public carPark getAnEmptySpace(){
		int max = 0;
		carPark cp = null;
		for(int i=0;i<this.parklots; i++){
			if(this.parklist[i].getEmptySpaces() > max){
				max = this.parklist[i].getEmptySpaces();
				cp = this.parklist[i];
			}
		}
		return cp;
	}
	
	public String whoAmI(){
		return "smart-boy";
	}
	
	
	 
}
