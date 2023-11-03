import config.OrderApiClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import order_model.Orders;
import org.junit.Before;
import org.junit.Test;
import static java.net.HttpURLConnection.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestReceiptOderList {
    private OrderApiClient orderConfig;
    private int statusCode;
    private Orders orders;

    @Before
    public void setUp() {
        orderConfig = new OrderApiClient();
    }

    @DisplayName("Получение списка заказов")
    @Description("Проверяем, что в тело ответа возвращается список заказов")
    @Test
    public void checkReceiptOderList() {
        ValidatableResponse receiveResponse = orderConfig.getOrderList();
        statusCode = receiveResponse.extract().statusCode();
        orders = receiveResponse.extract().body().as(Orders.class);
        assertEquals("The status code is invalid", HTTP_OK, statusCode);
        assertNotNull("The list of orders is not provided", orders);
    }
}
