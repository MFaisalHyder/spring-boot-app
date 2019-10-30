package com.spring.project.itc;

import com.spring.project.config.BaseTest_SBT;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This example is done with @SpringBootTest. This is recommended way to carry out Integration Testing.
 * In integration testing we are running complete HTTP servers and testing our complete feature from controller to DB
 * i.e. tests are carried out with actual environment instead of mocks.
 */
//@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class) //Now we don't need it as we now use Security
@DisplayName("UserControllerTest_SBT - SpringBootTest")
class UserControllerTest_SBT extends BaseTest_SBT {

    private TestRestTemplate restTemplate;
    // private MockMvc mockMvc;

    /*
     Constructor injection is not suitable for test classes as JUnit 5 latest version requires Parameter Resolver for
     passed params. Also It is not recommended to use MockMvc with SpringBootTest as we are loading whole context, we
     can use RestTemplate or TestRestTemplate directly instead of Mocking.
     */
    @Autowired
    public UserControllerTest_SBT(TestRestTemplate testRestTemplate) {
        this.restTemplate = testRestTemplate;
    }

    // We can either annotate test case with a principal user or pass it in MockMvc/TestRestTemplate
    // @WithUserDetails("jionel.Pepsi@barka.com")
    @Test
    void getUsersListTest() {
        String USER = "jionel.Pepsi@barka.com";
        /*
        this.mockMvc.perform(MockMvcRequestBuilders.get("/user/listAll")
                .with(user(USER))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(ApplicationConstants.GeneralConstants.SUCCESS.getValue())))
                .andDo(print());
        */

        ResponseEntity<Map> response = this.restTemplate.withBasicAuth(USER, "12345mno")
                .getForEntity("/user/listAll", Map.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /*
    static class CustomParameterResolver implements ParameterResolver {

        @Override
        public boolean supportsParameter(ParameterContext parameterContext,
                                         ExtensionContext extensionContext) throws ParameterResolutionException {
            return (parameterContext.getParameter().getType() == TestRestTemplate.class);
        }

        @Override
        public Object resolveParameter(ParameterContext parameterContext,
                                       ExtensionContext extensionContext) throws ParameterResolutionException {
            return new TestRestTemplate();
        }
    }
    */
}