import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class OrderManager {

    private CreateOrder order;

    public Response getOrder(CreateOrder order) {
        String createOrderAPI = "/api/v1/orders";
        return given()
                .header("Content-type", "application/json")
                .body(order)
                .when()
                .post(createOrderAPI);
    }

    public void setOrderFields(CreateOrder order) {
        this.order = order;
        order.setFirstName("Naruto");
        order.setLastName("Uchiha");
        order.setAddress("Konoha, 142 apt,");
        order.setMetroStation("4");
        order.setPhone("+7 800 355 35 35");
        order.setRentTime("5");
        order.setDeliveryDate("2020-06-06");
        order.setComment("Saske, come back to Konoha");
    }

    public Response getOrderList() {
        final String orderListAPI = "/api/v1/orders";

        return given()
                .header("Content-type", "application/json")
                .when()
                .get(orderListAPI);
    }

}
