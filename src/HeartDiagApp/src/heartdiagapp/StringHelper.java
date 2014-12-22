package heartdiagapp;

import java.util.Vector;

public class StringHelper {

    public static Vector split(String str, char delim) {
        Vector tokens = new Vector();
        String token = "";
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == delim) {
                tokens.addElement(token);
                token = "";
            } else {
                token += str.charAt(i);
            }
        }
        if (token.length() > 0) {
            tokens.addElement(token);
        }
        return tokens;
    }
}
