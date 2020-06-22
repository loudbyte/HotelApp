package com.epam.hotel.validation;

import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.epam.hotel.action.impl.ActionConstant.*;
import static com.epam.hotel.dao.impl.DAOConstant.DATE_PATTERN;

public class DateValidation {

    private static final Logger LOGGER = Logger.getLogger(DateValidation.class);

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN);
    private static final int ZERO = 0;
    private static final int ONE = 1;


    public static boolean isStartEndDatesValid(String stringStartDate, String stringEndDate) {

        boolean result = false;

        if (EMPTY_STRING.equals(stringStartDate) || EMPTY_STRING.equals(stringEndDate)) {
            return result;
        }

        Date startDate = null;
        Date endDate = null;
        Date nowDate = new Date();

        try {
            startDate = simpleDateFormat.parse(stringStartDate);
            endDate = simpleDateFormat.parse(stringEndDate);
        } catch (ParseException exception) {
            LOGGER.error(exception, exception);
        }

        if ((endDate.getTime() - startDate.getTime()) > ZERO && (nowDate.getTime() - TimeUnit.DAYS.toMillis(ONE)) <= startDate.getTime()) {
            result = true;
        }

        return result;
    }

    public static boolean isDate(String stringDate) {
        DateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN);
        simpleDateFormat.setLenient(false);
        try {
            simpleDateFormat.parse(stringDate);
        } catch (ParseException exception) {
            return false;
        }
        return true;
    }
}