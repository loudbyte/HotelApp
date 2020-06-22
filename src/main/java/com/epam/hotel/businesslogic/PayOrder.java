package com.epam.hotel.businesslogic;

import com.epam.hotel.action.impl.ActionConstant;
import com.epam.hotel.dao.OrderMainDAO;
import com.epam.hotel.dao.impl.OrderMainDAOImpl;
import com.epam.hotel.entity.OrderMain;

import java.math.BigDecimal;

/**
 * The class {@code PayOrder} simulates payment
 */
public class PayOrder {

    private static final int RANGE = 10;
    private static final int BOUND = 8;

    public boolean pay(OrderMain orderMain) {
        CalculatePrice calculatePrice = new CalculatePrice();
        BigDecimal price = calculatePrice.calculateOrderMain(orderMain.getId());

        //some code for payment
        int randomNumber = (int) (Math.random() * RANGE);

        if (randomNumber < BOUND) {
            orderMain.setStatus(ActionConstant.PAID);
            OrderMainDAO orderMainDAO = new OrderMainDAOImpl();
            orderMainDAO.updateOneById(orderMain.getId(), orderMain);
            return true;
        } else {
            return false;
        }
    }
}