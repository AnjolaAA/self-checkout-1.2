//Import statements
//Import the packages
//import java.util.Currency;

public class BanknoteValidatorListenerStub implements BanknoteValidatorListener {
    @Override
    public void enabled(AbstractDevice<? extends AbstractDeviceListener> device) {
        // TODO Auto-generated method stub

    }

    @Override
    public void disabled(AbstractDevice<? extends AbstractDeviceListener> device) {
        // TODO Auto-generated method stub

    }

    @Override
    public void validBanknoteDetected(BanknoteValidator validator, Currency currency, int value); {
        // TODO Auto-generated method stub

    }
    @Override
    public void invalidBanknoteDetected(BanknoteValidator validator); {
        // TODO Auto-generated method stub
    }
}
