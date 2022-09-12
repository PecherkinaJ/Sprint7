import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;


public class TestCreateOrder {

    private final OrderManager om = new OrderManager();
    private final CreateOrder order = new CreateOrder();

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    public void testBlackAndGreyColors() {
        om.setOrderFields(order);

        String[] array = new String[2];
        array[0] = "BLACK";
        array[1] = "GREY";
        order.setColor(array);

        Response resp = om.getOrder(order);
        resp.then().statusCode(201)
                .assertThat().body("track", Matchers.instanceOf(Integer.class));
    }

    @Test
    public void testOnlyBlackColor() {
        om.setOrderFields(order);

        String[] array = new String[1];
        array[0] = "BLACK";
        order.setColor(array);

        Response resp = om.getOrder(order);
        resp.then().statusCode(201)
                .assertThat().body("track", Matchers.instanceOf(Integer.class));
    }

    @Test
    public void testOnlyGreyColor() {
        om.setOrderFields(order);

        String[] array = new String[1];
        array[0] = "GREY";
        order.setColor(array);

        Response resp = om.getOrder(order);
        resp.then().statusCode(201)
                .assertThat().body("track", Matchers.instanceOf(Integer.class));
    }

    @Test
    public void testEmptyColors() {
        om.setOrderFields(order);

        String[] array = new String[0];
        order.setColor(array);

        Response resp = om.getOrder(order);
        resp.then().statusCode(201)
                .assertThat().body("track", Matchers.instanceOf(Integer.class));
    }

}
