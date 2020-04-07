package com.ornettech.nmmcqcandbirthdaywishes.utility;

/**
 * Created by New on 12/02/2017.
 */

public class Transalator {
    public static String marDigitToEngDigit(String str) {
        String s = str;
        if (s.contains("१")) {
            s = s.replace("१", "1");
        }
        if (s.contains("२")) {
            s = s.replace("२", "2");
        }
        if (s.contains("३")) {
            s = s.replace("३", "3");
        }
        if (s.contains("४")) {
            s = s.replace("४", "4");
        }
        if (s.contains("५")) {
            s = s.replace("५", "5");
        }
        if (s.contains("६")) {
            s = s.replace("६", "6");
        }
        if (s.contains("७")) {
            s = s.replace("७", "7");
        }
        if (s.contains("८")) {
            s = s.replace("८", "8");
        }
        if (s.contains("९")) {
            s = s.replace("९", "9");
        }
        if (s.contains("०")) {
            s = s.replace("०", "0");
        }
        return s;
    }

    public static String engLetterToMarLetter(String str) {
        String s = str;
        if (s.contains("A")) {
            s = s.replace("A", "अ");
        }
        if (s.contains("B")) {
            s = s.replace("B", "ब");
        }
        if (s.contains("C")) {
            s = s.replace("C", "क");
        }
        if (s.contains("D")) {
            s = s.replace("D", "ड");
        }
        return s;
    }

    public static String englishDigitToMarathiDigit(String str) {
        String s = str;

        if (s.contains("1")) {
            s = s.replace("1", "१");
        }
        if (s.contains("2")) {
            s = s.replace("2", "२");
        }
        if (s.contains("3")) {
            s = s.replace("3", "३");
        }
        if (s.contains("4")) {
            s = s.replace("4", "४");
        }
        if (s.contains("5")) {
            s = s.replace("5", "५");
        }
        if (s.contains("6")) {
            s = s.replace("6", "६");
        }
        if (s.contains("7")) {
            s = s.replace("7", "७");
        }
        if (s.contains("8")) {
            s = s.replace("8", "८");
        }
        if (s.contains("9")) {
            s = s.replace("9", "९");
        }
        if (s.contains("0")) {
            s = s.replace("0", "०");
        }


        return s;


    }

    public static String convertIntMonthIntoName(String day) {
        String month = "";
        if (day.equalsIgnoreCase("1") || day.equalsIgnoreCase("01")) {
            month = "JANUARY";
        } else if (day.equalsIgnoreCase("2") || day.equalsIgnoreCase("02")) {
            month = "FEBRUARY";
        } else if (day.equalsIgnoreCase("3") || day.equalsIgnoreCase("03")) {
            month = "MARCH";
        } else if (day.equalsIgnoreCase("4") || day.equalsIgnoreCase("04")) {
            month = "APRIL";
        } else if (day.equalsIgnoreCase("5") || day.equalsIgnoreCase("05")) {
            month = "MAY";
        } else if (day.equalsIgnoreCase("6") || day.equalsIgnoreCase("06")) {
            month = "JUNE";
        } else if (day.equalsIgnoreCase("7") || day.equalsIgnoreCase("07")) {
            month = "JULY";
        } else if (day.equalsIgnoreCase("8") || day.equalsIgnoreCase("08")) {
            month = "AUGUST";
        } else if (day.equalsIgnoreCase("9") || day.equalsIgnoreCase("09")) {
            month = "SEPTEMBER";
        } else if (day.equalsIgnoreCase("10")) {
            month = "OCTOBER";
        } else if (day.equalsIgnoreCase("11")) {
            month = "NOVEMBER";
        } else if (day.equalsIgnoreCase("12")) {
            month = "DECEMBER";
        }

        return month;
    }

    public static String convertStringFirstCharToCapital(String str)
    {

        // Create a char array of given String
        char ch[] = str.toCharArray();
        for (int i = 0; i < str.length(); i++) {

            // If first character of a word is found
            if (i == 0 && ch[i] != ' ' ||
                    ch[i] != ' ' && ch[i - 1] == ' ') {

                // If it is in lower-case
                if (ch[i] >= 'a' && ch[i] <= 'z') {

                    // Convert into Upper-case
                    ch[i] = (char)(ch[i] - 'a' + 'A');
                }
            }

            // If apart from first character
            // Any one is in Upper-case
            else if (ch[i] >= 'A' && ch[i] <= 'Z')

                // Convert into Lower-Case
                ch[i] = (char)(ch[i] + 'a' - 'A');
        }

        // Convert the char array to equivalent String
        String st = new String(ch);
        return st;
    }
}
