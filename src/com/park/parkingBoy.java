package com.park;

public class parkingBoy extends Boys {
	protected carPark[] parklist;
	protected int parklots;
	protected int totals;
	protected int parknum;
	
	public parkingBoy(int parklots, int[] count){
		this.parklist = new carPark[parklots];
		for(int i=0; i<parklots; i++){
			this.parklist[i] = new carPark(count[i]);
			this.totals += count[i];
		}
		this.parklots = parklots;
		this.parknum = -1;
	}
	
	public boolean pushACar(String IDcard, String carNumber){
		carPark park = this.getAnEmptySpace();
		if(park == null){
			return false;
		}
		boolean pushed = park.pushACar(IDcard, carNumber);
		if(pushed){
			this.totals--;
			return true;
		}
		return false;
	}
	
	public carPark getAnEmptySpace(){
		for(int i=0;i<this.parklots; i++){
			if(this.parklist[i].getEmptySpaces() > 0){
				this.parknum = i;
				return this.parklist[i];
			}
		}
		return null;
	}
	
	public Object popAcar(String IDcard){
		for(int i=0; i< this.parklots;i++){
			Object obj = this.parklist[i].popACar(IDcard);
			if(obj != null){
				this.totals++;
				return obj;
			}
		}
		return null;
	}
	public int getEmptySpace(){
		return this.totals;
	}
	
	public int getParkNum(){
		return this.parknum;
	}
	public String whoAmI(){
		return "parking-boy";
	}
}
