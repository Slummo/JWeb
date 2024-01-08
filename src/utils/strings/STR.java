package utils.strings;

public class STR {
    public static String difference(String str1, String str2) {
        int minLength = Math.min(str1.length(), str2.length());
        int diffIndex = -1;

        for (int i = 0; i < minLength; i++) {
            if (str1.charAt(i) != str2.charAt(i)) {
                diffIndex = i;
                break;
            }
        }

        if (diffIndex == -1) {
            // If one string is a prefix of the other, return the longer substring
            if (str1.length() > minLength) {
                return str1.substring(minLength);
            } else if (str2.length() > minLength) {
                return str2.substring(minLength);
            }
        } else {
            // Return the substring from the differing index to the end
            return str1.substring(diffIndex);
        }

        // Strings are identical
        return "";
    }
}
