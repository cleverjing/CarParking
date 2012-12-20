package com.park;

import java.util.HashMap;

public class carPark {
	private HashMap<String,String> idcard2carnum;
	private int count;  // 车位数
	private int totalPark; //停车位总数
	
	public carPark(int COUNT){
		this.count = 0;
		this.totalPark = COUNT;
		this.idcard2carnum = new HashMap<String,String>();
	}

	public boolean pushACar(String IDcard, String carNumber){
		if(this.count == this.totalPark){
			return false;
		}
		this.idcard2carnum.put(IDcard,carNumber);
		this.count++;
		return true;
	}
	public Object popACar(String IDcard){
		if(this.count == 0){
			return null;
		}
		if(! this.idcard2carnum.containsKey(IDcard)){
			return null;
		}
		Object carNumber = this.idcard2carnum.get(IDcard);
		this.count--;
		return carNumber;
	}
	public int getTotal(){
		return this.totalPark;
	}
	public int getEmptySpaces(){
		return this.totalPark - this.count;
	}
	public String reporting(int parknum){
		String str = new String();
		str = "\t停车场编号:\t" + parknum + "\n\t\t车位数:\t" + this.count + "\n" + "\t\t空位数:\t" + this.getEmptySpaces() + "\n";
		return str;
	}
}
