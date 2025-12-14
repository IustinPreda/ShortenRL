package org.shorty.shortenrl.util;


import org.springframework.stereotype.Component;

@Component
public class Base62Encoder {

    private static final String alp = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int base = alp.length();

    public String encode(long value) {
        StringBuilder sb = new StringBuilder();
        if (value == 0) {
            return String.valueOf(alp.charAt(0));
        }

        while (value > 0) {
            int re = (int) (value % base);
            sb.append(alp.charAt(re));
            value /= base;
        }

        return sb.reverse().toString();
    }

    public long decode(String str) {
        long result = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            int value = alp.indexOf(c);
            result = result * base + value;
        }
        return result;
    }

}
