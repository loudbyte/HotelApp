package com.epam.hotel.action;

import com.epam.hotel.action.impl.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ActionFactory {

    private static final Map<String, Action> actions = new ConcurrentHashMap<>();

    private static ActionFactory instance = null;

    private ActionFactory() {
    }

    static {
        actions.put("/index", new ShowStartPageAction());
        actions.put("/create_person", new CreatePersonAction());
        actions.put("/registration_person", new RegistrationPersonAction());
        actions.put("/registration_button", new RegistrationButtonOnIndexPageAction());
        actions.put("/login_button", new LoginButtonOnIndexPageAction());
        actions.put("/login", new LoginAction());
        actions.put("/show_rooms", new ShowRoomsAction());
        actions.put("/show_person_admin_list", new ShowPersonAdminListAction());
        actions.put("/show_room_admin_list", new ShowRoomAdminListAction());
        actions.put("/cabinet", new ShowCabinetAction());
        actions.put("/logout", new LogoutAction());
        actions.put("/delete_person", new DeletePersonAction());
        actions.put("/delete_room", new DeleteRoomAction());
        actions.put("/delete_item", new DeleteItemAction());
        actions.put("/edit_person_button", new EditPersonButtonAction());
        actions.put("/edit_person", new EditPersonAction());
        actions.put("/edit_room", new EditRoomAction());
        actions.put("/upload_room_image_button", new UploadRoomImageButtonAction());
        actions.put("/upload_room_image", new UploadRoomImageAction());
        actions.put("/delete_room_image", new DeleteRoomImageAction());
        actions.put("/create_room", new CreateRoomAction());
        actions.put("/create_item", new CreateItemAction());
        actions.put("/create_item_button", new CreateItemButtonAction());
        actions.put("/create_order", new CreateOrderAction());
        actions.put("/cancel_order", new CancelOrderAction());
        actions.put("/show_my_orders", new ShowMyOrdersAction());
        actions.put("/show_order_room_detail", new ShowOrderDetailAction());
        actions.put("/show_order_admin_list", new ShowOrderAdminListButtonAction());
        actions.put("/delete_order", new DeleteOrderAction());
        actions.put("/delete_order_room_detail", new DeleteOrderRoomDetailAction());
        actions.put("/edit_order", new EditOrderAction());
        actions.put("/edit_order_room_detail", new EditOrderRoomDetailAction());
        actions.put("/delete_facility_package", new DeleteFacilityPackageAction());
        actions.put("/edit_facility_package", new EditFacilityPackageAction());
        actions.put("/create_facility_package", new CreateFacilityPackageAction());
        actions.put("/delete_facility", new DeleteFacilityAction());
        actions.put("/edit_facility", new EditFacilityAction());
        actions.put("/create_facility", new CreateFacilityAction());
        actions.put("/show_cart", new ShowCartAction());
        actions.put("/payment", new PaymentAction());
        actions.put("/set_reserved", new SetRoomAvailabilityAction(Boolean.FALSE));
        actions.put("/set_available", new SetRoomAvailabilityAction(Boolean.TRUE));
        actions.put("/create_language", new CreateLanguageAction());
        actions.put("/edit_language", new EditLanguageAction());
        actions.put("/delete_language", new DeleteLanguageAction());
        actions.put("/create_room_class", new CreateRoomClassAction());
        actions.put("/edit_room_class", new EditRoomClassAction());
        actions.put("/delete_room_class", new DeleteRoomClassAction());
        actions.put("/create_order_status", new CreateOrderStatusAction());
        actions.put("/edit_order_status", new EditOrderStatusAction());
        actions.put("/delete_order_status", new DeleteOrderStatusAction());

    }

    public static ActionFactory getInstance() {

        if (instance == null) {
            instance = new ActionFactory();
        }
        return instance;
    }

    public Action getAction(HttpServletRequest request) {

        return actions.get(request.getPathInfo().toLowerCase());
    }
}