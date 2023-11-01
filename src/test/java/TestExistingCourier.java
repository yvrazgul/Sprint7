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
import static org.junit.Assert.*;

public class TestExistingCourier {
    private CourierApiClient courierCreate;
    private Courier courier;
    private int statusCode;
    private int courierId;

    @Before
    public void setUp() {
        courierCreate = new CourierApiClient();
    }

    @DisplayName("При создании курьера с существующими данными, ожидаем ошибку")
    @Description("Проверяем, что нельзя создать курьера с существующими данными")
    @Test
    public void createCourierWithExistingData() {
        courier = CourierGenerator.getRandomData();
        ValidatableResponse createResponse = courierCreate.create(courier);
        statusCode = createResponse.extract().statusCode();
        assertEquals("The status code is invalid", HTTP_CREATED, statusCode);

        ValidatableResponse createExistingCourier = courierCreate.create(courier);
        statusCode = createExistingCourier.extract().statusCode();
        String isCourierCreated = createExistingCourier.extract().path("message");
        assertEquals("The status code is invalid", HTTP_CONFLICT, statusCode);
        assertEquals("The courier is already created", "Этот логин уже используется. Попробуйте другой.", isCourierCreated);

        ValidatableResponse loginResponse = courierCreate.login(CourierLogin.courierLogin(courier));
        courierId = loginResponse.extract().path("id");
        assertTrue("The courier ID is not provided", courierId != 0);

    }


    @After
    public void clearDown() {
        courierCreate.delete(courierId);
    }
}
