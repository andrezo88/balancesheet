package com.andre.balancesheet;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class BalancesheetApplicationTests {

	@Test
	void contextLoads() {
		assertDoesNotThrow(this::doNotThrowException);
	}

	private void doNotThrowException(){
		//This method will never throw exception
	}

}
