package org.lsmr.selfcheckout.software.test;

import java.math.BigDecimal;
import java.util.Currency;

import org.junit.*;
import org.lsmr.selfcheckout.software.src.Customer;
import org.lsmr.selfcheckout.*;
import org.lsmr.selfcheckout.devices.*;
import org.lsmr.selfcheckout.products.*;
import org.lsmr.selfcheckout.external.*;

public class SoftwareTest {
	public Customer c;
	@Before
	public void startUp() {
		c = new Customer();
	}
	   
    @Test
    //Scan testing
    public void scanValidItem(){
        Barcode bCode = new Barcode("1234567890");
        BarcodedProduct prod = new BarcodedProduct(bCode, "A red apple", new BigDecimal(8.99));
        BarcodedItem item = new BarcodedItem(bCode, 0.77);
        ProductDatabases.BARCODED_PRODUCT_DATABASE.put(bCode, prod);
        boolean failedScans = true;
        while (failedScans) {
        	try {
                c.addBarcodeItem(item);
                failedScans = false;
            } catch (Exception e) {
            	c.runningTotal = new BigDecimal(0);
            	failedScans = true;
            }
        }
        System.out.println(c.runningTotal);
        Assert.assertTrue(c.runningTotal.equals(new BigDecimal(8.99)));
    }
    
    @Test(expected = NullPointerException.class)
    public void scanInvalidItem(){
        Barcode bCode = new Barcode("01234567");
        BarcodedItem item = new BarcodedItem(bCode, 0.77);
        c.addBarcodeItem(item);
    }
    
    @Test
    public void checkRunningTotal(){
        Barcode bCode1 = new Barcode("00000000000");
        Barcode bCode2 = new Barcode("11111111111");

        BarcodedItem item1 = new BarcodedItem(bCode1, 0.33);
        BarcodedItem item2 = new BarcodedItem(bCode2, 0.33);

        BarcodedProduct prod1 = new BarcodedProduct(bCode1, "A red apple", new BigDecimal("9"));
        BarcodedProduct prod2 = new BarcodedProduct(bCode2, "A small apple", new BigDecimal("1"));
        ProductDatabases.BARCODED_PRODUCT_DATABASE.put(bCode1, prod1);
        ProductDatabases.BARCODED_PRODUCT_DATABASE.put(bCode2, prod2);
        boolean failedScans = true;
        while (failedScans) {
        	try {
                c.addBarcodeItem(item1);
                c.addBarcodeItem(item2);
                failedScans = false;
            } catch (Exception e) {
            	c.runningTotal = new BigDecimal(0);
            	failedScans = true;
            }
        }

        //Running total should be 10
        Assert.assertTrue(c.runningTotal.equals(new BigDecimal(10)));
    }
    
    @Test
    public void checkWeightTotal(){
        Barcode bCode1 = new Barcode("00");
        Barcode bCode2 = new Barcode("11");

        BarcodedItem item1 = new BarcodedItem(bCode1, 0.77);
        BarcodedItem item2 = new BarcodedItem(bCode2, 0.33);

        BarcodedProduct prod1 = new BarcodedProduct(bCode1, "A red apple", new BigDecimal("9"));
        BarcodedProduct prod2 = new BarcodedProduct(bCode2, "A small apple", new BigDecimal("1"));
        ProductDatabases.BARCODED_PRODUCT_DATABASE.put(bCode1, prod1);
        ProductDatabases.BARCODED_PRODUCT_DATABASE.put(bCode2, prod2);
        boolean failedScans = true;
        while (failedScans) {
        	try {
                c.addBarcodeItem(item1);
                c.addBarcodeItem(item2);
                failedScans = false;
            } catch (Exception e) {
            	c.expectedWeight = 0.0;
            	failedScans = true;
            }
        }
        
        //expectedWeight = 1.10
        Assert.assertTrue(c.expectedWeight == 1.10);
    }
	//Testing Coin Payment
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
	
	//BANKNOTE TESTING PAYMENT
	@Test
	public void testBanknotePay() throws DisabledException, OverloadException{
		c.billPrice = 10;
		c.startBanknotePay();
		Banknote banknote = new Banknote(10, Currency.getInstance("CAD"));
		c.payBanknote(banknote);
		Assert.assertTrue(c.endBanknotePay());
	}
	
	@Test
	public void testPayingInsufficientBanknotes() throws DisabledException, OverloadException {
		c.billPrice = 20;
		c.startBanknotePay();
		Banknote banknote = new Banknote(10, Currency.getInstance("CAD"));
		c.payBanknote(banknote);
		Assert.assertFalse(c.endBanknotePay());
	}
	
	@Test
	public void testPayingWithAllBanknotes() throws DisabledException, OverloadException{
		c.billPrice = 25;
		c.startBanknotePay();
		Banknote banknote1 = new Banknote(5, Currency.getInstance("CAD"));
		c.payBanknote(banknote1);
		Banknote banknote2 = new Banknote(10, Currency.getInstance("CAD"));
		c.payBanknote(banknote2);
		Banknote banknote3 = new Banknote(10, Currency.getInstance("CAD"));
		c.payBanknote(banknote3);
		Assert.assertTrue(c.endBanknotePay());
	}
	
	@Test (expected = SimulationException.class)
	public void payWithInvalidBanknotes() throws DisabledException, OverloadException{
		c.billPrice = 10;
		c.startBanknotePay();
		c.payBanknote(null);
		Assert.assertFalse(c.endBanknotePay());
	}
	
	@Test
	public void payWithAmericanBanknotes() throws DisabledException, OverloadException{
		c.billPrice = 25;
		c.startBanknotePay();
		Banknote banknote = new Banknote(25, Currency.getInstance("USD"));
		c.payBanknote(banknote);
		Assert.assertFalse(c.endBanknotePay());
	}
	
	@Test (expected = DisabledException.class)
	public void testPayOnDisabledMachineBanknote() throws DisabledException, OverloadException {
		c.billPrice = 10;
		c.startBanknotePay();
		c.checkout.banknoteValidator.disable();
		Banknote banknote = new Banknote(10, Currency.getInstance("CAD"));
		c.payBanknote(banknote);
		Assert.assertFalse(c.endBanknotePay());
	}
	
	@Test
	public void testOverPayingBanknote() throws DisabledException, OverloadException {
		c.billPrice = 10;
		c.startBanknotePay();
		Banknote banknote = new Banknote(20, Currency.getInstance("CAD"));
		c.payBanknote(banknote);
		Assert.assertTrue(c.endBanknotePay());
	}
	
	@Test
	public void testPayOnEnabledDeviceBanknote() throws DisabledException, OverloadException {
		c.billPrice = 10;
		c.startBanknotePay();
		c.checkout.banknoteValidator.enable();
		Banknote banknote = new Banknote(10, Currency.getInstance("CAD"));
		c.payBanknote(banknote);
		Assert.assertTrue(c.endBanknotePay());
	}
	
  
}
