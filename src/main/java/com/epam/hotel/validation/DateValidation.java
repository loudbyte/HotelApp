package com.epam.hotel.validation;

import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.epam.hotel.dao.impl.DAOConstant.DATE_PATTERN;

public class DateValidation {

    private static final Logger LOGGER = Logger.getLogger(DateValidation.class);
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN);

    public static boolean isStartEndDatesValid(String stringStartDate, String stringEndDate) {

        boolean result = false;

        if ("".equals(stringStartDate) || "".equals(stringEndDate)) {
            return result;
        }

        Date startDate = null;
        Date endDate = null;
        Date nowDate = new Date();

        try {
            startDate = simpleDateFormat.parse(stringStartDate);
            endDate = simpleDateFormat.parse(stringEndDate);
        } catch (ParseException e) {
            LOGGER.error("ParseException in DateValidation", e);
        }

        if ((endDate.getTime() - startDate.getTime()) > 0 && (nowDate.getTime() - TimeUnit.DAYS.toMillis(1)) <= startDate.getTime()) {
            result = true;
        }

        return result;
    }

    public static boolean isDate(String stringDate) {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        try {
            sdf.parse(stringDate);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
}