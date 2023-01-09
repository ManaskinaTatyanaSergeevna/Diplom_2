package tests.user;

import api.model.User;
import api.steps.UserSteps;
import io.restassured.RestAssured;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import io.restassured.response.Response;

import java.util.Locale;

public class CreateUserTest {

    @Before
    public void setUp(){
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }

    @Test
    public void createUserTest(){
        UserSteps userSteps = new UserSteps();
        String name = RandomStringUtils.randomAlphanumeric(4,20);
        String email = RandomStringUtils.randomAlphanumeric(6, 10) + "@yandex.ru";
        String password = RandomStringUtils.randomAlphanumeric(10, 20);
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
    public void createTwoIdenticalUsersTest(){
        UserSteps userSteps = new UserSteps();
        String name = RandomStringUtils.randomAlphanumeric(4,20);
        String email = RandomStringUtils.randomAlphanumeric(6, 10) + "@yandex.ru";
        String password = RandomStringUtils.randomAlphanumeric(10, 20);
        User user = new User(name, email, password);
        userSteps.sendPostRequestApiAuthRegister(user);
        Response response = userSteps.sendPostRequestApiAuthRegister(user);
        response.then().log().all()
                .assertThat().body("success", Matchers.is(false))
                .and().body("message", Matchers.is("User already exists"))
                .and().statusCode(403);
    }

    @Test
    public void createUserWithoutNameTest(){
        UserSteps userSteps = new UserSteps();
        String email = RandomStringUtils.randomAlphanumeric(6, 10) + "@yandex.ru";
        String password = RandomStringUtils.randomAlphanumeric(10, 20);
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        Response response = userSteps.sendPostRequestApiAuthRegister(user);
        response.then().log().all()
                .assertThat().body("success", Matchers.is(false))
                .and().body("message", Matchers.is("Email, password and name are required fields"))
                .and().statusCode(403);
    }

    @Test
    public void createUserWithoutEmailTest(){
        UserSteps userSteps = new UserSteps();
        String name = RandomStringUtils.randomAlphanumeric(4, 20);
        String password = RandomStringUtils.randomAlphanumeric(10, 20);
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        Response response = userSteps.sendPostRequestApiAuthRegister(user);
        response.then().log().all()
                .assertThat().body("success", Matchers.is(false))
                .and().body("message", Matchers.is("Email, password and name are required fields"))
                .and().statusCode(403);
    }

    @Test
    public void createUserWithoutPasswordTest(){
        UserSteps userSteps = new UserSteps();
        String email = RandomStringUtils.randomAlphanumeric(6, 10) + "@yandex.ru";
        String name = RandomStringUtils.randomAlphanumeric(4, 20);
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        Response response = userSteps.sendPostRequestApiAuthRegister(user);
        response.then().log().all()
                .assertThat().body("success", Matchers.is(false))
                .and().body("message", Matchers.is("Email, password and name are required fields"))
                .and().statusCode(403);
    }

    @Test
    public void createUserWithoutNameAndEmailTest(){
        UserSteps userSteps = new UserSteps();
        String password = RandomStringUtils.randomAlphanumeric(10, 20);
        User user = new User();
        user.setPassword(password);
        Response response = userSteps.sendPostRequestApiAuthRegister(user);
        response.then().log().all()
                .assertThat().body("success", Matchers.is(false))
                .and().body("message", Matchers.is("Email, password and name are required fields"))
                .and().statusCode(403);
    }

    @Test
    public void createUserWithoutNameAndPassword(){
        UserSteps userSteps = new UserSteps();
        String email = RandomStringUtils.randomAlphanumeric(6, 10) + "@yandex.ru";
        User user = new User();
        user.setEmail(email);
        Response response = userSteps.sendPostRequestApiAuthRegister(user);
        response.then().log().all()
                .assertThat().body("success", Matchers.is(false))
                .and().body("message", Matchers.is("Email, password and name are required fields"))
                .and().statusCode(403);
    }

    @Test
    public void createUserWithoutEmailAndPassword(){
        UserSteps userSteps = new UserSteps();
        String name = RandomStringUtils.randomAlphanumeric(4, 20);
        User user = new User();
        user.setName(name);
        Response response = userSteps.sendPostRequestApiAuthRegister(user);
        response.then().log().all()
                .assertThat().body("success", Matchers.is(false))
                .and().body("message", Matchers.is("Email, password and name are required fields"))
                .and().statusCode(403);
    }
}
