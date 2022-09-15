import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.Random;

import static io.restassured.RestAssured.*;

public class OrderManager {
    String[] firstName = {"Ivan", "Nikolay", "Alisa", "Marusya"};
    String[] lastName = {"Petrov", "Ivanov", "Kolonka", "Helper"};
    String[] address = {"Moscow", "Saint-Petersburg", "Nizhny Novgorod", "Novosibirsk"};
    String[] metroStation = {"1", "2", "3", "4"};
    String[] phone = {"89536547896", "86543259875"};
    String[] rentTime = {"5", "3"};
    String[] deliveryDate = {"2022-09-15", "2022-09-18", "2022-09-23"};
    String[] comment = {"no comment", "random comment"};
    String[][] color = {{"BLACK", "GREY"},{"black", "grey"}, {"BLACK"}, {"black"}, {"GREY"}, {"grey"}, {null, null}, {"PINK"}, {"NoColor"}, {}};
    private final WebManager webManager = new WebManager();

    @Step("Get First Name")
    public String getFirstName(){
        return firstName[new Random().nextInt(firstName.length)];
    }

    @Step("Get lastName")
    public String getLastName(){
        return lastName[new Random().nextInt(lastName.length)];
    }

    @Step("Get address")
    public String getAddress(){
        return address[new Random().nextInt(address.length)];
    }

    @Step("Get metroStation")
    public String getMetroStation(){
        return metroStation[new Random().nextInt(metroStation.length)];
    }

    @Step("Get phone")
    public String getPhone(){
        return phone[new Random().nextInt(phone.length)];
    }

    @Step("Get rentTime")
    public String getRentTime(){
        return rentTime[new Random().nextInt(rentTime.length)];
    }

    @Step("Get deliveryDate")
    public String getDeliveryDate(){
        return deliveryDate[new Random().nextInt(deliveryDate.length)];
    }

    @Step("Get comment")
    public String getComment(){
        return comment[new Random().nextInt(comment.length)];
    }

    @Step("Get color")
    public String[] getColor(int i){
        return color[i];
    }

    public Response getOrder(CreateOrder order) {
        String createOrderAPI = webManager.getCreateOrderAPI();
        return given()
                .header("Content-type", "application/json")
                .body(order)
                .when()
                .post(createOrderAPI);
    }

    public Response getOrderList() {
        String orderListAPI = webManager.getOrderListAPI();
        return given()
                .header("Content-type", "application/json")
                .when()
                .get(orderListAPI);
    }

}
