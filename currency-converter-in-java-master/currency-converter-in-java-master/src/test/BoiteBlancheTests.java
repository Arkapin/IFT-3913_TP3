package test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import currencyConverter.Currency;
import currencyConverter.MainWindow;

import java.util.ArrayList;
import java.util.HashMap;

public class BoiteBlancheTests {

    private static final ArrayList<Currency> currenciesIChemins = Currency.init();

    @BeforeAll
    static void preparation() {
        Currency usDollar = currenciesIChemins.get(0);
        Currency currency7Chemin = new Currency("Unique", "UNQ");
        usDollar.setExchangeValues("UNQ", 2.00);
        currenciesIChemins.set(0, usDollar);
        currenciesIChemins.add(currency7Chemin);
    }

    @Test
    void testIChemins() {
        Currency usDollar = currenciesIChemins.get(0);

        assertAll("I-chemins",
                () -> testIChemin(100.0*usDollar.getExchangeValues().get(currenciesIChemins.get(currenciesIChemins.size()-1).getShortName())),
                () -> {currenciesIChemins.remove(currenciesIChemins.size()-1);
                testIChemin(100.0*usDollar.getExchangeValues().get(currenciesIChemins.get(currenciesIChemins.size()-1).getShortName()));
                },
                () -> {currenciesIChemins.remove(currenciesIChemins.size()-1);
                    testIChemin(100.0*usDollar.getExchangeValues().get(currenciesIChemins.get(currenciesIChemins.size()-1).getShortName()));
                },
                () -> {currenciesIChemins.remove(currenciesIChemins.size()-1);
                    testIChemin(100.0*usDollar.getExchangeValues().get(currenciesIChemins.get(currenciesIChemins.size()-1).getShortName()));
                },
                () -> {currenciesIChemins.remove(currenciesIChemins.size()-1);
                    testIChemin(100.0*usDollar.getExchangeValues().get(currenciesIChemins.get(currenciesIChemins.size()-1).getShortName()));
                },
                () -> {currenciesIChemins.remove(currenciesIChemins.size()-1);
                    testIChemin(100.0*usDollar.getExchangeValues().get(currenciesIChemins.get(currenciesIChemins.size()-1).getShortName()));
                },
                () -> {currenciesIChemins.remove(currenciesIChemins.size()-1);
                    testIChemin(100.0*usDollar.getExchangeValues().get(currenciesIChemins.get(currenciesIChemins.size()-1).getShortName()));
                },
                () -> {currenciesIChemins.remove(currenciesIChemins.size()-1); testSkipBoucle();});
    }

    void testIChemin(Double expected) {
        Currency currencyIChemin = currenciesIChemins.get(currenciesIChemins.size()-1);
        String name = currencyIChemin.getName(),
                shortName = currencyIChemin.getShortName();

        assertAll(currenciesIChemins.size()+"-chemin",
                () -> assertEquals(expected, MainWindow.convert("US Dollar", name, currenciesIChemins, 100.0),
                        "Échec du cas 1 pour "+currenciesIChemins.size()+" itérations de boucle"),
                () -> assertEquals(0.0, MainWindow.convert("NULL", name, currenciesIChemins, 100.0),
                        "Échec du cas 2 pour "+currenciesIChemins.size()+" itérations de boucle"),
                () -> {
                    currencyIChemin.setShortName(null);
                    currenciesIChemins.set(currenciesIChemins.size()-1, currencyIChemin);
                    assertEquals(0.0, MainWindow.convert("US Dollar", name, currenciesIChemins, 100.0),
                            "Échec du cas 3 pour "+currenciesIChemins.size()+" itérations de boucle");
                },
                () -> {
                    currencyIChemin.setShortName(shortName);
                    currenciesIChemins.set(currenciesIChemins.size()-1, currencyIChemin);
                    assertEquals(0.0, MainWindow.convert("US Dollar", "NULL", currenciesIChemins, 100.0),
                            "Échec du cas 4 pour "+currenciesIChemins.size()+" itérations de boucle");
                }
        );
    }

    void testSkipBoucle() {
        assertEquals(0.0, MainWindow.convert("US Dollar", "US Dollar", currenciesIChemins, 100.0),
                "Échec du test pour aucune itération de boucle");
    }

}
