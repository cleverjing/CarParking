package com.park;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import com.park.superParkingBoy;

public class superParkingBoyTest extends TestCase{

	private int parkinglots = 5;
	private int[] count = {10,8,12,15,5};
	private String IDCard = "123456789";
	private String carNumber = "京A12345"; 
	private superParkingBoy boy = null;
	
	@Before
	public void setUp() throws Exception{
		carPark[] parklist = new carPark[this.parkinglots];
		int totals = 0;
		for(int i=0; i<this.count.length; i++){
			parklist[i] = new carPark(this.count[i]);
			totals += count[i];
		}
		boy = new superParkingBoy(this.parkinglots, parklist, totals);
		 this.boy.pushACar("23464752", "京A12845");
		 this.boy.pushACar("523452656","京B43212");
		 this.boy.pushACar("265898525","京B2312");
		 this.boy.pushACar("256987454","京B26987");
		 this.boy.pushACar("523452758","京B43435");
		 this.boy.pushACar("089768645","京B23212");
	}
	
	@Test
	public void test_parkboy_push_cars_when_lots_have_space_successly(){
		carPark cp = this.boy.getAnEmptySpace();
		boolean result = this.boy.pushACar(this.IDCard, this.carNumber);
		assertTrue("success", result);
		int number = cp.getEmptySpaces();
		assertEquals(number, 10);
		assertEquals(cp, boy.parklist[2]);
		
	}
	
	@Test
	public void test_parkboy_get_a_car_when_lots_have_a_car_successly(){
		this.boy.pushACar(this.IDCard, this.carNumber);
		Object result = this.boy.popAcar(this.IDCard);
		assertNotNull("Success", result);
		int count = this.boy.getEmptySpace();
		assertEquals(count, 44);
	}
}
