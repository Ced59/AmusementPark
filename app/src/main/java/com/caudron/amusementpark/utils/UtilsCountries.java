package com.caudron.amusementpark.utils;

import android.content.Context;

import com.caudron.amusementpark.models.entities.Country;

import java.util.List;

public class UtilsCountries {

    public static void fillCountryCode(Country country) {
            String name = country.getName();
            switch (name.toLowerCase()) {
                case "france":
                    country.setCountryCode("FR");
                    break;
                case "spain":
                    country.setCountryCode("ES");
                    break;
                case "belgium":
                    country.setCountryCode("BE");
                    break;
                case "uk":
                    country.setCountryCode("GB");
                    break;
                case "usa":
                    country.setCountryCode("US");
                    break;
                case "germany":
                    country.setCountryCode("DE");
                    break;
                case "italy":
                    country.setCountryCode("IT");
                    break;
                case "taiwan":
                    country.setCountryCode("TW");
                    break;
                case "japan":
                    country.setCountryCode("JP");
                    break;
                case "australia":
                    country.setCountryCode("AU");
                    break;
                case "netherlands":
                    country.setCountryCode("NL");
                    break;
                case "russia":
                    country.setCountryCode("RU");
                    break;
                case "denmark":
                    country.setCountryCode("DK");
                    break;
                case "finland":
                    country.setCountryCode("FI");
                    break;
                case "sweden":
                    country.setCountryCode("SE");
                    break;
                case "malaysia":
                    country.setCountryCode("MY");
                    break;
                case "china":
                    country.setCountryCode("CN");
                    break;
                case "guatemala":
                    country.setCountryCode("GT");
                    break;
                case "ukraine":
                    country.setCountryCode("UA");
                    break;
                case "canada":
                    country.setCountryCode("CA");
                    break;
                case "portugal":
                    country.setCountryCode("PT");
                    break;
                case "brazil":
                    country.setCountryCode("BR");
                    break;
                case "lebanon":
                    country.setCountryCode("LB");
                    break;
                case "na":
                    country.setCountryCode("NA");
                    break;
                case "austria":
                    country.setCountryCode("AT");
                    break;
                case "southkorea":
                    country.setCountryCode("KR");
                    break;
                case "uae":
                    country.setCountryCode("AE");
                    break;
                case "mexico":
                    country.setCountryCode("MX");
                    break;
                case "vietnam":
                    country.setCountryCode("VN");
                    break;
                case "newzealand":
                    country.setCountryCode("NZ");
                    break;
                case "croatia":
                    country.setCountryCode("HR");
                    break;
                case "qatar":
                    country.setCountryCode("QA");
                    break;
                case "poland":
                    country.setCountryCode("PL");
                    break;
                case "india":
                    country.setCountryCode("IN");
                    break;
                case "indonesia":
                    country.setCountryCode("ID");
                    break;
                case "argentina":
                    country.setCountryCode("AR");
                    break;
                case "iraq":
                    country.setCountryCode("IQ");
                    break;
                case "israel":
                    country.setCountryCode("IL");
                    break;
                case "peru":
                    country.setCountryCode("PE");
                    break;
                case "norway":
                    country.setCountryCode("NO");
                    break;
                case "turkey":
                    country.setCountryCode("TR");
                    break;
                case "slovakia":
                    country.setCountryCode("SK");
                    break;
                case "iran":
                    country.setCountryCode("IR");
                    break;
                case "serbia":
                    country.setCountryCode("RS");
                    break;
                case "southafrica":
                    country.setCountryCode("ZA");
                    break;
                case "lithuania":
                    country.setCountryCode("LT");
                    break;
                case "slovenia":
                    country.setCountryCode("SI");
                    break;
                case "thailand":
                    country.setCountryCode("TH");
                    break;
                case "belarus":
                    country.setCountryCode("BY");
                    break;
                case "pakistan":
                    country.setCountryCode("PK");
                    break;
                case "uzbekistan":
                    country.setCountryCode("UZ");
                    break;
                case "mongolia":
                    country.setCountryCode("MN");
                    break;
                case "singapore":
                    country.setCountryCode("SG");
                    break;
                case "tunisia":
                    country.setCountryCode("TN");
                    break;
                case "north korea":
                    country.setCountryCode("KP");
                    break;
                case "philippines":
                    country.setCountryCode("PH");
                    break;
                case "palestine":
                    country.setCountryCode("PS");
                    break;
                case "montenegro":
                    country.setCountryCode("ME");
                    break;
                case "kazakhstan":
                    country.setCountryCode("KZ");
                    break;
                case "burma":
                    country.setCountryCode("MM");
                    break;
                case "greece":
                    country.setCountryCode("GR");
                    break;
                case "ireland":
                    country.setCountryCode("IE");
                    break;
                case "bulgaria":
                    country.setCountryCode("BG");
                    break;
                case "colombia":
                    country.setCountryCode("CO");
                    break;
                case "moldova":
                    country.setCountryCode("MD");
                    break;
                case "switzerland":
                    country.setCountryCode("CH");
                    break;
                case "czech":
                    country.setCountryCode("CZ");
                    break;
                case "oman":
                    country.setCountryCode("OM");
                    break;
                case "andorra":
                    country.setCountryCode("AD");
                    break;
                case "bosnia and herzegovina":
                    country.setCountryCode("BA");
                    break;
                case "saudi arabia":
                    country.setCountryCode("SA");
                    break;
                case "cyprus":
                    country.setCountryCode("CY");
                    break;
                case "kosovo":
                    country.setCountryCode("XK");
                    break;
                case "north macedonia":
                    country.setCountryCode("MK");
                    break;
                case "egypt":
                    country.setCountryCode("EG");
                    break;
                case "latvia":
                    country.setCountryCode("LV");
                    break;
                case "romania":
                    country.setCountryCode("RO");
                    break;
                case "luxembourg":
                    country.setCountryCode("LU");
                    break;
                case "jamaica":
                    country.setCountryCode("JM");
                    break;
                case "malta":
                    country.setCountryCode("MT");
                    break;
                case "estonia":
                    country.setCountryCode("EE");
                    break;
                case "armenia":
                    country.setCountryCode("AM");
                    break;
                case "albania":
                    country.setCountryCode("AL");
                    break;
                case "hungary":
                    country.setCountryCode("HU");
                    break;
                default:
                    country.setCountryCode("");
                    break;

        }
    }

    public static String getLocaleCountryName(Context context, String countryCode){
        int resourceId = context.getResources().getIdentifier("country_" + countryCode.toLowerCase(), "string", context.getPackageName());
        if (resourceId != 0) {
            return context.getString(resourceId);
        } else {
            return countryCode;
        }
    }
}
