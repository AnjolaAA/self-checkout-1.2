package org.lsmr.selfcheckout.software;
import org.lsmr.selfcheckout.devices.*;
import org.lsmr.selfcheckout.devices.listeners.*;
import BigDecimal;

public class ScanItemEvent implements BarcodeScannerListener {
    
    private BigDecimal price;
    
    public ScanItemEvent(){
    }

    //Enable listener
    @Override
    public void enabled(AbstractDevice<? extends AbstractDeviceListener> device) {
        // TODO Auto-generated method stub
        System.out.println("Device is enabled.");

    }

    //Disable listener
    @Override
    public void disabled(AbstractDevice<? extends AbstractDeviceListener> device) {
        // TODO Auto-generated method stub
        System.out.println("Device is disabled.");
    }

    // Notify listener that valid barcode is scanned
    // Retrieve product price, add to running total for Customer
    @Override
    public void barcodeScanned(BarcodeScanner barcodeScanner, Barcode barcode) {
        // TODO Auto-generated method stub
        try{
            BarcodeProduct product = ProductDatabases.BARCODED_PRODUCT_DATABASE.get(barcode);
            this.price = product.getPrice();
            System.out.println("Barcode Successfully scanned!");
        }catch(NullPointerException e){
            System.out.println("Product Not Found!");
        }
    }
    
    public BigDecimal getPrice(){
        return price;
    }

}
