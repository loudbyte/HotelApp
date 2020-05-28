package com.epam.hotel.controller;

import com.epam.hotel.action.Action;
import com.epam.hotel.action.ActionFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HotelAppController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(HotelAppController.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        getAction(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        getAction(request, response);
    }

    private void getAction(HttpServletRequest request, HttpServletResponse response) {

        try {
            Action action = ActionFactory.getInstance().getAction(request);
            action.execute(request, response);
        } catch (IOException | ServletException e) {
            LOGGER.error("IOException | ServletException in HotelAppController getAction", e);
        }
    }
}