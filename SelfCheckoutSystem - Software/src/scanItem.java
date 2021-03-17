//Load the package somehow; package org.lsmr.selfcheckout;

//import org.lsmr.selfcheckout.devices.*;
//import org.lsmr.selfcheckout.devices.listeners.*;

public class scanItem implements BarcodeScannerListener {

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
    @Override
    public void barcodeScanned(BarcodeScanner barcodeScanner, Barcode barcode) {
        // TODO Auto-generated method stub
        System.out.println("Barcode is scanned.");
    }

}
