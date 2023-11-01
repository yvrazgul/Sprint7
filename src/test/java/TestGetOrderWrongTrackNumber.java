import config.OrderApiClient;
import order_model.Order;
import order_model.OrderGenerator;
import order_model.OrderRequest;
import org.apache.commons.lang3.RandomStringUtils;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static java.lang.Integer.parseInt;
import static java.net.HttpURLConnection.*;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class TestGetOrderWrongTrackNumber {
    private final String[] color;
    private final boolean orderIsCreated;
    private OrderApiClient orderCreate;
    private OrderRequest orderRequest;
    private int orderTrack;
    private int statusCode;
    private Order order;
    private String message;

    public TestGetOrderWrongTrackNumber(String[] color, boolean orderIsCreated) {
        this.color = color;
        this.orderIsCreated = orderIsCreated;
    }

    @Parameterized.Parameters()
    public static Object[][] getColorData() {
        return new Object[][]{
                {new String[]{"BLACK", "GREY"}, true},
                {new String[]{"BLACK"}, true},
                {new String[]{"GREY"}, true},
                {new String[]{""}, true},
        };
    }

    @Before
    public void setUp() {
        orderCreate = new OrderApiClient();
    }

    @DisplayName("Проверка ошибки получения заказа, если ввести неверный трэк номер")
    @Description("Проверяем, что если трэк номер не найден, получаем ошибку")
    @Test
    public void createReturnErrorIfNotExistingTrackNumber() {
        orderRequest = OrderGenerator.getRandomData();
        orderRequest.setColor(this.color);
        ValidatableResponse createResponse = orderCreate.createNewOrder(orderRequest);
        statusCode = createResponse.extract().statusCode();
        int initialOrderTrack = createResponse.extract().path("track");
        assertEquals("The status code is invalid", HTTP_CREATED, statusCode);
        assertTrue("The courier is not created", initialOrderTrack != 0);

        orderTrack = parseInt(RandomStringUtils.randomNumeric(8));

        ValidatableResponse createResponseByTrackNumber = orderCreate.getOrderByTrackNumber(orderTrack);
        statusCode = createResponseByTrackNumber.extract().statusCode();
        order = createResponseByTrackNumber.extract().body().as(Order.class);
        message = createResponseByTrackNumber.extract().path("message");
        assertEquals("The status code is invalid", HTTP_NOT_FOUND, statusCode);
        assertEquals("The login is still enabled", "Заказ не найден", message);

        orderTrack = initialOrderTrack;
    }


    @After
    public void clearDown() {
        orderCreate.cancelOrder(orderTrack);
    }

}
