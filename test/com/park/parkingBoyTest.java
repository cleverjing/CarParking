package com.park;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import com.park.carPark;
import com.park.parkingBoy;

public class parkingBoyTest extends TestCase {
	private int parkinglots = 5;
	private int count = 10;
	private String IDCard = "123456789";
	private String carNumber = "¾©A12345"; 
	private parkingBoy boy = new parkingBoy(this.parkinglots, this.count);

	@Test
	public void test_parkboy_push_a_car_when_lots_have_space_successly(){
		boolean result = this.boy.pushACar(this.IDCard, this.carNumber);
		assertTrue("success", result);
		int number = this.boy.getEmptySpace();
		assertEquals(number, (this.parkinglots * this.count - 1));
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
