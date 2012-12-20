package com.park;

public class parkingBoy extends Boys {
	protected carPark[] parklist;
	protected int parklots; // 停车场数
	protected int totals; // 可用空车位
	protected int parknum; // 停车场编号
	
	public parkingBoy(int parklots, carPark[] list, int totals){
		this.parklist = list;
		this.totals = totals;
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
