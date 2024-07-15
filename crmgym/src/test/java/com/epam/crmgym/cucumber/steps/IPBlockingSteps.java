package com.epam.crmgym.cucumber.steps;

import com.epam.crmgym.CrmgymApplication;
import com.epam.crmgym.controller.AuthenticateController;
import com.epam.crmgym.dto.user.LoginRequest;
import com.epam.crmgym.util.user.BlockLoginIpHelper;
import io.cucumber.java.en.*;
import org.junit.Assert;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import jakarta.servlet.http.HttpServletRequest;

@SpringBootTest(classes = CrmgymApplication.class)
@ActiveProfiles("test")
public class IPBlockingSteps {

    @Autowired
    private AuthenticateController authenticateController;

    @Autowired
    private BlockLoginIpHelper blockLoginIpHelper;

    private HttpServletRequest request;
    private ResponseEntity<String> response;

    @Given("an IP address is blocked due to too many unsuccessful login attempts")
    public void anIPAddressIsBlockedDueToTooManyUnsuccessfulLoginAttempts() {
        // Mocking HttpServletRequest
        request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getHeader(Mockito.anyString())).thenReturn("127.0.0.1");
        Mockito.when(request.getRemoteAddr()).thenReturn("127.0.0.1");

        String clientIP = blockLoginIpHelper.getClientIP(request);
        blockLoginIpHelper.blockIP(clientIP);
    }

    @When("I send a login request from the blocked IP")
    public void iSendALoginRequestFromTheBlockedIP() {
        LoginRequest loginRequest = new LoginRequest("blockedUser", "blockedPass");

        // Mocking HttpServletRequest
        request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getHeader(Mockito.anyString())).thenReturn("127.0.0.1");
        Mockito.when(request.getRemoteAddr()).thenReturn("127.0.0.1");

        response = authenticateController.login(loginRequest, request);
    }

    @Then("the request should be rejected with a status of too many requests")
    public void theRequestShouldBeRejectedWithAStatusOfTooManyRequests() {
        Assert.assertEquals(429, response.getStatusCodeValue());
    }
}
