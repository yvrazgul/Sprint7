package config;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import order_model.OrderRequest;
import static io.restassured.RestAssured.given;

public class OrderApiClient extends ConfigApp {
    private static final String ORDER_LIST_URL = URL + "api/v1/orders/";
    private static final String ORDER_TRACK_URL = URL + "api/v1/orders/track";
    private static final String ORDER_CANCEL_URL = URL + "api/v1/orders/cancel";

    @Step("Получение списка заказов")
    public ValidatableResponse getOrderList() {
        return given().log().all()
                .spec(getHeader())
                .when()
                .get(ORDER_LIST_URL)
                .then().log().all();
    }

    @Step("Создание нового заказа")
    public ValidatableResponse createNewOrder(OrderRequest orderRequest) {
        return given().log().all()
                .spec(getHeader())
                .body(orderRequest)
                .when()
                .post(ORDER_LIST_URL)
                .then().log().all();
    }
    @Step("Получение данных заказа")
    public ValidatableResponse getOrderByTrackNumber(int orderTrack){
        return given().log().all()
                .spec(getHeader())
                .when()
                .get(ORDER_TRACK_URL + "?t=" + orderTrack)
                .then().log().all();
    }

    @Step("Отмена созданного заказ")
    public ValidatableResponse cancelOrder(int orderTrack) {
        return given().log().all()
                .spec(getHeader())
                .when()
                .put(ORDER_CANCEL_URL  + "?track=" + String.valueOf(orderTrack))
                .then().log().all();
    }

}