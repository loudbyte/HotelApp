package com.epam.hotel.businesslogic;

import com.epam.hotel.dao.FacilityDAO;
import com.epam.hotel.dao.FacilityPackageDAO;
import com.epam.hotel.dao.OrderRoomDetailDAO;
import com.epam.hotel.dao.RoomDAO;
import com.epam.hotel.dao.impl.*;
import com.epam.hotel.entity.Facility;
import com.epam.hotel.entity.OrderRoomDetail;
import com.epam.hotel.entity.Room;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CalculatePrice {

    private static final Logger LOGGER = Logger.getLogger(CalculatePrice.class);

    private static final long MILLISECONDS_TO_ONE_DAY = 24 * 60 * 60 * 1000;
    private static final BigDecimal ONE_HUNDRED_PERCENT = BigDecimal.valueOf(100);
    private static final int PRECISION = 2;

    public BigDecimal calculateOrderMain(long orderMainId) {
        BigDecimal orderMainPrice = BigDecimal.ZERO;
        OrderRoomDetailDAO orderRoomDetailDAO = new OrderRoomDetailDAOImpl();
        List<OrderRoomDetail> orderRoomDetailList = orderRoomDetailDAO.getAllByOrderId(orderMainId);

        for (OrderRoomDetail orderRoomDetail : orderRoomDetailList) {
            orderMainPrice = orderMainPrice.add(calculateOrderRoomDetail(orderRoomDetail));
        }

        return orderMainPrice;
    }

    public BigDecimal calculateOrderRoomDetail(OrderRoomDetail orderRoomDetail) {

        BigDecimal orderDetailPrice = BigDecimal.ZERO;
        BigDecimal packageFacilityPrice = BigDecimal.ZERO;
        BigDecimal roomPrice = BigDecimal.ZERO;

        roomPrice = calculateRoomPrice(orderRoomDetail.getRoomId());
        packageFacilityPrice = calculateFacilityPackagePrice(orderRoomDetail.getFacilityPackageId());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DAOConstant.DATE_PATTERN);

        Date endDate = null;
        Date startDate = null;
        try {
            endDate = simpleDateFormat.parse(orderRoomDetail.getEndDate());
            startDate = simpleDateFormat.parse(orderRoomDetail.getStartDate());
        } catch (ParseException exception) {
            LOGGER.error(exception, exception);
        }
        long difference = endDate.getTime() - startDate.getTime();
        long diffDays = (difference / MILLISECONDS_TO_ONE_DAY);

        orderDetailPrice = (roomPrice.add(packageFacilityPrice)).multiply(BigDecimal.valueOf(diffDays));

        return orderDetailPrice;
    }

    public BigDecimal calculateRoomPrice(long roomId) {

        BigDecimal roomPrice = BigDecimal.ZERO;

        RoomDAO roomDAO = new RoomDAOImpl();
        Room room = roomDAO.getOneById(roomId);
        roomPrice = room.getPrice();

        return roomPrice;
    }

    public BigDecimal calculateFacilityPackagePrice(long facilityPackageId) {

        BigDecimal packageFacilityPrice = BigDecimal.ZERO;

        FacilityDAO facilityDAO = new FacilityDAOImpl();
        FacilityPackageDAO facilityPackageDAO = new FacilityPackageDAOImpl();
        BigDecimal discount = facilityPackageDAO.getOneById(facilityPackageId).getDiscount();
        List<Facility> facilityList = facilityDAO.getFacilityListByFacilityPackageId(facilityPackageId);
        for (Facility facility : facilityList) {
            packageFacilityPrice = packageFacilityPrice.add(facility.getPrice());
        }
        return packageFacilityPrice.multiply(discount).divide(ONE_HUNDRED_PERCENT).round(new MathContext(PRECISION));
    }
}