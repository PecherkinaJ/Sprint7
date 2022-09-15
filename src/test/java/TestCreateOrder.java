import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class TestCreateOrder {

    private static final OrderManager om = new OrderManager();
    private final WebManager webManager = new WebManager();
    private final CreateOrder order;
    private final int expectedCode;

    public TestCreateOrder(CreateOrder order, int expectedCode) {
        this.order = order;
        this.expectedCode = expectedCode;
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = webManager.getBaseURI();
    }

    @Parameterized.Parameters(name = "Тестовые данные: ожидается: 201, результат: {1}")
    public static Object[] data() {
        return new Object[][]{
                {orderBlackAndGreyColorsUpperCase(), 201},
                {orderBlackAndGreyColorsLowerCase(), 201},
                {testOnlyBlackColorUpperCase(), 201},
                {testOnlyBlackColorLowerCase(), 201},
                {testOnlyGreyColorUpperCase(), 201},
                {testOnlyGreyColorLowerCase(), 201},
                {testNullColor(), 201},
                {testOnlyPinkColor(), 201},
                {testNoColor(), 201},
                {testEmptyColor(), 201},

        };
    }

    @Test
    public void testCreateOrders() {
        Response resp = om.getOrder(order);
        int statusCode = om.getOrder(order).getStatusCode();
        //contains "track" in the response
        resp.then().assertThat().body("track", Matchers.instanceOf(Integer.class));
        //has 201 in statusCode
        assertEquals(expectedCode, statusCode);
    }

    public static CreateOrder orderBlackAndGreyColorsUpperCase() {
        CreateOrder order = new CreateOrder();
        order.setFirstName(om.getFirstName());
        order.setLastName(om.getLastName());
        order.setAddress(om.getAddress());
        order.setMetroStation(om.getMetroStation());
        order.setPhone(om.getPhone());
        order.setRentTime(om.getRentTime());
        order.setDeliveryDate(om.getDeliveryDate());
        order.setComment(om.getComment());
        order.setColor(om.getColor(0));
        return order;
    }

    public static CreateOrder orderBlackAndGreyColorsLowerCase() {
        CreateOrder order = new CreateOrder();
        order.setFirstName(om.getFirstName());
        order.setLastName(om.getLastName());
        order.setAddress(om.getAddress());
        order.setMetroStation(om.getMetroStation());
        order.setPhone(om.getPhone());
        order.setRentTime(om.getRentTime());
        order.setDeliveryDate(om.getDeliveryDate());
        order.setComment(om.getComment());
        order.setColor(om.getColor(1));
        return order;
    }

    public static CreateOrder testOnlyBlackColorUpperCase() {
        CreateOrder order = new CreateOrder();
        order.setFirstName(om.getFirstName());
        order.setLastName(om.getLastName());
        order.setAddress(om.getAddress());
        order.setMetroStation(om.getMetroStation());
        order.setPhone(om.getPhone());
        order.setRentTime(om.getRentTime());
        order.setDeliveryDate(om.getDeliveryDate());
        order.setComment(om.getComment());
        order.setColor(om.getColor(2));
        return order;
    }

    public static CreateOrder testOnlyBlackColorLowerCase() {
        CreateOrder order = new CreateOrder();
        order.setFirstName(om.getFirstName());
        order.setLastName(om.getLastName());
        order.setAddress(om.getAddress());
        order.setMetroStation(om.getMetroStation());
        order.setPhone(om.getPhone());
        order.setRentTime(om.getRentTime());
        order.setDeliveryDate(om.getDeliveryDate());
        order.setComment(om.getComment());
        order.setColor(om.getColor(3));
        return order;
    }

    public static CreateOrder testOnlyGreyColorUpperCase() {
        CreateOrder order = new CreateOrder();
        order.setFirstName(om.getFirstName());
        order.setLastName(om.getLastName());
        order.setAddress(om.getAddress());
        order.setMetroStation(om.getMetroStation());
        order.setPhone(om.getPhone());
        order.setRentTime(om.getRentTime());
        order.setDeliveryDate(om.getDeliveryDate());
        order.setComment(om.getComment());
        order.setColor(om.getColor(4));
        return order;
    }

    public static CreateOrder testOnlyGreyColorLowerCase() {
        CreateOrder order = new CreateOrder();
        order.setFirstName(om.getFirstName());
        order.setLastName(om.getLastName());
        order.setAddress(om.getAddress());
        order.setMetroStation(om.getMetroStation());
        order.setPhone(om.getPhone());
        order.setRentTime(om.getRentTime());
        order.setDeliveryDate(om.getDeliveryDate());
        order.setComment(om.getComment());
        order.setColor(om.getColor(5));
        return order;
    }

    public static CreateOrder testNullColor() {
        CreateOrder order = new CreateOrder();
        order.setFirstName(om.getFirstName());
        order.setLastName(om.getLastName());
        order.setAddress(om.getAddress());
        order.setMetroStation(om.getMetroStation());
        order.setPhone(om.getPhone());
        order.setRentTime(om.getRentTime());
        order.setDeliveryDate(om.getDeliveryDate());
        order.setComment(om.getComment());
        order.setColor(om.getColor(6));
        return order;
    }

    public static CreateOrder testOnlyPinkColor() {
        CreateOrder order = new CreateOrder();
        order.setFirstName(om.getFirstName());
        order.setLastName(om.getLastName());
        order.setAddress(om.getAddress());
        order.setMetroStation(om.getMetroStation());
        order.setPhone(om.getPhone());
        order.setRentTime(om.getRentTime());
        order.setDeliveryDate(om.getDeliveryDate());
        order.setComment(om.getComment());
        order.setColor(om.getColor(7));
        return order;
    }

    public static CreateOrder testNoColor() {
        CreateOrder order = new CreateOrder();
        order.setFirstName(om.getFirstName());
        order.setLastName(om.getLastName());
        order.setAddress(om.getAddress());
        order.setMetroStation(om.getMetroStation());
        order.setPhone(om.getPhone());
        order.setRentTime(om.getRentTime());
        order.setDeliveryDate(om.getDeliveryDate());
        order.setComment(om.getComment());
        order.setColor(om.getColor(8));
        return order;
    }

    public static CreateOrder testEmptyColor() {
        CreateOrder order = new CreateOrder();
        order.setFirstName(om.getFirstName());
        order.setLastName(om.getLastName());
        order.setAddress(om.getAddress());
        order.setMetroStation(om.getMetroStation());
        order.setPhone(om.getPhone());
        order.setRentTime(om.getRentTime());
        order.setDeliveryDate(om.getDeliveryDate());
        order.setComment(om.getComment());
        order.setColor(om.getColor(9));
        return order;
    }
}
