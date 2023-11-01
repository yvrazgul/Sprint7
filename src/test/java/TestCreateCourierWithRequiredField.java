import config.CourierApiClient;
import courier_model.Courier;
import courier_model.CourierGenerator;
import courier_model.CourierLogin;
import org.junit.After;
import org.junit.Before;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import static java.net.HttpURLConnection.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

    public class TestCreateCourierWithRequiredField {
        private CourierApiClient courierCreate;
        private int courierId;
        private int statusCode;
        private boolean isCourierCreated;

        @Before
        public void setUp() {
            courierCreate = new CourierApiClient();
        }

        @DisplayName("Создание нового курьера с логином, паролем и именем")
        @Description("Проверяем, что курьер создается с заполнением обязательных полей")
        @Test
        public void createNewCourierWithRequiredField() {
            Courier courier = CourierGenerator.getRandomData();
            ValidatableResponse createResponse = courierCreate.create(courier);
            statusCode = createResponse.extract().statusCode();
            isCourierCreated = createResponse.extract().path("ok");

            assertEquals("The status code is invalid", HTTP_CREATED, statusCode);
            assertTrue("The courier is not created", isCourierCreated);


            ValidatableResponse loginResponse = courierCreate.login(CourierLogin.courierLogin(courier));
            courierId = loginResponse.extract().path("id");     }

        @After
        public void clearDown() {
            courierCreate.delete(courierId);
        }
}
