package org.lsmr.selfcheckout.software;

import java.math.BigDecimal;
import java.util.Currency;

import org.lsmr.selfcheckout.*;
import org.lsmr.selfcheckout.devices.*;

public class Customer {
    
    public int billPrice;
    private PayWithBanknote paymentBanknote;
    
    private ElectronicScale baggingScale; 
    private BagArea baggingArea;
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
        
        baggingScale = new ElectronicScale(23000,1);
        baggingScale.register(baggingArea);
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
	
//paying with banknotes
  	public void startBanknotePay() {
  		paymentBanknote = new PayWithBanknote(this.billPrice);
  		checkout.banknoteValidator.register(paymentBanknote);
  	}
  	
  	public void payBanknote (Banknote banknote) throws DisabledException, OverloadException {
  		checkout.banknoteInput.accept(banknote);
  	}
  	
  	public boolean endBanknotePay() {
  		if (paymentBanknote.payment()) {
  			System.out.println("Successful end transaction.");
  			return true;
  		}
  		return false;
  	}
  	
    
    //Barcode item price is added to running total
    public void addBarcodeItem(BarcodedItem item){
        ScanItemEvent sc = new ScanItemEvent();
        checkout.mainScanner.register(sc);
        checkout.mainScanner.scan(item);
        runningTotal = runningTotal.add(sc.getPrice());
        expectedWeight += item.getWeight(); //for checking the weight 
    }
	
	//Places Item into the Bagging Area and Checks if weight is correct
    public void placeItemInBagging(BarcodedItem item) throws Exception{
    	baggingScale.add(item);
    	try {
			if (baggingScale.getCurrentWeight() != expectedWeight) {
				throw new Exception("Item added to bagging is not the Item Scanned please try again");
				
			}
		} catch (OverloadException e) {
			//catch block
			System.out.println("Bagging Area Scale is Overloaded, Please remove the Item.");
		}
    	
    }
	
    //Removes Item from bagging area
    public void removeItemInBagging(BarcodedItem item) {
    	baggingScale.remove(item);
    }
    

  

}
