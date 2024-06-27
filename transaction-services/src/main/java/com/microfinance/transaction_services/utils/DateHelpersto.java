package com.microfinance.transaction_services.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelpersto {

    public static Date stringToOpDate(String source) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(source);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
