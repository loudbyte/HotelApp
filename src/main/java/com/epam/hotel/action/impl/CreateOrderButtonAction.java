package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.impl.FacilityDAOImpl;
import com.epam.hotel.dao.impl.OrderFacilityDetailDAOImpl;
import com.epam.hotel.dao.impl.RoomDAOImpl;
import com.epam.hotel.entity.Facility;
import com.epam.hotel.entity.FacilityPackage;
import com.epam.hotel.entity.OrderFacilityDetail;
import com.epam.hotel.entity.Room;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.hotel.action.impl.Constant.CREATE_ORDER_URL;
import static java.lang.Boolean.FALSE;

public class CreateOrderButtonAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RoomDAOImpl roomDAO = new RoomDAOImpl();

        long id = Long.parseLong(request.getParameter("room_id"));

        Room room = roomDAO.getOneById(id);

        room.setAvailability(FALSE);
//        roomDAO.updateOneById(room.getId(), room);

        FacilityDAOImpl facilityDAO = new FacilityDAOImpl();
        OrderFacilityDetailDAOImpl orderFacilityDetailDAO = new OrderFacilityDetailDAOImpl();
        List<OrderFacilityDetail> orderFacilityDetailList = orderFacilityDetailDAO.getAll();
        List<Facility> facilityList = new ArrayList<>();
        List<FacilityPackage> facilityPackageList = new ArrayList<>();
        FacilityPackage facilityPackage;


        for (OrderFacilityDetail detail: orderFacilityDetailList) {
            facilityPackage = new FacilityPackage(detail.getId(), detail.getFacilityPackageName(), facilityDAO.getFacilityListByPackageId(detail.getId()));
        }

        int counter = 0;

        do {
            counter++;
            request.setAttribute("facilityPackageListId"+ counter, facilityList);
        } while (facilityList == null);

        request.setAttribute("room", room);
        request.getRequestDispatcher(CREATE_ORDER_URL).forward(request, response);
    }
}
