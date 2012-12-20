package com.park;

public class parkingBoy extends Boys {
	protected carPark[] parklist;
	protected int parklots; // ͣ������
	protected int empty_parklots; // ���ÿճ�λ
	protected int parknum; // ͣ�������
	protected int total_parklots; // ��λ����
	
	public parkingBoy(int parklots, carPark[] list, int empty_parklots){
		this.parklist = list;
		this.empty_parklots = empty_parklots;
		this.parklots = parklots;
		this.parknum = -1;
		this.total_parklots = empty_parklots;
	}
	
	public boolean pushACar(String IDcard, String carNumber){
		carPark park = this.getAnEmptySpace();
		if(park == null){
			return false;
		}
		boolean pushed = park.pushACar(IDcard, carNumber);
		if(pushed){
			this.empty_parklots--;
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
				this.empty_parklots++;
				return obj;
			}
		}
		return null;
	}
	public int getEmptySpace(){
		return this.empty_parklots;
	}
	
	public int getParkNum(){
		return this.parknum;
	}
	public String whoAmI(){
		return "parking-boy";
	}
	public int getTotalParklots(){
		return this.total_parklots;
	}
	public String reporting(){
		String str = new String();
		for(int i = 0; i < this.parklist.length; i++){
			str += this.parklist[i].reporting(i);
		}
		str += "\ttotal��λ��:\t" + (this.total_parklots - this.empty_parklots) + "\n" + "\ttotal��λ��:\t" + this.empty_parklots + "\n";
		return str;
	}
}
