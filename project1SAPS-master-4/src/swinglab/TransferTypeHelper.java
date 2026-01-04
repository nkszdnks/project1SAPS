package swinglab;

import java.util.Set;

public class TransferTypeHelper {

    private static final Set<String> SEPA_COUNTRIES = Set.of(
            "AT","BE","BG","CY","CZ","DE","DK","EE","ES","FI","FR","GB",
            "GR","HR","HU","IE","IS","IT","LI","LT","LU","LV","MT","NL",
            "NO","PL","PT","RO","SE","SI","SK"
    );

    public static boolean isSepa(String iban) {
        if (iban == null || iban.length() < 2) return false;
        String countryCode = iban.substring(0,2).toUpperCase();
        return SEPA_COUNTRIES.contains(countryCode);
    }


}

