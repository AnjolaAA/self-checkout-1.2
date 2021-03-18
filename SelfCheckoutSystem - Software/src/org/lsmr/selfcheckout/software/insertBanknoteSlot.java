package org.lsmr.selfcheckout.software;

import java.util.Currency;

import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.BanknoteSlot;
import org.lsmr.selfcheckout.devices.listeners.BanknoteSlotListener;
import org.lsmr.selfcheckout.devices.BanknoteValidator;
import org.lsmr.selfcheckout.devices.listeners.BanknoteValidatorListener;

import org.lsmr.selfcheckout.devices.listeners.AbstractDeviceListener;

public class insertBanknoteSlot implements BanknoteSlotListener {
	
	private boolean ejectFlag = false; //flag signifies if banknote is ejected
	
	public insertBanknoteSlot(boolean ejectFlag) { //constructor that identifies if a banknote has been inserted, rejected or removed
		this.ejectFlag = ejectFlag;
	}

	@Override //enables device
	public void enabled(AbstractDevice<? extends AbstractDeviceListener> device) {
		// TODO Auto-generated method stub
		System.out.println("Banknote slot enabled.");
	}

	@Override //disables device
	public void disabled(AbstractDevice<? extends AbstractDeviceListener> device) {
		// TODO Auto-generated method stub
		System.out.println("Banknote slot disabled.");
	}

	@Override //overrides so system returns a message when banknote is inserted
	public void banknoteInserted(BanknoteSlot slot) {
		// TODO Auto-generated method stub
		System.out.println("Banknote was inserted.");
	}

	@Override //overrides so system returns an ejection message when Banknote is ejected
	public void banknoteEjected(BanknoteSlot slot) {
		// TODO Auto-generated method stub
		System.out.println("Banknote was ejected.");
		ejectFlag = true;
	}

	@Override
	public void banknoteRemoved(BanknoteSlot slot) {
		// TODO Auto-generated method stub
		System.out.println("Banknote was removed");
	}
	
	public boolean returnBanknote() {
		if (ejectFlag == true) {
			System.out.println("Banknote ejected, cost remains the same");
			return true;
		} else {
			System.out.println("Banknote accepted, cost goes down");
			return false;
		}
	}

}
