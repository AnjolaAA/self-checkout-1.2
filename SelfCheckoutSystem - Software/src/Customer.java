package org.lsmr.selfcheckout.software.src;

import java.math.BigDecimal;
import java.util.Currency;

import org.lsmr.selfcheckout.*;
import org.lsmr.selfcheckout.devices.*;

public class Customer {

    public BigDecimal runningTotal;
    public SelfCheckoutStation checkout;
    public double expectedWeight;
    private PayWithCoin payment;
   
    
    public Customer () {
    	// create the needed selfcheckoutstation for all tests
    	Currency currency = Currency.getInstance("CAD");
    	int[] bankDenom = {5, 10, 20, 50, 100};
    	BigDecimal[] coinDenom = {new BigDecimal(0.05), new BigDecimal(0.10), new BigDecimal(0.25), new BigDecimal(1.00), new BigDecimal(2.00)};
    	
        // Self checkout station: so far scale maximum weight + sensitivity are 23000g and 10g, this can be changed as necessary, this is just an example.
    	checkout = new SelfCheckoutStation(currency, bankDenom, coinDenom, 23000, 10);
        
        this.expectedWeight = 0;
        this.runningTotal = new BigDecimal(0.000);
    }

//indicating that payment has started with coins
   public void startCoinPay(){
       payment = new PayWithCoin(this.runningTotal);
       //register as a listener
       checkout.coinValidator.register(payment);

    }
  
  //paying with coins
  	public void pay (Coin coin) throws DisabledException {
  		// mimics customer paying with a coin
  		checkout.coinSlot.accept(coin);
  	}
  
   //ending payment with coins, checks to see if it's valid.
  	public boolean endCoinPay() {
  		if (payment.finishPaying()) {
  			System.out.println("Successfully end transaction");
  			return true;
  		}
  		return false;
  	}
    
    //Barcode item price is added to running total
    public void addBarcodeItem(BarcodedItem item){
        scannedItemEvent sc = new ScanItemEvemt();
        checkout.mainScanner.register(sc);
        checkout.mainScanner.scan(item);
        runningTotal = runningTotal.add(sc.getPrice());
        expectedWeight += item.getWeight(); //for checking the weight 
    }

    public void placeItemInBagging(){

    }

    public void pay(Banknote banknote){
        payWithBanknote(this.runningTotal);
    }

}
