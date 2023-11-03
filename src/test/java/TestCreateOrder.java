import config.OrderApiClient;
import order_model.OrderGenerator;
import order_model.OrderRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import static java.net.HttpURLConnection.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

    @RunWith(Parameterized.class)
    public class TestCreateOrder {
        private final String[] color;
        private OrderApiClient orderCreate;
        private OrderRequest orderRequest;
        private int orderTrack;
        private int statusCode;

        public TestCreateOrder(String[] color, boolean orderIsCreated) {
            this.color = color;
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

        @DisplayName("Создание заказа и проверка ответа с указанием разных цветов и без указания цвета")
        @Description("Проверяем, что можно создать заказ с указанием разных цветов и без указания цвета")
        @Test
        public void createNewOrderTest() {
            orderRequest = OrderGenerator.getRandomData();
            orderRequest.setColor(this.color);
            ValidatableResponse createResponse = orderCreate.createNewOrder(orderRequest);
            statusCode = createResponse.extract().statusCode();
            orderTrack = createResponse.extract().path("track");

            assertEquals("The status code is invalid", HTTP_CREATED, statusCode);
            assertTrue("The order is not created", orderTrack != 0);
        }


        @After
        public void clearDown() {
            orderCreate.cancelOrder(orderTrack);
        }
}
