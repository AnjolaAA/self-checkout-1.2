package org.lsmr.selfcheckout.software;

import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.ElectronicScale;
import org.lsmr.selfcheckout.devices.listeners.AbstractDeviceListener;
import org.lsmr.selfcheckout.devices.listeners.ElectronicScaleListener;

public class BagArea implements ElectronicScaleListener{
	private double totalWeight;
	private boolean overloaded;
	private boolean status;

	@Override
	public void enabled(AbstractDevice<? extends AbstractDeviceListener> device) {
		// TODO Auto-generated method stub
		status = true;
		System.out.println("Bagging Area Scale is enabled.");
	}

	@Override
	public void disabled(AbstractDevice<? extends AbstractDeviceListener> device) {
		// TODO Auto-generated method stub
		status = false;
		System.out.println("Bagging Area Scale is disabled.");
	}

	@Override
	public void weightChanged(ElectronicScale scale, double weightInGrams) {
		// TODO Auto-generated method stub
		totalWeight = weightInGrams;
		
		System.out.println("Bagging Area Scale weight updated.");
	}

	@Override
	public void overload(ElectronicScale scale) {
		// TODO Auto-generated method stub
		overloaded = true;
		System.out.println("Bagging Area Scale is Overloaded, Please remove an Item.");
		
	}

	@Override
	public void outOfOverload(ElectronicScale scale) {
		// TODO Auto-generated method stub
		overloaded = false;
		System.out.println("Bagging Area Scale is no longer Overloaded.");
	}

}
