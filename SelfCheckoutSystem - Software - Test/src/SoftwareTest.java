package org.lsmr.selfcheckout.software.test;

import java.math.BigDecimal;
import java.util.Currency;

import org.junit.*;
import org.lsmr.selfcheckout.software.src.Customer;
import org.lsmr.selfcheckout.*;
import org.lsmr.selfcheckout.devices.*;

public class SoftwareTest {
	public Customer c;
	@Before
	public void startUp() {
		c = new Customer();
	}

	@Test
	public void payTest() throws DisabledException{
		c.runningTotal = new BigDecimal(1.00);
		c.startCoinPay();
		Coin coin = new Coin(new BigDecimal(1.00), Currency.getInstance("CAD"));
		c.pay(coin);
		Assert.assertTrue(c.endCoinPay());
	}
	
	@Test
	public void payInsufficientAmount() throws DisabledException {
		c.runningTotal = new BigDecimal(9.00);
		c.startCoinPay();
		Coin coin = new Coin(new BigDecimal(1.00), Currency.getInstance("CAD"));
		c.pay(coin);
		Assert.assertFalse(c.endCoinPay());
	}
	
	@Test
	public void payWithAllCoins() throws DisabledException{
		c.runningTotal = new BigDecimal(3.40);
		c.startCoinPay();
		Coin coin1 = new Coin(new BigDecimal(2.00), Currency.getInstance("CAD"));
		c.pay(coin1);
		Coin coin2 = new Coin(new BigDecimal(1.00), Currency.getInstance("CAD"));
		c.pay(coin2);
		Coin coin3 = new Coin(new BigDecimal(0.25), Currency.getInstance("CAD"));
		c.pay(coin3);
		Coin coin4 = new Coin(new BigDecimal(0.10), Currency.getInstance("CAD"));
		c.pay(coin4);
		Coin coin5 = new Coin(new BigDecimal(0.05), Currency.getInstance("CAD"));
		c.pay(coin5);
		Assert.assertTrue(c.endCoinPay());
	}
	
	@Test (expected = SimulationException.class)
	public void payWithInvalidCoins() throws DisabledException{
		c.runningTotal = new BigDecimal(1.00);
		c.startCoinPay();
		c.pay(null);
		Assert.assertFalse(c.endCoinPay());
	}
	
	@Test
	public void payWithAmericanCoins() throws DisabledException{
		c.runningTotal = new BigDecimal(0.25);
		c.startCoinPay();
		Coin coin = new Coin(new BigDecimal(0.25), Currency.getInstance("USD"));
		c.pay(coin);
		Assert.assertFalse(c.endCoinPay());
	}
	
	@Test (expected = DisabledException.class)
	public void payOnDisabledMachine() throws DisabledException {
		c.runningTotal = new BigDecimal(1.00);
		c.startCoinPay();
		c.checkout.coinValidator.disable();
		Coin coin = new Coin(new BigDecimal(1.00), Currency.getInstance("CAD"));
		c.pay(coin);
		Assert.assertFalse(c.endCoinPay());
	}
	
	@Test
	public void overPay() throws DisabledException {
		c.runningTotal = new BigDecimal(1.00);
		c.startCoinPay();
		Coin coin = new Coin(new BigDecimal(2.00), Currency.getInstance("CAD"));
		c.pay(coin);
		Assert.assertTrue(c.endCoinPay());
	}
	
	@Test
	public void payOnEnabledDevice() throws DisabledException {
		c.runningTotal = new BigDecimal(1.00);
		c.startCoinPay();
		c.checkout.coinValidator.enable();
		Coin coin = new Coin(new BigDecimal(1.00), Currency.getInstance("CAD"));
		c.pay(coin);
		Assert.assertTrue(c.endCoinPay());
	}
	
	
  
}
