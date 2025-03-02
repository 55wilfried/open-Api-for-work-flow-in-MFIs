package com.microfinance.loan_services.utils;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Collection;

public class Trame {

    public static boolean isObjectEmpty(Object object) {
        if (object == null) {
            return true;
        } else if (object instanceof String) {
            if (((String) object).trim().length() == 0) {
                return true;
            }
        } else if (object instanceof Collection) {
            return isCollectionEmpty((Collection<?>) object);
        }
        return false;
    }

    private static boolean isCollectionEmpty(Collection<?> collection) {
        if (collection == null || collection.isEmpty()) {
            return true;
        }
        return false;
    }


    public class ResponseCode {
        public static final int
                SUCCESS = 200,
                SERVER_ERROR = 500,
                NOT_FOUND = 404,
                ACCESS_DENIED = 401,
                DUPLICATE_ENTRY = 401,
                BAD_REQUEST = 402,
                CONSTRAINT_ERROR = 403,

        CHANGE_PASSWORD = 4232;
    }

    public static <T> T getRequestData(String str, Class<T> t) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        return (T) gson.fromJson(str, t);
    }
}
