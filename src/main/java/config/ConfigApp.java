package config;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
public class ConfigApp {

        protected static final String URL = "https://qa-scooter.praktikum-services.ru/";
        protected RequestSpecification getHeader() {
            return new RequestSpecBuilder()
                    .setContentType(ContentType.JSON)
                    .setBaseUri(URL)
                    .build();
        }
}
