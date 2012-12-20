package com.park;

public class superParkingBoy extends parkingBoy  {
	public superParkingBoy(int parklots, carPark[] list, int totals) {
		super(parklots, list, totals);
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
	
	public String whoAmI(){
		return "super-boy";
	}
}
