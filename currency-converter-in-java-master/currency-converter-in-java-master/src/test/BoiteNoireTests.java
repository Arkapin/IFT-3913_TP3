package test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import currencyConverter.Currency;
import currencyConverter.MainWindow;

import java.util.ArrayList;

public class BoiteNoireTests {

    private static final ArrayList<Currency> currencies = Currency.init();
    private static final double min = 0, max = 10000;

    private static Currency USD = null;
    private static Currency EUR = null;
    private static Currency GBP = null;
    private static Currency CHF = null;
    private static Currency CNY = null;
    private static Currency JPY = null;
    
    @BeforeAll
    static void preparation() {
    	// int index = 0;
    	// USD = currencies.get(index++);
    	// EUR = currencies.get(index++);
    	// GBP = currencies.get(index++);
    	// CHF = currencies.get(index++);
    	// CNY = currencies.get(index++);
    	// JPY = currencies.get(index);
    }
    
    @Test
    void testFromAllCurrencyToAllCurrency() {
    	for(int i = 0; i < currencies.size(); i ++) {
    		// Get current 'from' currency
    		Currency fromCurrency = currencies.get(i);
    		for(int j = 0; j < currencies.size(); j++) {
        		// Get current 'to' currency
        		Currency toCurrency = currencies.get(j);
        		double exchangeRate = fromCurrency.getExchangeValues().get(toCurrency.getShortName());
        		
        		String failureText = "Échec du test " + fromCurrency.getShortName() + " -> " + toCurrency.getShortName();
        		
        		assertEquals(exchangeRate, MainWindow.convert(fromCurrency.getName(), toCurrency.getName(), currencies, 1.0), failureText);
    		}
    	}
    }

    @Test
    void testFromInvalidCurrencyToValidCurrency() {
    	Currency c = new Currency("invalidCurrency", "inv");
    	/*for (Currency currency : currencies) {
    		c.setExchangeValues(currency.getShortName(), -1.0);
		}*/
    	
    	currencies.add(c);
    	
		for(int i = 0; i < currencies.size(); i++) {
    		//var temp = c.getExchangeValues().get(toCurrency.getShortName());
    		//double exchangeRate = temp != null ? c.getExchangeValues().get(toCurrency.getShortName()) : -1;    		
    		//String failureText = "Échec du test " + c.getShortName() + " -> " + toCurrency.getShortName();    		
    		//assertEquals(exchangeRate, MainWindow.convert(c.getName(), toCurrency.getName(), currencies, 1.0), failureText);
    		//assertth(exchangeRate, MainWindow.convert(c.getName(), toCurrency.getName(), currencies, 1.0), failureText);
    		

    		// Get current 'to' currency
    		Currency toCurrency = currencies.get(i);
    		
    		// make sure an exception is raised (int this case NullPointerException because the invalid currency doesnt have any non-null exchangeRates values)
    		Throwable thrown = assertThrows(NullPointerException.class, () -> MainWindow.convert(c.getName(), toCurrency.getName(), currencies, 1.0));
    		assertTrue(thrown.getMessage().contains("Cannot invoke \"java.lang.Double.doubleValue()\" because \"exchangeValue\" is null"));
		}
    }

    @Test
    void testMontantsAcceptés() {
    	for(int i = 0; i <= 10000; i++) {
    		// [0, 10000]
    		assertEquals(i, Currency.convert((double)i, 1.0),"[" + i + "]Échec du test");
    	}
    }
    
    @Test
    void testMontantTropBas() {
    	// Hypothèse : dans le cas où on entre une valeur incorrecte en entrée, on s'attend
    	// à recevoir 'null' en sortie
    	// try lower than minimal accepted value aka negatives
    	assertEquals(null, Currency.convert(min - 1, 1.0),"Échec, une valeur inférieure à " + min + " a été acceptée");
    }
    
    @Test
    void testMontantTropHaut() {
    	// Hypothèse : dans le cas où on entre une valeur incorrecte en entrée, on s'attend
    	// à recevoir 'null' en sortie
    	// try higher than maximal accepted value aka 10000+
    	assertEquals(null, Currency.convert(max + 1, 1.0),"Échec, une valeur supérieur à " + max + " a été acceptée");
    }

    // old tests
    {
    // @Test
    // void testFromUSD() {
    // 	for(int i = 0; i < currencies.size(); i++) {
    // 		Currency currentCurrency = currencies.get(i);
    // 		double exchangeRate = USD.getExchangeValues().get(currentCurrency.getShortName());
    // 		assertEquals(exchangeRate, MainWindow.convert(USD.getName(), currentCurrency.getName(), currencies, 1.0),"Échec du test USD -> " + currentCurrency.getShortName());
    // 	}
    // }

    // @Test
    // void testFromEUR() {
    // 	for(int i = 0; i < currencies.size(); i++) {
    // 		Currency currentCurrency = currencies.get(i);
    // 		double exchangeRate = EUR.getExchangeValues().get(currentCurrency.getShortName());
    // 		assertEquals(exchangeRate, MainWindow.convert(USD.getName(), currentCurrency.getName(), currencies, 1.0),"Échec du test USD -> " + currentCurrency.getShortName());
    // 	}
    // }
    }

}
