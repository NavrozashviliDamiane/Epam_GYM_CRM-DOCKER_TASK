package com.epam.crmgym.cucumber.steps;

import com.epam.crmgym.CrmgymApplication;
import com.epam.crmgym.config.JwtService;
import com.epam.crmgym.controller.AuthenticateController;
import com.epam.crmgym.dto.user.LoginRequest;
import com.epam.crmgym.util.user.BlockLoginIpHelper;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.junit.Assert;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import jakarta.servlet.http.HttpServletRequest;

@SpringBootTest(classes = CrmgymApplication.class)
@ActiveProfiles("test")
public class AuthenticationSteps {

    @Autowired
    private AuthenticateController authenticateController;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private BlockLoginIpHelper blockLoginIpHelper;

    private String username;
    private String password;
    private String invalidPassword;
    private ResponseEntity<String> response;

    private HttpServletRequest request;

    @Before
    public void setup() {
        // Mocking HttpServletRequest
        request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getHeader(Mockito.anyString())).thenReturn("127.0.0.1");
        Mockito.when(request.getRemoteAddr()).thenReturn("127.0.0.1");
    }


    @Given("the system has a user with username {string} and password {string}")
    public void theSystemHasAUserWithUsernameAndPassword(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @When("I send a login request with username {string} and password {string}")
    public void iSendALoginRequestWithUsernameAndPassword(String username, String password) {
        LoginRequest loginRequest = new LoginRequest(username, password);

        response = authenticateController.login(loginRequest, request);
    }




    @Then("the user should be authenticated successfully")
    public void theUserShouldBeAuthenticatedSuccessfully() {
        Assert.assertEquals(200, response.getStatusCodeValue());
    }



    @When("I send an invalid login request with username {string} and password {string} three times from the same IP")
    public void iSendAnInvalidLoginRequestWithUsernameAndPasswordThreeTimesFromTheSameIP(String username, String invalidPassword) {
        this.invalidPassword = invalidPassword;

        for (int i = 0; i < 3; i++) {
            LoginRequest loginRequest = new LoginRequest(username, invalidPassword);
            response = authenticateController.login(loginRequest, request);
        }
    }

    @Then("the IP should be blocked")
    public void theIPShouldBeBlocked() {
        String clientIP = blockLoginIpHelper.getClientIP(request); // Replace with actual request object if needed
        Assert.assertTrue(blockLoginIpHelper.isIPBlocked(clientIP));
    }

    @And("further login attempts from that IP should be rejected")
    public void furtherLoginAttemptsFromThatIPShouldBeRejected() {
        LoginRequest loginRequest = new LoginRequest(username, invalidPassword);
        response = authenticateController.login(loginRequest, request);
        Assert.assertEquals(429, response.getStatusCodeValue());
    }

    @Given("a user is logged in with a valid JWT token")
    public void aUserIsLoggedInWithAValidJWTToken() {
        LoginRequest loginRequest = new LoginRequest(username, password);
        response = authenticateController.login(loginRequest, request);
    }



    @Then("the JWT token should be blacklisted")
    public void theJWTTokenShouldBeBlacklisted() {
        String token = response.getBody();
        Assert.assertTrue(jwtService.isTokenBlacklisted(token));
    }
}
