package datas;


import org.testng.annotations.DataProvider;

import java.util.ArrayList;

public class ValidationData {

    private static ArrayList<String> getInvalidCases(String validString, String invalidSequence) {
        ArrayList<String> invalidCases = new ArrayList<String>(3);
        StringBuilder builder = new StringBuilder(validString);
        invalidCases.add(builder.insert(0, invalidSequence).toString());
        builder = new StringBuilder(validString);
        invalidCases.add(builder.insert(validString.length()/2, invalidSequence).toString());
        builder = new StringBuilder(validString);
        invalidCases.add(builder.insert(validString.length(), invalidSequence).toString());
        return invalidCases;
    }
    private static ArrayList<String> getInvalidMails(String email, String symbol) {
        if (email.matches("^[a-zA-z]+?@[a-zA-z]+?[.][a-zA-z]+?$")) {
            String first = email.split("@")[0];
            String second = email.split("@")[1].split("[.]")[0];
            String third = email.split("@")[1].split("[.]")[1];

            ArrayList<String> modified = new ArrayList<String>(6);

            for (String s : getInvalidCases(second, symbol)) {
                modified.add(first + "@" + s + "." + third);
            }
            for (String t : getInvalidCases(third, symbol)) {
                modified.add(first + "@" + second + "." + t);
            }
            return modified;
        } else {
            System.err.println("Email should match regex: ^[a-zA-z]+?@[a-zA-z]+?[.][a-zA-z]+?$ !");
            return null;
        }
    }

    @DataProvider(name = "invalidEmails")
    public static Object[][] invalidEmails() {

        String[] invalids = {"~", "`", "!", "@", "#", "$", "%", "^", "&",
                "*", "(", ")", "Л", "+", "=", "а", "№", "{",
                "}", "[", "]", "\\", "\"", ";", ":", "'", "|",
                "/", "<", ">", ",", " ", "?", "--", "__", "..",
        };
        String validMail = "email@valid.com";

        Object[][] array = new Object[6 * invalids.length][1];
        int index = 0;

        for (String sym : invalids) {
            for (String str : getInvalidMails(validMail, sym)) {
                array[index][0] = str;
                index++;
            }
        }

        System.out.println("Provider generated " + array.length + " items");
        return array;
    }
    @DataProvider(name = "validEmails")
    public static Object[][] validEmails () {
        return new Object[][] {
                {"em-ail@valid.com"}, {"em_ail@valid.com"}, {"em.ai.l@valid.com"}, {"em.ail@va.lid.com"},
                {"em-ail@val-id.com"}, {"e-m_a.il@valid.com"}, {"email@valid.com"},
        };
    }
    @DataProvider(name = "invalidEmailsExceptions")
    public static Object[][] invalidMailsExceptions() {
        return new Object[][] {
                {"-email@mail.ma"}, {"email-@mail.ma"}, {"email@-mail.ma"}, {"email@mail-.ma"}, {"email@mail.-ma"}, {"email@mail.ma-"},
                {".email@mail.ma"}, {"email.@mail.ma"}, {"email@.mail.ma"}, {"email@mail..ma"}, {"email@mail..ma"}, {"email@mail.ma."},
                {"_email@mail.ma"}, {"email_@mail.ma"}, {"email@_mail.ma"}, {"email@mail_.ma"}, {"email@mail._ma"}, {"email@mail.ma_"},
                {"@mail.ma"},
        };
    }
    @DataProvider(name = "existEmailsTableGames")
    public static Object[][] existEmailsTableGames () {
        return new Object[][] {
                {"LcQA@dsln.LHIF"}, {"bcda@AcdB.dbCa3"}, {"JTtP@Iypo.MeYs"}, {"ruzome@asdasd.asd"}, {"HLAJ@XjZF.ozrH"}, {"CdaB@aDbC.CBdA"},
                {"VxSl@sbUW.coZP"}, {"cdBA@aCbd.cbDa"}, {"zSzM@BKtM.vWJn"}
        };
    }

    @DataProvider(name = "invalidLogins")
    public static Object[][] invalidLogins() {

        String[] invalids = {"~", "`", "!", "@", "#", "$", "%", "^", "&",
                "*", "(", ")", "Л", "+", "=", "а", "№", "{",
                "}", "[", "]", "\\", "\"", ";", ":", "'", "|",
                "/", "<", ">", ",", " ", "?", "--", "__", "..",
        };
        String validLogin = "correct";

        Object[][] array = new Object[3 * invalids.length][1];
        int index = 0;

        for (String sym : invalids) {
            for (String str : getInvalidCases(validLogin, sym)) {
                array[index][0] = str;
                index++;
            }
        }
        System.out.println("Provider generated " + array.length + " items");
        return array;
    }
    @DataProvider(name = "validLogins")
    public static Object[][] validLogins () {
        return new Object[][] {
                {"logins"}, {"log.ins"}, {"log_ins"},
                {"lOGins"}, {"log.inS"}, {"lOg_ins"},
                {"LOGINS"}, {"LOG.INS"}, {"LOG_INS"},
        };
    }
    @DataProvider(name = "invalidLoginsExceptions")
    public static Object[][] invalidLoginsExceptions () {
        return new Object[][] {
                {".logins"}, {"logins."}, {"logins_"}, {"_logins"}
        };
    }
    @DataProvider(name = "existLoginsTableGames")
    public static Object[][] existLoginsTableGames () {
        return new Object[][] {
                {"ebAHcdFg"}, {"pIHLKmhy"}, {"afeBDGHc2"}, {"afeBDGHc"}, {"xoMvIfgJ"}, {"HEgbaFdC"}, {"vkcdmkcI"}, {"max1321"},
        };
    }

    @DataProvider(name = "validPhones")
    public static Object[][] validPhones () {
        return new Object[][] {
                {"0990000000"}, {"099 00 000 00"}, {"+3 099 000 00 00"}, {"099-000-00-00"}, {"+3 099-00-000-00"},
        };
    }
    @DataProvider(name = "invalidPhones")
    public static Object[][] invalidPhones() {

        String[] invalids = {"~", "`", "!", "@", "#", "$", "%", "^", "&",
                "*", "(", ")", "Л", "=", "№", "{",
                "}", "[", "]", "\\", "\"", ";", ":", "'", "|",
                "/", "<", ">", ",", "?", "--", "__", "..",
        };
        String validLogin = "0990000000";

        Object[][] array = new Object[3 * invalids.length][1];
        int index = 0;

        for (String sym : invalids) {
            for (String str : getInvalidCases(validLogin, sym)) {
                array[index][0] = str;
                index++;
            }
        }
        System.out.println("Provider generated " + array.length + " items");
        return array;
    }
    @DataProvider(name = "invalidPhonesExceptions")
    public static Object[][] invalidPhonesExceptions () {
        return new Object[][] {
                {"-0990-00-00-00"}, {"099+000-000-00"}, {"00  0 00 0 000 00  0"}, {" +3 099 000 000 00"} , {"+3 099 000 000 00 "}, {" 3 099 000 000 00"}
                , {"-3 099 000 000 00 "}, {"099 000 000 00-"}, {"-099 000 000 00"}
        };
    }

    @DataProvider(name = "validNames")
    public static Object[][] validNames () {
        return new Object[][] {
                {"Name"}, {"A"}, {"Вася"}, {"йа ся"}, {"Na-me my"}, {"Ibn Ah Dali"}, {"Wo-cha"}
        };
    }
    @DataProvider(name = "invalidNames")
    public static Object[][] invalidNames() {

        String[] invalids = {"~", "`", "!", "@", "#", "$", "%", "^", "&",
                "*", "(", ")", "+", "=", "№", "{", ".",
                "}", "[", "]", "\\", "\"", ";", ":", "'", "|",
                "/", "<", ">", ",", "?", "--", "__", "..",
        };
        String validLogin = "correct";

        Object[][] array = new Object[3 * invalids.length][1];
        int index = 0;

        for (String sym : invalids) {
            for (String str : getInvalidCases(validLogin, sym)) {
                array[index][0] = str;
                index++;
            }
        }
        System.out.println("Provider generated " + array.length + " items");
        return array;
    }
    @DataProvider(name = "invalidNamesExceptions")
    public static Object[][] invalidNamesExceptions () {
        return new Object[][] {
                {"-Name"}, {"A-"}, {" Вася"}, {"йа ся "}, {"Na-me my-"}, {"Ibn-Ah- Dali"}, {"Wo-cha "}
        };
    }




}
