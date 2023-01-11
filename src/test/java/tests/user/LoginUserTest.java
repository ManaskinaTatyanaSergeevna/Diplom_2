package tests.user;

import api.model.User;
import api.steps.UserSteps;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class LoginUserTest {

    private String email;
    private String password;
    private String name;
    private UserSteps userSteps;
    private User user;
    private User authUser;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
        name = RandomStringUtils.randomAlphanumeric(4, 20);
        email = RandomStringUtils.randomAlphanumeric(6, 10) + "@yandex.ru";
        password = RandomStringUtils.randomAlphanumeric(10, 20);
        userSteps = new UserSteps();
        user = new User(name, email, password);
        authUser = new User();
        userSteps.sendPostRequestApiAuthRegister(user);
    }

    @Test
    public void authorizationTest() {
        Response response = userSteps.sendPostRequestApiAuthLogin(user);
        response.then().log().all()
                .assertThat().body("success", Matchers.is(true))
                .and().body("accessToken", Matchers.notNullValue())
                .and().body("refreshToken", Matchers.notNullValue())
                .and().body("user.email", Matchers.is(user.getEmail().toLowerCase(Locale.ROOT)))
                .and().body("user.name", Matchers.is(name))
                .and().statusCode(200);
    }

    @Test
    public void authorizationWithoutEmailTest() {
        authUser.setPassword(password);
        Response response = userSteps.sendPostRequestApiAuthLogin(authUser);
        userSteps.checkFailedResponseApiAuthLogin(response);

    }

    @Test
    public void authorizationWithoutPasswordTest() {
        authUser.setEmail(email);
        Response response = userSteps.sendPostRequestApiAuthLogin(authUser);
        userSteps.checkFailedResponseApiAuthLogin(response);
    }

    @Test
    public void authorizationWithoutEmailAndPasswordTest() {
        Response response = userSteps.sendPostRequestApiAuthLogin(authUser);
        userSteps.checkFailedResponseApiAuthLogin(response);
    }

    @Test
    public void authorizationWithWrongEmailTest() {
        authUser = new User(name, email, password);
        authUser.setEmail("haha" + email);
        Response response = userSteps.sendPostRequestApiAuthLogin(authUser);
        userSteps.checkFailedResponseApiAuthLogin(response);
    }

    @Test
    public void authorizationWithWrongPasswordTest() {
        authUser = new User(name, email, password);
        authUser.setPassword(password + "8765");
        Response response = userSteps.sendPostRequestApiAuthLogin(authUser);
        userSteps.checkFailedResponseApiAuthLogin(response);
    }

    @After
    public void deleteRandomUser() {
        given().log().all()
                .header("Content-Type", "application/json")
                .body(new User(name, email, password))
                .delete("/api/auth/user");
    }
}
