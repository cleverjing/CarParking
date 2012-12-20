package com.park;

import junit.framework.TestCase;
import com.park.carPark;
import org.junit.Before;
import org.junit.Test;

public class carParkTest extends TestCase {
	private carPark park;
	private String IDCard = "123456789";
	private String carNumber = "京A12345"; 
	
	@Before
	public void setUp(){
		this.park = new carPark(10);
	}
	@Test
	public void test_have_empty_space_push_a_car_success() {
		boolean result = this.park.pushACar(IDCard, carNumber);
		int count = this.park.getEmptySpaces();
		assertTrue("success", result);
	    assertEquals(count, 9);
	}

	@Test
	public void test_have_no_empty_space_push_a_car_return_false(){
		carPark nospace = new carPark(0);
		boolean result = nospace.pushACar(IDCard, carNumber);
		assertFalse("Fail", result);
	}
	
	@Test
	public void test_get_the_car_success(){
		carPark getCar = new carPark(10);
		getCar.pushACar(IDCard, carNumber);
		Object result = getCar.popACar(IDCard);
		assertNotNull("Success", result);
		int count = park.getEmptySpaces();
		assertEquals(count, 10);
	}
	
	@Test
	public void test_get_the_car_from_empty_park_false(){
		carPark getCar = new carPark(10);
		Object result = getCar.popACar(IDCard);
		assertNull("fail", result);
	}
	
	@Test
	public void test_get_the_pushed_in_car_from_park_success(){
		carPark getCar = new carPark(10);
		getCar.pushACar(IDCard, carNumber);
		String result = getCar.popACar(IDCard).toString();
		assertEquals(result, carNumber);
	}
	
	@Test
	public void test_get_the_pushed_in_car_by_invalid_idcard_fail(){
		carPark getCar = new carPark(10);
		getCar.pushACar(IDCard, carNumber);
		Object result = getCar.popACar("122334");
		assertNull(result);
	}
	
	@Test
	public void test_get_twice_the_pushed_in_car_success(){
		carPark getCar = new carPark(10);
		getCar.pushACar(IDCard, carNumber);
		String result1 = getCar.popACar(IDCard).toString();
		Object result2 = getCar.popACar(IDCard);
		assertEquals(result1, carNumber);
		assertNull(result2);
	}
	
	@Test
	public void test_reporting(){
		String str = "停车场编号:\t0\n\t车位数:\t0\n\t空位数:\t10\n";
		assertEquals(str,this.park.reporting(0));
	}
}
