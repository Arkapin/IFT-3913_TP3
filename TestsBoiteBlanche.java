package test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import currencyConverter.Currency;
import currencyConverter.MainWindow;

import java.util.ArrayList;

public class TestsBoiteBlanche {

    private final ArrayList<Currency> currencies1Chemins = new ArrayList<>();

    private final ArrayList<Currency> currenciesIChemins = Currency.init();

    @Test
    void test1Chemins() {
        Currency currency1Chemin = new Currency("US Dollar", "USD");
        currency1Chemin.defaultValues();
        currencies1Chemins.add(currency1Chemin);

        assertAll("1-chemin",
                () -> assertEquals(100.0, MainWindow.convert("US Dollar", "US Dollar", currencies1Chemins, 100.0),
                        "Échec du cas 1 pour 1-chemins"),
                () -> assertEquals(0.0, MainWindow.convert("NULL", "US Dollar", currencies1Chemins, 100.0),
                        "Échec du cas 2 pour 1-chemins"),
                () -> {
                    currency1Chemin.setShortName(null);
                    currencies1Chemins.set(0, currency1Chemin);
                    assertEquals(0.0, MainWindow.convert("US Dollar", "US Dollar", currencies1Chemins, 100.0),
                            "Échec du cas 3 pour 1-chemins");
                },
                () -> {
                    currency1Chemin.setShortName("USD");
                    currencies1Chemins.set(0, currency1Chemin);
                    assertEquals(0.0, MainWindow.convert("US Dollar", "NULL", currencies1Chemins, 100.0),
                            "Échec du cas 4 pour 1-chemins");
                }
        );
    }

    @Test
    void testIChemins() {

    }

}
