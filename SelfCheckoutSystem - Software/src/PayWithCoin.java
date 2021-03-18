import java.math.BigDecimal;

import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.CoinValidator;
import org.lsmr.selfcheckout.devices.listeners.AbstractDeviceListener;
import org.lsmr.selfcheckout.devices.listeners.CoinValidatorListener;

public class PayWithCoin implements CoinValidatorListener{
	// price is the total price of the transaction, so for however many items are scanned
	private BigDecimal price;
	
	// create a new "PayWithCoin" "session" which has the price of the transaction
	public PayWithCoin(BigDecimal p) {
		this.price = p;
	}
	
	// From listener - says the device is enabled. probably have to change the parameters, I'll get around to that later
	@Override
	public void enabled(AbstractDevice<? extends AbstractDeviceListener> device) {
		// TODO Auto-generated method stub
		System.out.println("Device is enabled.");

	}

	// Same thing as above but when device is disabled.
	@Override
	public void disabled(AbstractDevice<? extends AbstractDeviceListener> device) {
		// TODO Auto-generated method stub
		System.out.println("Device is disabled.");
	}

	// from listener, when a valid coin is detected. It subtracts the value of the coin from the total cost. You want the cost to get to 0 or below.
	@Override
	public void validCoinDetected(CoinValidator validator, BigDecimal value) {
		// TODO Auto-generated method stub
		price = price.subtract(value);
	}

	// From listener, when an invalid coin is detected. It just notifies that the coin is invalid.
	@Override
	public void invalidCoinDetected(CoinValidator validator) {
		// TODO Auto-generated method stub
		System.out.println("Coin is invalid");

	}
	

	// For when finishing paying. If the value of all the coins is less than or equal to 0, 
	// everything has been paid off and the transaction is successful. Otherwise it says it is insufficient and it returns "false" to indicate the transaction isn't over.
	public boolean finishPaying() {
		if (price.doubleValue() <= 0.00) {
			System.out.println("Successful transaction!");
			return true;
		} else {
			System.out.println("Insufficient amount paid, still requires $" + price.doubleValue());
			return false;
		}
	}
	
	

}
