package org.lsmr.selfcheckout.software.src;

import java.math.BigDecimal;
import java.util.Currency;

import org.lsmr.selfcheckout.*;
import org.lsmr.selfcheckout.devices.*;

public class Customer {

    //should we have a class for this? Keep track of item, barcode, and running total?
    public BigDecimal runningTotal;
    //List<BarcodedItem> itemTotal;
   // BarcodeScanner scanner;
    public SelfCheckoutStation checkout;
    private PayWithCoin payment;
   
    
    public Customer () {
    	// create the needed selfcheckoutstation for all t ests
    	Currency currency = Currency.getInstance("CAD");
    	int[] bankDenom = {5, 10, 20, 50, 100};
    	BigDecimal[] coinDenom = {new BigDecimal(0.05), new BigDecimal(0.10), new BigDecimal(0.25), new BigDecimal(1.00), new BigDecimal(2.00)};
    	// Self checkout station: so far scale maximum weight + sensitivity are 100 and 1, this can be changed according to scan stuff, this is just an example.
    	checkout = new SelfCheckoutStation(currency, bankDenom, coinDenom, 100, 1);
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

    public void addItem(BarcodedItem item){
        scanner.scan(item);
    }

    public void placeItemInBagging(){

    }

    public void pay(Banknote banknote){
        payWithBanknote(this.runningTotal);
    }

}
