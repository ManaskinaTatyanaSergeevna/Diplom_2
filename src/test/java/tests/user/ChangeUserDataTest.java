package tests.user;

import api.model.User;
import api.steps.UserSteps;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

public class ChangeUserDataTest {

    private final static String OLD_NAME = "Username";
    private final static String OLD_EMAIL = "test-data@yandex.ru";
    private final static String OLD_PASSWORD = "password";

    @Before
    public void setUp(){
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }

    @Test
    public void changeUserNameWithAuthorizationTest(){
        String newName = "Tatyana";
        UserSteps userSteps = new UserSteps();
        User user = new User(OLD_EMAIL, OLD_PASSWORD);
        String accessToken = userSteps.getAccessTokenForChangeUserData(user);
        User changeUser = new User();
        changeUser.setName(newName);
        Response response = userSteps.sendPatchRequestWithAuthorizationApiAuthUser(changeUser, accessToken);
        userSteps.checkSuccessResponseApiAuthUser(response, OLD_EMAIL, newName);

        //возврат к изначальному значению имени пользователя
        changeUser.setName(OLD_NAME);
        userSteps.sendPatchRequestWithAuthorizationApiAuthUser(changeUser, accessToken);
    }

    @Test
    public void changeUserEmailWithAuthorizationTest(){
        String newEmail = "hochyChobiMenyaPohvalili@yandex.ru";
        UserSteps userSteps = new UserSteps();
        User user = new User(OLD_EMAIL, OLD_PASSWORD);
        String accessToken = userSteps.getAccessTokenForChangeUserData(user);
        User changeUser = new User();
        changeUser.setEmail(newEmail);
        Response response = userSteps.sendPatchRequestWithAuthorizationApiAuthUser(changeUser, accessToken);
        userSteps.checkSuccessResponseApiAuthUser(response, newEmail, OLD_NAME);

        //возврат к изначальному значению почты пользователя
        changeUser.setEmail(OLD_EMAIL);
        userSteps.sendPatchRequestWithAuthorizationApiAuthUser(changeUser, accessToken);
    }

    @Test
    public void changeUserPasswordWithAuthorizationTest(){
        String newPassword = "diplomNa100ballov";
        UserSteps userSteps = new UserSteps();
        User user = new User(OLD_EMAIL, OLD_PASSWORD);
        String accessToken = userSteps.getAccessTokenForChangeUserData(user);
        User changeUser = new User();
        changeUser.setPassword(newPassword);
        Response response = userSteps.sendPatchRequestWithAuthorizationApiAuthUser(changeUser, accessToken);
        userSteps.checkSuccessResponseApiAuthUser(response, OLD_EMAIL, OLD_NAME);

        //возврат к изначальному значению пароля пользователя
        changeUser.setPassword(OLD_PASSWORD);
        userSteps.sendPatchRequestWithAuthorizationApiAuthUser(changeUser, accessToken);
    }

    @Test
    public void changeUserNameAndEmailWithAuthorizationTest(){
        String newEmail = "hochyChobiMenyaPohvalili@yandex.ru";
        String newName = "TatyanaSergeevna";
        UserSteps userSteps = new UserSteps();
        User user = new User(OLD_EMAIL, OLD_PASSWORD);
        String accessToken = userSteps.getAccessTokenForChangeUserData(user);
        User changeUser = new User();
        changeUser.setEmail(newEmail);
        changeUser.setName(newName);
        Response response = userSteps.sendPatchRequestWithAuthorizationApiAuthUser(changeUser, accessToken);
        userSteps.checkSuccessResponseApiAuthUser(response, newEmail, newName);

        //возврат к изначальному значению почты и имени пользователя
        changeUser.setEmail(OLD_EMAIL);
        changeUser.setName(OLD_NAME);
        userSteps.sendPatchRequestWithAuthorizationApiAuthUser(changeUser, accessToken);
    }

    @Test
    public void changeUserNameAndPasswordWithAuthorizationTest(){
        String newPassword = "HappyNewYear!!!AMneSdachiDiploma";
        String newName = "TatyanaSergeevna";
        UserSteps userSteps = new UserSteps();
        User user = new User(OLD_EMAIL, OLD_PASSWORD);
        String accessToken = userSteps.getAccessTokenForChangeUserData(user);
        User changeUser = new User();
        changeUser.setPassword(newPassword);
        changeUser.setName(newName);
        Response response = userSteps.sendPatchRequestWithAuthorizationApiAuthUser(changeUser, accessToken);
        userSteps.checkSuccessResponseApiAuthUser(response, OLD_EMAIL, newName);

        //возврат к изначальному значению пароля и имени пользователя
        changeUser.setPassword(OLD_PASSWORD);
        changeUser.setName(OLD_NAME);
        userSteps.sendPatchRequestWithAuthorizationApiAuthUser(changeUser, accessToken);
    }

    @Test
    public void changeUserEmailAndPasswordWithAuthorizationTest(){
        String newPassword = "YaObyazatel'noSpravlus'!";
        String newEmail = "vsemLucheiDobra@yandex.ru";
        UserSteps userSteps = new UserSteps();
        User user = new User(OLD_EMAIL, OLD_PASSWORD);
        String accessToken = userSteps.getAccessTokenForChangeUserData(user);
        User changeUser = new User();
        changeUser.setPassword(newPassword);
        changeUser.setEmail(newEmail);
        Response response = userSteps.sendPatchRequestWithAuthorizationApiAuthUser(changeUser, accessToken);
        userSteps.checkSuccessResponseApiAuthUser(response, newEmail, OLD_NAME);

        //возврат к изначальному значению пароля и почты пользователя
        changeUser.setPassword(OLD_PASSWORD);
        changeUser.setEmail(OLD_EMAIL);
        userSteps.sendPatchRequestWithAuthorizationApiAuthUser(changeUser, accessToken);
    }

    @Test
    public void changeAllUserFieldsWithAuthorizationTest(){
        String newPassword = "Zdorov'yaSamoeGlavnoe";
        String newName = "ManaskinaTatyanaSergeevna";
        String newEmail = "GlavnoePotomNaityRabotu@yandex.ru";
        UserSteps userSteps = new UserSteps();
        User user = new User(OLD_EMAIL, OLD_PASSWORD);
        String accessToken = userSteps.getAccessTokenForChangeUserData(user);
        User changeUser = new User(newName, newEmail, newPassword);
        Response response = userSteps.sendPatchRequestWithAuthorizationApiAuthUser(changeUser, accessToken);
        userSteps.checkSuccessResponseApiAuthUser(response, newEmail, newName);

        //возврат к изначальному значению почты, пароля и имени пользователя
        changeUser.setEmail(OLD_EMAIL);
        changeUser.setName(OLD_NAME);
        changeUser.setPassword(OLD_PASSWORD);
        userSteps.sendPatchRequestWithAuthorizationApiAuthUser(changeUser, accessToken);
    }



    @Test
    public void changeUserNameWithoutAuthorizationTest(){
        String newName = "Tatyana";
        UserSteps userSteps = new UserSteps();
        User changeUser = new User();
        changeUser.setName(newName);
        Response response = userSteps.sendPatchRequestWithoutAuthorizationApiAuthUser(changeUser);
        userSteps.checkFailedResponseApiAuthUser(response);
    }

    @Test
    public void changeUserEmailWithoutAuthorizationTest(){
        String newEmail = "hochyChobiMenyaPohvalili@yandex.ru";
        UserSteps userSteps = new UserSteps();
        User changeUser = new User();
        changeUser.setName(newEmail);
        Response response = userSteps.sendPatchRequestWithoutAuthorizationApiAuthUser(changeUser);
        userSteps.checkFailedResponseApiAuthUser(response);
    }

    @Test
    public void changeUserPasswordWithoutAuthorizationTest(){
        String newPassword = "diplomNa100ballov";
        UserSteps userSteps = new UserSteps();
        User changeUser = new User();
        changeUser.setName(newPassword);
        Response response = userSteps.sendPatchRequestWithoutAuthorizationApiAuthUser(changeUser);
        userSteps.checkFailedResponseApiAuthUser(response);
    }

    @Test
    public void changeUserNameAndEmailWithoutAuthorizationTest(){
        String newName = "TatyanaSergeevna";
        String newEmail = "hochyChobiMenyaPohvalili@yandex.ru";
        UserSteps userSteps = new UserSteps();
        User changeUser = new User();
        changeUser.setName(newName);
        changeUser.setEmail(newEmail);
        Response response = userSteps.sendPatchRequestWithoutAuthorizationApiAuthUser(changeUser);
        userSteps.checkFailedResponseApiAuthUser(response);
    }

    @Test
    public void changeUserNameAndPasswordWithoutAuthorizationTest(){
        String newPassword = "diplomNa100ballov";
        String newName = "TatyanaSergeevna";
        UserSteps userSteps = new UserSteps();
        User changeUser = new User();
        changeUser.setName(newPassword);
        changeUser.setName(newName);
        Response response = userSteps.sendPatchRequestWithoutAuthorizationApiAuthUser(changeUser);
        userSteps.checkFailedResponseApiAuthUser(response);
    }

    @Test
    public void changeUserEmailAndPasswordWithoutAuthorizationTest(){
        String newEmail = "hochyChobiMenyaPohvalili@yandex.ru";
        String newPassword = "diplomNa100ballov";
        UserSteps userSteps = new UserSteps();
        User changeUser = new User();
        changeUser.setName(newEmail);
        changeUser.setPassword(newPassword);
        Response response = userSteps.sendPatchRequestWithoutAuthorizationApiAuthUser(changeUser);
        userSteps.checkFailedResponseApiAuthUser(response);
    }

    @Test
    public void changeAllUserFieldsWithoutAuthorizationTest(){
        String newEmail = "hochyChobiMenyaPohvalili@yandex.ru";
        String newPassword = "diplomNa100ballov";
        String newName = "TatyanaSergeevna";
        UserSteps userSteps = new UserSteps();
        User changeUser = new User();
        changeUser.setName(newEmail);
        changeUser.setPassword(newPassword);
        changeUser.setName(newName);
        Response response = userSteps.sendPatchRequestWithoutAuthorizationApiAuthUser(changeUser);
        userSteps.checkFailedResponseApiAuthUser(response);
    }

}
