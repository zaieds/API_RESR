package com.user.register_user.models;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

    public class DateHelpers {
        //private static SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
       private static SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd");

        public static Date parseDate(String date) {
            try {
                return dateParser.parse(date);
            } catch (ParseException e) {
                return null;
            }
        }
    }

