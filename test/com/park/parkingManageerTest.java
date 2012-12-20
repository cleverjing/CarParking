package com.park;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

public class parkingManageerTest extends TestCase {
	private int[] boys = {5,3,2};
	private parkingManager pm;
	private int[] parklots = {1,2,3,4,5,5,4,3,2,1};
	private int[][] counts = {{10},{10,9},{5,4,3},{5,7,4,8},{2,6,6,7,4},{2,3,4,5,6},{9,8,6,4},{4,2,7},{5,8},{4}};
	private String[] IDCard = {"123456789", "234567891", "345678912", "456789123","567891234", "678912345"};
	private String[] carNumber = {"京A12345","京B12345","京A13456","京A56789","京N45673","京J23456"}; 
	private int total_counts = 167;
	
	@Before
	public void setUp(){
		this.pm = new parkingManager(boys,parklots,counts);
	}

	@Test
	public void test_choose_a_boy_by_randrom_successfully(){
		Boys boy = this.pm.let_a_boy_to_park();
		assertNotNull("success", boy);
	}
	
	@Test
	public void test_choose_parking_boy_successfully(){
		this.pm.setPriority(0);
		Boys boy = this.pm.let_a_boy_to_park();
		assertNotNull("success", boy);
		assertTrue(boy instanceof parkingBoy);
	}
	
	@Test
	public void test_choose_smart_boy_successfully(){
		this.pm.setPriority(1);
		Boys boy = this.pm.let_a_boy_to_park();
		assertNotNull("success", boy);
		assertTrue(boy instanceof smartParkingBoy);
	}
	@Test
	public void test_choose_super_boy_successfully(){
		this.pm.setPriority(2);
		Boys boy = this.pm.let_a_boy_to_park();
		assertNotNull("success", boy);
		assertTrue(boy instanceof superParkingBoy);
	}
	
	@Test
	public void test_have_no_parking_boy_but_choose_it_return_null_successfully(){
		this.boys[0] = 0;
		int[][] counts = {{2,3,4,5,6},{9,8,6,4},{4,2,7},{5,8},{4}};
		int[] parklots = {5,4,3,2,1};
		this.pm = new parkingManager(this.boys,parklots,counts);
		this.pm.setPriority(0);
		Boys boy = this.pm.let_a_boy_to_park();
		assertNull(boy);
	}
	
	@Test
	public void test_push_a_car_by_random_boy_successfully(){
		String result = this.pm.pushACar(this.IDCard[0],this.carNumber[0]);
		assertNotNull(result);
		int empty_space = this.pm.getEmptySpace();
		assertEquals(empty_space, this.total_counts - 1);
	}
	
	@Test
	public void test_pop_a_car_by_effective_IDCard_successfully(){
		String ticket = this.pm.pushACar(this.IDCard[0],this.carNumber[0]);
		Object result = this.pm.popACar(this.IDCard[0], ticket);
		assertNotNull(result);
		assertEquals(result,this.carNumber[0]);
		int empty_space = this.pm.getEmptySpace();
		assertEquals(empty_space, this.total_counts);
	}
	
	@Test
	public void test_pop_a_car_by_invaid_IDCard_failed(){
		String ticket = this.pm.pushACar(this.IDCard[0],this.carNumber[0]);
		Object result = this.pm.popACar("963258741", ticket);
		assertNull(result);
		int empty_space = this.pm.getEmptySpace();
		assertEquals(empty_space, this.total_counts - 1);
	}
	
	@Test
	public void test_push_five_cars_into_empty_park_successfully(){
		String[] tickets = new String[5];
		for(int i = 0; i <5; i++){
			tickets[i] = this.pm.pushACar(this.IDCard[i],this.carNumber[i]);
			assertNotNull(tickets[i]);
		}
		int empty_space = this.pm.getEmptySpace();
		assertEquals(empty_space, this.total_counts - 5);
	}
	
	@Test
	public void test_push_six_cars_into_empty_park_successfully(){
		String[] tickets = new String[6];
		for(int i = 0; i < 6; i++){
			if(i<2){
				this.pm.setPriority(0);
			}else if(i<4){
				this.pm.setPriority(1);
			}else if(i<6){
				this.pm.setPriority(2);
			}
			tickets[i] = this.pm.pushACar(this.IDCard[i],this.carNumber[i]);
			assertNotNull(tickets[i]);
			String[] str = tickets[i].split(" ");
			String employee = str[0];
			if(i<2){
				assertEquals(employee,"parking-boy");
			}else if(i<4){
				assertEquals(employee,"smart-boy");
			}else if(i<6){
				assertEquals(employee,"super-boy");
			}
		}
		int empty_space = this.pm.getEmptySpace();
		assertEquals(empty_space, this.total_counts - 6);
	}
}
