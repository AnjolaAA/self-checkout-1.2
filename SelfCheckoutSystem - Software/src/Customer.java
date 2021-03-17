import java.math.BigDecimal;
import java.util.List;

import org.lsmr.selfcheckout.Banknote;
import org.lsmr.selfcheckout.BarcodedItem;
import org.lsmr.selfcheckout.Coin;
import org.lsmr.selfcheckout.devices.BarcodeScanner;
import org.lsmr.selfcheckout.devices.ElectronicScale;

//Load the package somehow; package SelfCheckoutSystem - Hardware - v1.2\org.lsmr.selfcheckout;

//import org.lsmr.selfcheckout.devices.*;
//import org.lsmr.selfcheckout.devices.listeners.*;

public class Customer {

    //should we have a class for this? Keep track of item, barcode, and running total?
    BigDecimal runningTotal;
    List<BarcodedItem> itemTotal;
    BarcodeScanner scanner;
    BagArea baggingArea;

    public static void main(String args[]){
        System.out.println("Hello World");
    }

    public void addItem(BarcodedItem item){
        scanner.scan(item);
    }

    public void placeItemInBagging(BarcodedItem item){
    	//bagScale.add(item);
    }

    public void pay(Coin coin){
        //payWithCoin(this.runningTotal);

    }

    public void pay(Banknote banknote){
        //payWithBanknote(this.runningTotal);
    }

}