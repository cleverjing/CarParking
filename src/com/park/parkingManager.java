package com.park;

import com.park.parkingBoy;

import java.util.Arrays;
import com.park.smartParkingBoy;
import com.park.superParkingBoy;

import java.util.HashMap;

import java.util.Vector;

public class parkingManager {
	private String[] level = {"parking-boy", "smart-boy", "super-boy"};
	private HashMap<String, carPark[]> carparks; // 停车场
	private HashMap<String, Boys[]> employees;  // 停车仔
	private int[] work_counts;  // 记录每个停车仔的工作量，停或取一次车，工作量加1
	private int total_members;
	private int service_num;	// 获取当前停车停车仔的工号
	private int cur_parkcounts;  // 车位数
	private int empty_parkcounts;	// 空位数
	private int priority; // -1 -- 随机  0 -- parking boy 1-- smart boy 2 -- super boy 3 -- manager
	
	public parkingManager(int[] nums, int[] parklots, int[][] count){
		this.employees = new HashMap<String,Boys[]>();
		this.carparks = new HashMap<String, carPark[]>();
		this.cur_parkcounts = 0;
		this.setPriority(-1);
		parkingBoy[] boys = new parkingBoy[nums[0]];
		smartParkingBoy[] smart_boys = new smartParkingBoy[nums[1]];
		superParkingBoy[] super_boys = new superParkingBoy[nums[2]];
		carPark[][] parks = new carPark[3][0];
		// 成员总数
		for(int i = 0; i < nums.length; i++){
			this.total_members += nums[i];
			}
		// 空车位总数
		for(int i = 0; i < count.length; i++){
			for(int j = 0; j < count[i].length; j++){
				this.empty_parkcounts += count[i][j];
			}
		}
		// 工作量计数器初始化
		this.work_counts = new int[this.total_members];
		for(int i = 0; i < this.total_members; i++){
			this.work_counts[i] = 0;	
		}
		
		// 停车场和停车仔初始化
		for(int i = 0; i< count.length; i++){
			carPark[] parklist = new carPark[count[i].length];
			int totals = 0;
			
			for(int j=0; j<count[i].length; j++){
				parklist[j] = new carPark(count[i][j]);
				totals += count[i][j];
			}
			
			if(i < boys.length){
				boys[i] = new parkingBoy(count[i].length, parklist, totals);
				parks[0] = Arrays.copyOf(parks[0], parks[0].length + parklist.length);
			}else if(i < boys.length + smart_boys.length){
				int key = i-boys.length;
				parks[1] = Arrays.copyOf(parks[1], parks[1].length + parklist.length);
				smart_boys[key] = new smartParkingBoy(count[i].length, parklist, totals);
			}else{
				int key = i - boys.length - smart_boys.length;
				parks[2] = Arrays.copyOf(parks[2], parks[2].length + parklist.length);
				super_boys[key] = new superParkingBoy(count[i].length, parklist, totals);
			}
			
		}
		this.employees.put(this.level[0], boys);
		this.employees.put(this.level[1], smart_boys);
		this.employees.put(this.level[2], super_boys);		
		for(int i = 0; i < level.length; i++){
			this.carparks.put(this.level[i], parks[i]);
		}
	}
	
	public Boys let_a_boy_to_park(){
		int min = 0;
		Boys just_boy = null;
		if(this.priority < 0 || this.priority > this.level.length){
			for(int j = 0; j < this.level.length; j++){
				just_boy = this.employees.get(this.level[j])[0];
				for(int i = 0; i < this.employees.get(this.level[j]).length; i++){
					if(work_counts[i] < min){
						min = work_counts[i];
						just_boy = this.employees.get(this.level[j])[i];
						this.service_num = i;
					}
				}
			}
		}else if(this.employees.get(this.level[this.priority]).length > 0){
			just_boy = this.employees.get(this.level[this.priority])[0];
			for(int i = 0; i < this.employees.get(this.level[this.priority]).length; i++){
				if(work_counts[i] < min){
					min = work_counts[i];
					just_boy = this.employees.get(this.level[this.priority])[i];
					this.service_num = i;
				}
			}
		}
		return just_boy;
	}
	
	private boolean pushcar(String IDcard, String carNumber){
		return true;
	}

	public String pushACar(String IDcard, String carNumber){
		Boys just_boy = this.let_a_boy_to_park();
		boolean pushed;
		if(just_boy == null){
			 pushed = this.pushcar(IDcard, carNumber);
		}else{
			pushed = just_boy.pushACar(IDcard, carNumber);
		}
		if(pushed){
			this.work_counts[this.service_num]++;
			this.empty_parkcounts--;
			this.cur_parkcounts++;
			return just_boy.whoAmI() + " " +this.service_num + " " + just_boy.getParkNum();
		}
		return "";
	}
	
	public int getEmptySpace(){
		return this.empty_parkcounts;
	}
	
	public void setPriority(int priority){
		this.priority = priority;
	}
	
	public Object popACar(String IDcard, String ticket){
		String[] str = ticket.split(" ");
		String boy = str[0];
		int service_num = Integer.valueOf(str[1]);
		Object obj;
		if(this.employees.containsKey(boy)){
			obj = this.employees.get(boy)[service_num].popAcar(IDcard);
		}else{
			return null;
		}
		if(obj == null) return obj;
		this.work_counts[this.service_num]++;
		this.empty_parkcounts++;
		this.cur_parkcounts--;
		return obj;
	}
}
