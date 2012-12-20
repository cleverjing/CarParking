package com.park;

import com.park.parkingBoy;

import com.park.smartParkingBoy;
import com.park.superParkingBoy;

import java.util.HashMap;

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
	private String getFromWhom; // 经理停车，记录停在哪个停车仔的停车场
	private int total_parklots;  // 停车场的总数
	
	public parkingManager(int[] nums, int[][] count){
		this.employees = new HashMap<String,Boys[]>();
		this.carparks = new HashMap<String, carPark[]>();
		this.cur_parkcounts = 0;
		this.setPriority(-1);
		parkingBoy[] boys = new parkingBoy[nums[0]];
		smartParkingBoy[] smart_boys = new smartParkingBoy[nums[1]];
		superParkingBoy[] super_boys = new superParkingBoy[nums[2]];
		carPark[][] parks = new carPark[3][100];
		// 成员总数
		for(int i = 0; i < nums.length; i++){
			this.total_members += nums[i];
			}
		// 空车位总数 和停车场总数
		for(int i = 0; i < count.length; i++){
			this.total_parklots += count[i].length;
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
		int[] count_tmp = {0,0,0};
		for(int i = 0; i< count.length; i++){
			carPark[] parklist = new carPark[count[i].length];
			int totals = 0;
			int pos = 0;
			
			for(int j=0; j<count[i].length; j++){
				parklist[j] = new carPark(count[i][j]);
				totals += count[i][j];
			}
			
			if(i < boys.length){
				boys[i] = new parkingBoy(count[i].length, parklist, totals);
				pos = 0;
			}else if(i < boys.length + smart_boys.length){
				int key = i-boys.length;
				smart_boys[key] = new smartParkingBoy(count[i].length, parklist, totals);
				pos = 1;
			}else{
				int key = i - boys.length - smart_boys.length;
				super_boys[key] = new superParkingBoy(count[i].length, parklist, totals);
				pos = 2;
			}
			for(carPark cp:parklist){
				parks[pos][count_tmp[pos]] = cp;
				count_tmp[pos]++; 
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
		if(this.priority < 0){
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
		}else if(this.priority >= 0 && this.priority < this.level.length && this.employees.containsKey(this.level[this.priority]) && this.employees.get(this.level[this.priority]).length > 0){
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
		for(int i = 0; i< this.level.length; i++){
			int parklots = this.carparks.get(this.level[i]).length;
			for(int j = 0; j < parklots; j++){
				if(this.carparks.get(this.level[i])[j].getEmptySpaces() > 0){
					this.carparks.get(this.level[i])[j].pushACar(IDcard, carNumber);
					this.getFromWhom = this.level[i];
					this.service_num = j;
					return true;
				}
			}
		}
		return false; 
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
			if(just_boy != null)
				return just_boy.whoAmI() + " " + this.service_num + " " + just_boy.getParkNum();
			else
				return this.getFromWhom + " " + this.service_num;
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
	
	public String reporting(){
		String str = new String();
		for(int i = 0; i < this.level.length; i++){
			Boys[] boys = this.employees.get(this.level[i]);	
			for(int j = 0; j < boys.length; j++){
				str += "停车仔编号\t" + j + "\n";
				str += boys[j].reporting();
			}
		}
		str += "total车位数：\t" + this.cur_parkcounts + "\n" + "total空位数:\t" + this.empty_parkcounts + "\n";
		return str;
	}
	/*public String reporting(){
		int total = this.total_parklots;
		int[] carnums = this.getNums("carnums");
		int[] emptynums = this.getNums("emptynums");
		String str = new String();
		for(int i = 0; i < total; i++){
			str += "\t停车场编号:\t" + i + "\n" + "\t车位数:\t" + carnums[i] + "\n" + "\t空位数:\t" + emptynums[i] + "\n\n\n";
		}
		str += "\ttotal车位数：\t" + carnums[total] + "\n" + "\ttotal空位数:\t" + emptynums[total] + "\n\n\n";
		return str;
	}
	
	public int[] getNums(String type){
		int total = this.total_parklots;
		int[] nums = new int[total + 1];
		int count = 0;
		for(int i = 0; i < this.level.length; i++){
			carPark[] parks = this.carparks.get(this.level[i]);
			for(int j = 0; j < parks.length; j++){
				if(parks[j] == null) continue;
				if(type.equals("carnums")){
					nums[count] = parks[j].getTotal() - parks[j].getEmptySpaces();
				}
				else if(type.equals("emptynums")){
					nums[count] = parks[j].getEmptySpaces();
				}
				nums[total] += nums[count];
				count++;
			}
		}
		return nums;
	}
	
	public String reportParkingBoys(){
		int total_mem = this.total_members;
		int total_park = this.total_parklots;
		int[][] carnums = this.getParkingBoyNums("carnums");
		int[][] emptynums = this.getParkingBoyNums("emptynums");
		String str = new String();
		for(int j = 0; j < total_mem; j++){
			str += "停车仔编号\t" + j + "\n";
			for(int i = 0; i < total_park; i++){
				if(carnums[j][i] == 0 && emptynums[j][i] == 0) continue;
				str += "\t停车场编号:\t" + i + "\n" + "\t车位数:\t" + carnums[j][i] + "\n" + "\t空位数:\t" + emptynums[j][i] + "\n\n";
			}
			str += "\ttotal车位数：\t" + carnums[j][total_park] + "\n" + "\ttotal空位数:\t" + emptynums[j][total_park] + "\n\n";
		}
		str += "\ttotal车位数：\t" + this.cur_parkcounts + "\n" + "\ttotal空位数:\t" + this.empty_parkcounts + "\n";
		return str;
	}
	public int[][] getParkingBoyNums(String type){
		int total_mem = this.total_members;
		int total_park = this.total_parklots;
		int[][] nums = new int[total_mem][total_park + 1];
		for(int i = 0; i < this.level.length; i++){
			Boys[] boys = this.employees.get(this.level[i]);
			int count = 0;
			for(int j = 0; j < boys.length; j++){
				if(boys[j] == null) continue;
				if(type.equals("carnums")){
					nums[i][count] = boys[j].getTotalParklots() - boys[j].getEmptySpace();
				}
				else if(type.equals("emptynums")){
					nums[i][count] = boys[j].getEmptySpace();
				}
				nums[i][total_park] += nums[i][count];
				count++;
			}
		}
		return nums;
	}*/
}
