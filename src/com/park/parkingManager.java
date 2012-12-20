package com.park;

import com.park.parkingBoy;
import com.park.smartParkingBoy;
import com.park.superParkingBoy;

public class parkingManager {
	private parkingBoy[] boys;
	private smartParkingBoy[] smart_boys;
	private superParkingBoy[] super_boys;
	int[] work_counts;
	private int num_boys;
	private int num_smart_boys;
	private int num_super_boys;
	private int total_members;
	private int service_num;
	private int cur_parkcounts;
	private int total_parkcounts;
	private int priority;
	
	public parkingManager(int[] nums, int[] parklots, int[][] count){
		this.boys = new parkingBoy[nums[0]];
		this.smart_boys = new smartParkingBoy[nums[1]];
		this.super_boys = new superParkingBoy[nums[2]];
		this.num_boys = nums[0];
		this.num_smart_boys = nums[1];
		this.num_super_boys = nums[2];
		this.cur_parkcounts = 0;
		this.setPriority(-1);

		for(int i = 0; i < nums.length; i++){
			this.total_members += nums[i];
		}
		this.work_counts = new int[this.total_members];
		for(int i = 0; i < this.total_members; i++){
			this.work_counts[i] = 0;	
			if(i < this.num_boys){
				this.boys[i] = new parkingBoy(parklots[i],count[i]);
				this.total_parkcounts += this.boys[i].getEmptySpace();
			}else if(i < this.num_boys+this.num_smart_boys){
				int key = i-this.num_boys;
				this.smart_boys[key] = new smartParkingBoy(parklots[i],count[i]);
				this.total_parkcounts += this.smart_boys[key].getEmptySpace();
			}else{
				int key = i-this.num_boys-this.num_smart_boys;
				this.super_boys[key] = new superParkingBoy(parklots[i],count[i]);
				this.total_parkcounts += this.super_boys[key].getEmptySpace();
			}			
		}
	}
	
	public Boys let_a_boy_to_park(){
		int min = 0;
		Boys just_boy = null;
		if(this.priority <= 0 && this.boys.length > 0){
			just_boy = this.boys[0];
			for(int i = 0; i < this.num_boys; i++){
				if(work_counts[i] < min){
					min = work_counts[i];
					just_boy = this.boys[i];
					this.service_num = i;
				}
			}
		}else if((this.priority == -1 || this.priority == 1) && this.smart_boys.length > 0){
			just_boy = this.smart_boys[0];
			for(int i = 0; i < this.num_smart_boys; i++){
				if(work_counts[i] < min){
					min = work_counts[i];
					just_boy = this.smart_boys[i];
					this.service_num = i;
				}
			}
		}else if((this.priority == -1 || this.priority == 2) && this.super_boys.length > 0){
			just_boy = this.super_boys[0];
			for(int i = 0; i < this.num_super_boys; i++){
				if(work_counts[i] < min){
					min = work_counts[i];
					just_boy = this.super_boys[i];
					this.service_num = i;
				}
			}
		}
		return just_boy;
	}
	
	public String pushACar(String IDcard, String carNumber){
		Boys just_boy = this.let_a_boy_to_park();
		boolean pushed = just_boy.pushACar(IDcard, carNumber);
		if(pushed){
			this.work_counts[this.service_num]++;
			this.total_parkcounts--;
			this.cur_parkcounts++;
			return just_boy.whoAmI() + " " +this.service_num + " " + just_boy.getParkNum();
		}
		return "";
	}
	
	public int getEmptySpace(){
		return this.total_parkcounts;
	}
	
	public void setPriority(int priority){
		this.priority = priority;
	}
	
	public Object popACar(String IDcard, String ticket){
		String[] str = ticket.split(" ");
		String boy = str[0];
		int service_num = Integer.valueOf(str[1]);
		Object obj;
		if(boy.equals("parking-boy")){
			obj = this.boys[service_num].popAcar(IDcard);
		}else if(boy.equals("smart-boy")){
			obj = this.smart_boys[service_num].popAcar(IDcard);
		}else if(boy.equals("super-boy")){
			obj = this.super_boys[service_num].popAcar(IDcard);
		}else{
			return null;
		}
		if(obj == null) return obj;
		this.work_counts[this.service_num]++;
		this.total_parkcounts++;
		this.cur_parkcounts--;
		return obj;
	}
}
