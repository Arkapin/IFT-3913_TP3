package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import currencyConverter.Currency;
import currencyConverter.MainWindow;

import java.util.ArrayList;

public class BoiteNoireTests {

    private static ArrayList<Currency> currencies;
    private static final double min = 0, max = 10000;
    
    @BeforeAll
    static void preparation() {
    	currencies = Currency.init();
    }
    
    @AfterEach
    void clean() {
    	// reset the currencies arrayList
        currencies = Currency.init();
    }
    
    @Test
    void testFromAllValidCurrencyToAllValidCurrency() {
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
    	currencies.add(c);
    	
		for(int i = 0; i < currencies.size(); i++) {
    		// Get current 'to' currency
    		Currency toCurrency = currencies.get(i);
    		
    		// make sure an exception is raised (int this case NullPointerException because the invalid currency doesnt have any non-null exchangeRates values)
    		Throwable thrown = assertThrows(NullPointerException.class, () -> MainWindow.convert(c.getName(), toCurrency.getName(), currencies, 1.0));
    		assertTrue(thrown.getMessage().contains("Cannot invoke \"java.lang.Double.doubleValue()\" because \"exchangeValue\" is null"));
		}
    }

    @Test
    void testFromValidCurrencyToInvalidCurrency() {
    	Currency c = new Currency("invalidCurrency", "inv");
    	currencies.add(c);
    	
		for(int i = 0; i < currencies.size(); i++) {
    		// Get current 'to' currency
    		Currency toCurrency = currencies.get(i);
    		
    		// make sure an exception is raised (int this case NullPointerException because the invalid currency doesnt have any non-null exchangeRates values)
    		Throwable thrown = assertThrows(NullPointerException.class, () -> MainWindow.convert(toCurrency.getName(), c.getName(), currencies, 1.0));
    		assertTrue(thrown.getMessage().contains("Cannot invoke \"java.lang.Double.doubleValue()\" because \"exchangeValue\" is null"));
		}
    }
    
    @Test
    void testFromInvalidCurrencyToInvalidCurrency() {
    	currencies.clear();
    	
    	Currency c = new Currency("invalidCurrency1", "inv1");
    	currencies.add(c);
    	
    	Currency cc = new Currency("invalidCurrency2", "inv2");
    	currencies.add(cc);
		
		// c -> c
		Throwable thrown = assertThrows(NullPointerException.class, () -> MainWindow.convert(c.getName(), c.getName(), currencies, 1.0));
		assertTrue(thrown.getMessage().contains("Cannot invoke \"java.lang.Double.doubleValue()\" because \"exchangeValue\" is null"));

		// c -> cc
		thrown = assertThrows(NullPointerException.class, () -> MainWindow.convert(c.getName(), cc.getName(), currencies, 1.0));
		assertTrue(thrown.getMessage().contains("Cannot invoke \"java.lang.Double.doubleValue()\" because \"exchangeValue\" is null"));

		// cc -> cc
		thrown = assertThrows(NullPointerException.class, () -> MainWindow.convert(cc.getName(), cc.getName(), currencies, 1.0));
		assertTrue(thrown.getMessage().contains("Cannot invoke \"java.lang.Double.doubleValue()\" because \"exchangeValue\" is null"));

		// cc -> c
		thrown = assertThrows(NullPointerException.class, () -> MainWindow.convert(cc.getName(), c.getName(), currencies, 1.0));
		assertTrue(thrown.getMessage().contains("Cannot invoke \"java.lang.Double.doubleValue()\" because \"exchangeValue\" is null"));
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
}
