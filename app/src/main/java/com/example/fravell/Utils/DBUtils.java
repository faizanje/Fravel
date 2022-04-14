package com.example.fravell.Utils;

import com.example.fravell.Models.Buyer;

import io.paperdb.Paper;

public class DBUtils {


    public static void saveLoggedInUser(Buyer buyer) {
        Paper.book().write(Constants.KEY_User,buyer);
    }

    public static Buyer readLoggedInUser() {
        return Paper.book().read(Constants.KEY_User);
    }

    public static void deleteLoggedInUser() {
        Paper.book().delete(Constants.KEY_User);
    }
}
