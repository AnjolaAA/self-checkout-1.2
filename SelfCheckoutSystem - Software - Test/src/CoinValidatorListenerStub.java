//Import statements
//Import the packages
//import java.util.Currency;

public class CoinValidatorListenerStub implements CoinValidatorListener {
    @Override
    public void enabled(AbstractDevice<? extends AbstractDeviceListener> device) {
        // TODO Auto-generated method stub
    }

    @Override
    public void disabled(AbstractDevice<? extends AbstractDeviceListener> device) {
        // TODO Auto-generated method stub
    }

    @Override
    public void validCoinDetected(CoinValidator validator, BigDecimal value); {
        // TODO Auto-generated method stub

    }
    @Override
    public void invalidCoinDetected(CoinValidator validator); {
        // TODO Auto-generated method stub
    }
}
