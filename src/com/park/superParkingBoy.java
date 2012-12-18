package com.park;

public class superParkingBoy extends parkingBoy  {
	public superParkingBoy(int parklots, int count) {
		super(parklots, count);
	}
	
	public carPark getAnEmptySpace(){
		float max = 0;
		carPark cp = null;
		for(int i=0;i<this.parklots; i++){
			float per = (float)this.parklist[i].getEmptySpaces() / this.parklist[i].getTotal();
			if(per > max){
				max = per;
				cp = this.parklist[i];
			}
		}
		return cp;
	}
}
