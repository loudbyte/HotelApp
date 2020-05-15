package com.epam.hotel.action;

import com.epam.hotel.action.impl.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.epam.hotel.action.impl.Constant.*;

public class ActionFactory {


    private static Map<String, Action> actions = new ConcurrentHashMap<>();

    private static ActionFactory instance = null;

    private ActionFactory() {
    }

    static {
        actions.put("/index", new StartPageAction());
        actions.put("/create_person", new CreatePersonAction());
        actions.put("/registration_person", new RegistrationPersonAction());
        actions.put("/registration_button", new RegistrationButtonOnIndexPageAction());
        actions.put("/login_button", new LoginButtonOnIndexPageAction());
        actions.put("/login", new LoginAction());
        actions.put("/show_rooms", new ShowRoomsAction());
        actions.put("/show_person_list", new ShowPersonAdminListAction());
        actions.put("/show_room_admin_list", new ShowRoomAdminListAction());
        actions.put("/cabinet", new CabinetAction());
        actions.put("/logout", new LogoutAction());
        actions.put("/show_rooms_standard", new ShowRoomsByClassAction(STANDARD));
        actions.put("/show_rooms_suite", new ShowRoomsByClassAction(SUITE));
        actions.put("/show_rooms_delux", new ShowRoomsByClassAction(DELUX));
        actions.put("/delete_person", new DeletePersonAction());
        actions.put("/delete_room", new DeleteRoomAction());
        actions.put("/edit_person_button", new EditPersonButtonAction());
        actions.put("/edit_person", new EditPersonAction());
        actions.put("/edit_room_button", new EditRoomButtonAction());
        actions.put("/edit_room", new EditRoomAction());
        actions.put("/upload_room_image_button", new UploadRoomImageButtonAction());
        actions.put("/upload_room_image", new UploadRoomImageAction());
        actions.put("/delete_room_image", new DeleteRoomImageAction());
        actions.put("/create_room", new CreateRoomAction());
        actions.put("/create_order", new CreateOrderAction());
        actions.put("/create_order_button", new CreateOrderButtonAction());
        actions.put("/cancel_order", new CancelOrderAction());
        actions.put("/show_my_orders", new ShowMyOrdersAction());
        actions.put("/show_order_detail", new ShowOrderDetailAction());
        actions.put("/show_order_admin_list", new ShowOrderAdminListButtonAction());
        actions.put("/delete_order", new DeleteOrderAction());
        actions.put("/edit_order_button", new EditOrderButtonAction());
        actions.put("/edit_order", new EditOrderAction());
        actions.put("/edit_order_detail_button", new EditOrderDetailButtonAction());
        actions.put("/edit_order_detail", new EditOrderDetailAction());

    }

    public static ActionFactory getInstance() {

        if (instance == null) {
            instance = new ActionFactory();
        }
        return instance;
    }

    public Action getAction(HttpServletRequest request) {

        Action action = actions.get(request.getPathInfo());

        return action;
    }
}