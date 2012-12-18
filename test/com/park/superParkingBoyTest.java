package com.park;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import com.park.superParkingBoy;

public class superParkingBoyTest extends TestCase{

	private int parkinglots = 5;
	private int count = 10;
	private String IDCard = "123456789";
	private String carNumber = "��A12345"; 
	private superParkingBoy boy = null;
	
	@Before
	public void init(){
		 boy = new superParkingBoy(this.parkinglots, this.count);
		 this.boy.pushACar("23464752", "��A12845");
		 this.boy.pushACar("523452656","��B43212");
		 this.boy.pushACar("265898525","��B2312");
		 this.boy.pushACar("256987454","��B26987");
		 this.boy.pushACar("523452758","��B43435");
		 this.boy.pushACar("089768645","��B23212");
	}
	
	@Test
	public void test_parkboy_push_cars_when_lots_have_space_successly(){
		carPark cp = this.boy.getAnEmptySpace();
		boolean result = this.boy.pushACar(this.IDCard, this.carNumber);
		assertTrue("su" +
				"ccess", result);
		int number = this.boy.getEmptySpace();
		assertEquals(number, (this.parkinglots * this.count - 3));
		assertEquals(cp, boy.parklist[1]);
		
	}
	
	@Test
	public void test_parkboy_get_a_car_when_lots_have_a_car_successly(){
		this.boy.pushACar(this.IDCard, this.carNumber);
		Object result = this.boy.popAcar(this.IDCard);
		assertNotNull("Success", result);
		int count = this.boy.getEmptySpace();
		assertEquals(count, this.parkinglots * this.count);
	}
}
