package org.lsmr.selfcheckout.software;

import java.util.Currency;

import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.BanknoteSlot;
import org.lsmr.selfcheckout.devices.listeners.BanknoteSlotListener;
import org.lsmr.selfcheckout.devices.BanknoteValidator;
import org.lsmr.selfcheckout.devices.listeners.BanknoteValidatorListener;

import org.lsmr.selfcheckout.devices.listeners.AbstractDeviceListener;

public class PayWithBanknote implements BanknoteValidatorListener {
	
	private int cost; //cost is the price of the transaction
	private Currency currency; //currency is currency of the banknote
	
	public PayWithBanknote(int cost) {//constructor 
		this.cost = cost;
	}
	
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
	
	@Override //overrides method from BanknoteValidatorListener, so when a valid banknote is detected, the cost will decrease
	public void validBanknoteDetected(BanknoteValidator validator, Currency currency, int value) {
		cost -= value;
	}
	
	@Override //overrides method from BanknoteValidatorListener, so when an invalid banknote is detected, notify and reject
	public void invalidBanknoteDetected(BanknoteValidator validator) {
		System.out.println("Invalid banknote detected.");
	}
	
	public boolean payment() {
		if (cost <= 0) {
			System.out.println("Transaction completed.");
			return true;
		} else {
			System.out.println("Insufficient funds provided, still owe: " + cost);
			return false; 
		}
	}

	
	
}
