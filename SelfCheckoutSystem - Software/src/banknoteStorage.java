import java.util.Currency;  

import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.BanknoteSlot;
import org.lsmr.selfcheckout.devices.BanknoteStorageUnit;
import org.lsmr.selfcheckout.devices.listeners.AbstractDeviceListener;
import org.lsmr.selfcheckout.devices.listeners.BanknoteSlotListener;
import org.lsmr.selfcheckout.devices.BanknoteValidator;
import org.lsmr.selfcheckout.devices.listeners.BanknoteValidatorListener;
import org.lsmr.selfcheckout.devices.listeners.BanknoteStorageUnitListener;


public class banknoteStorage implements BanknoteStorageUnitListener{
	
	private boolean fullFlag = false;
	
	@Override
	public void enabled(AbstractDevice<? extends AbstractDeviceListener> device) {
		// TODO Auto-generated method stub
		System.out.println("Device enabled.");
	}

	@Override
	public void disabled(AbstractDevice<? extends AbstractDeviceListener> device) {
		// TODO Auto-generated method stub
		System.out.println("Device disabled.");
	}

	@Override
	public void banknotesFull(BanknoteStorageUnit unit) {
		fullFlag = true;
		System.out.println("Storage capacity full");
	}

	@Override
	public void banknoteAdded(BanknoteStorageUnit unit) {
		// TODO Auto-generated method stub
		System.out.println("Banknote added");
	}

	@Override
	public void banknotesLoaded(BanknoteStorageUnit unit) {
		// TODO Auto-generated method stub
		System.out.println("Banknotes loaded");
	}

	@Override
	public void banknotesUnloaded(BanknoteStorageUnit unit) {
		System.out.println("Banknotes unloaded");
	}
	
	public boolean capacityFull() {
		if (fullFlag = true) {
			System.out.println("Banknote storage capacity reached. Banknote not accepted.");
			return true;
		} else {
			System.out.println("Banknote storage capacity not reached. Banknote accepted.");
			return false;
		}
	}
	
	
}
