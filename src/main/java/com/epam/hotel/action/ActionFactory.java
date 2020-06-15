package com.epam.hotel.action;

import com.epam.hotel.action.impl.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.epam.hotel.action.impl.ActionConstant.*;

public class ActionFactory {

    private static Map<String, Action> actions = new ConcurrentHashMap<>();

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
        actions.put("/show_rooms_standard", new ShowRoomsByClassAction(STANDARD));
        actions.put("/show_rooms_suite", new ShowRoomsByClassAction(SUITE));
        actions.put("/show_rooms_deluxe", new ShowRoomsByClassAction(DELUXE));
        actions.put("/delete_person", new DeletePersonAction());
        actions.put("/delete_room", new DeleteRoomAction());
        actions.put("/delete_item", new DeleteItemAction());
        actions.put("/edit_person_button", new EditPersonButtonAction());
        actions.put("/edit_person", new EditPersonAction());
        actions.put("/edit_room_button", new EditRoomButtonAction());
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
        actions.put("/edit_order_button", new EditOrderButtonAction());
        actions.put("/edit_order", new EditOrderAction());
        actions.put("/edit_order_room_detail_button", new EditOrderRoomDetailButtonAction());
        actions.put("/edit_order_room_detail", new EditOrderRoomDetailAction());
        actions.put("/delete_package", new DeletePackageAction());
        actions.put("/edit_package", new EditPackageAction());
        actions.put("/create_package", new CreatePackageAction());
        actions.put("/delete_facility", new DeleteFacilityAction());
        actions.put("/edit_facility", new EditFacilityAction());
        actions.put("/create_facility", new CreateFacilityAction());
        actions.put("/show_cart", new ShowCartAction());
        actions.put("/pay_order", new PayOrderAction());
        actions.put("/payment", new PaymentAction());

    }

    public static ActionFactory getInstance() {

        if (instance == null) {
            instance = new ActionFactory();
        }
        return instance;
    }

    public Action getAction(HttpServletRequest request) {

        Action action = actions.get(request.getPathInfo().toLowerCase());

        return action;
    }
}