package com.microfinance.transaction_services.utils;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;
@Component
public class Helpers {

    public String generateCode(String lastCodeGenerated, String format) {
        if (!Pattern.matches("(^([a-zA-Z])+\\d+([a-zA-Z]))", lastCodeGenerated)) {
            int most = Integer.parseInt(incrementStr("9", format.length()));
            if (lastCodeGenerated == null) {
                lastCodeGenerated = "";
            }
            if (lastCodeGenerated.length() != format.length()) {
                throw new IllegalArgumentException("Format must have the same length as LastGeneratedCode");
            } else if (tryParseOut(lastCodeGenerated) && most != 0) {
                return buildCode(Integer.parseInt(lastCodeGenerated), format);
            } else {
                if (most == 0) {
                    return buildCode(0, format.substring(format.length() - 2));
                }
                if (lastCodeGenerated.equals("")) {
                    return format;
                }
                if (lastCodeGenerated.length() <= 1 || !tryParseOut(lastCodeGenerated.substring(1))) {
                    return "";
                }
                int first = Integer.parseInt(lastCodeGenerated.substring(1));
                char c = lastCodeGenerated.substring(0, 1).charAt(0);
                if (first != Integer.parseInt(buildCode(9, format.substring(format.length() - 2)))) {
                    return String.valueOf(c) + first;
                }
                return ((char) (c + 1)) + buildCode(0, format.substring(format.length() - 2));
            }
        } else {
            throw new IllegalArgumentException("Unusual LastGeneratedCode. This code was not generated using this algorithm and cannot be handled.");
        }
    }


    private String incrementStr(String str, int length) {
        StringBuilder holder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            holder.append(str);
        }
        return holder.toString();
    }

    private boolean tryParseOut(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private String buildCode(int num, String format) {
        int newNum = num + 1;
        return format.substring(String.valueOf(newNum).length()) + newNum;
    }
}
