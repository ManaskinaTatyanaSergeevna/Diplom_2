package api.steps;

import api.model.User;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.hamcrest.Matchers;

import static io.restassured.RestAssured.given;

public class UserSteps {

    private final static String ERROR_MESSAGE_TEXT_REGISTER = "Email, password and name are required fields";
    private final static String ERROR_MESSAGE_TEXT_LOGIN = "email or password are incorrect";

    @Step
    public Response sendPostRequestApiAuthRegister(User user){
        return given().log().all()
                .header("Content-type", "application/json")
                .body(user)
                .when()
                .post("/api/auth/register");
    }

    @Step
    public void checkFailedResponseApiAuthRegister(Response response){
        response.then().log().all()
                .assertThat().body("success", Matchers.is(false))
                .and().body("message", Matchers.is(ERROR_MESSAGE_TEXT_REGISTER))
                .and().statusCode(403);
    }


    @Step
    public Response sendPostRequestApiAuthLogin(User user) {
        return given()
                .log()
                .all()
                .header("Content-type", "application/json")
                .body(user)
                .when()
                .post("/api/auth/login");
    }

    @Step
    public void checkFailedResponseApiAuthLogin(Response response){
         response.then().log().all()
                .assertThat().body("success", Matchers.is(false))
                .and().body("message", Matchers.is(ERROR_MESSAGE_TEXT_LOGIN))
                .and().statusCode(401);
    }
}
