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

public class CreateUserTest {

    private String name;
    private String email;
    private String password;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }

    @Before
    public void createRandomData() {
        name = RandomStringUtils.randomAlphanumeric(4, 20);
        email = RandomStringUtils.randomAlphanumeric(6, 10) + "@yandex.ru";
        password = RandomStringUtils.randomAlphanumeric(10, 20);
    }


    @Test
    public void createUserTest() {
        UserSteps userSteps = new UserSteps();
        User user = new User(name, email, password);
        Response response = userSteps.sendPostRequestApiAuthRegister(user);
        response.then().log().all()
                .assertThat().body("success", Matchers.is(true))
                .and().body("user.email", Matchers.is(email.toLowerCase(Locale.ROOT)))
                .and().body("user.name", Matchers.is(name))
                .and().body("accessToken", Matchers.notNullValue())
                .and().body("refreshToken", Matchers.notNullValue())
                .and().statusCode(200);
    }


    @Test
    public void createTwoIdenticalUsersTest() {
        UserSteps userSteps = new UserSteps();
        User user = new User(name, email, password);
        userSteps.sendPostRequestApiAuthRegister(user);
        Response response = userSteps.sendPostRequestApiAuthRegister(user);
        response.then().log().all()
                .assertThat().body("success", Matchers.is(false))
                .and().body("message", Matchers.is("User already exists"))
                .and().statusCode(403);
    }

    @Test
    public void createUserWithoutNameTest() {
        UserSteps userSteps = new UserSteps();
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        Response response = userSteps.sendPostRequestApiAuthRegister(user);
        userSteps.checkFailedResponseApiAuthRegister(response);
    }

    @Test
    public void createUserWithoutEmailTest() {
        UserSteps userSteps = new UserSteps();
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        Response response = userSteps.sendPostRequestApiAuthRegister(user);
        userSteps.checkFailedResponseApiAuthRegister(response);
    }

    @Test
    public void createUserWithoutPasswordTest() {
        UserSteps userSteps = new UserSteps();
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        Response response = userSteps.sendPostRequestApiAuthRegister(user);
        userSteps.checkFailedResponseApiAuthRegister(response);
    }

    @Test
    public void createUserWithoutNameAndEmailTest() {
        UserSteps userSteps = new UserSteps();
        User user = new User();
        user.setPassword(password);
        Response response = userSteps.sendPostRequestApiAuthRegister(user);
        userSteps.checkFailedResponseApiAuthRegister(response);
    }

    @Test
    public void createUserWithoutNameAndPasswordTest() {
        UserSteps userSteps = new UserSteps();
        User user = new User();
        user.setEmail(email);
        Response response = userSteps.sendPostRequestApiAuthRegister(user);
        userSteps.checkFailedResponseApiAuthRegister(response);
    }

    @Test
    public void createUserWithoutEmailAndPasswordTest() {
        UserSteps userSteps = new UserSteps();
        User user = new User();
        user.setName(name);
        Response response = userSteps.sendPostRequestApiAuthRegister(user);
        userSteps.checkFailedResponseApiAuthRegister(response);
    }

    @Test
    public void createUserWithoutAllTest() {
        UserSteps userSteps = new UserSteps();
        Response response = userSteps.sendPostRequestApiAuthRegister(new User());
        userSteps.checkFailedResponseApiAuthRegister(response);
    }

    @After
    public void deleteRandomUser() {
        given().log().all()
                .header("Content-Type", "application/json")
                .body(new User(name, email, password))
                .delete("/api/auth/user");
    }
}
