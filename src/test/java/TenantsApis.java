import com.shaft.api.RestActions;
import com.shaft.driver.DriverFactory;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

public class TenantsApis {

    // getting All Tenants
    @Test
    public void getAllTenants(){
        RestActions apiObject = DriverFactory.getAPIDriver("www.demo.com");
                apiObject.buildNewRequest("tenants", RestActions.RequestType.GET)
                .setTargetStatusCode(200).performRequest();
    }

    // create a new Tenant
    @Test
    public void postTenants(){
        RestActions apiObject = DriverFactory.getAPIDriver("www.demo.com");

        JSONObject createTenantBody = new JSONObject();
        createTenantBody.put("id:","1");
        createTenantBody.put("name:","Ahmed");
        createTenantBody.put("email:","Ahmed@gmail.com");
        createTenantBody.put("phone:","01234567891");

        Response createTenantRes = apiObject.buildNewRequest("tenants", RestActions.RequestType.POST)
                .setContentType(ContentType.JSON)
                .setRequestBody(createTenantBody)
                .setTargetStatusCode(201).performRequest();
        // getting the posted Tenant to assert
        String tenantId = RestActions.getResponseJSONValue(createTenantRes,"id");
       Response getTenantRes = apiObject.buildNewRequest("tenants/" + tenantId, RestActions.RequestType.GET).performRequest();
        String tenantName = RestActions.getResponseJSONValue(getTenantRes,"name");
        String tenantEmail = RestActions.getResponseJSONValue(getTenantRes,"email");
        String tenantPhone = RestActions.getResponseJSONValue(getTenantRes,"phone");
        // assert on the new tenant
        Assertions.assertEquals("Ahmed",tenantName);
        Assertions.assertEquals("Ahmed@gmail.com",tenantEmail);
        Assertions.assertEquals("01234567891",tenantPhone);
    }
    //getting Tenant by id
    @Test
    public void getTenantById(){
        RestActions apiObject = DriverFactory.getAPIDriver("www.demo.com");
        Response getTenantRes = apiObject.buildNewRequest("tenants/" + "1", RestActions.RequestType.GET).setTargetStatusCode(200).performRequest();
        String tenantName = RestActions.getResponseJSONValue(getTenantRes,"name");
        String tenantEmail = RestActions.getResponseJSONValue(getTenantRes,"email");
        String tenantPhone = RestActions.getResponseJSONValue(getTenantRes,"phone");
        // assert on the tenant
        Assertions.assertEquals("Ahmed",tenantName);
        Assertions.assertEquals("Ahmed@gmail.com",tenantEmail);
        Assertions.assertEquals("01234567891",tenantPhone);
    }
    // deleting A Tenant by id
    @Test
    public void deleteTenant(){
        RestActions apiObject = DriverFactory.getAPIDriver("www.demo.com");
        Response deletingTenant = apiObject.buildNewRequest("tenants/" + "1", RestActions.RequestType.DELETE)
                .setTargetStatusCode(204).performRequest();
        // assert on deleted tenant
        String deleteTenantBody = RestActions.getResponseBody(deletingTenant);
        Assertions.assertEquals("deleted",deleteTenantBody);

    }
    // update a Tenant by id
    @Test
    public void updateTenants(){
        RestActions apiObject = DriverFactory.getAPIDriver("www.demo.com");

        JSONObject updateTenantBody = new JSONObject();
        updateTenantBody.put("id:","2");
        updateTenantBody.put("name:","fady");
        updateTenantBody.put("email:","fady@gmail.com");
        updateTenantBody.put("phone:","01231122554");

        Response updateTenantRes = apiObject.buildNewRequest("tenants" + "1", RestActions.RequestType.PUT)
                .setContentType(ContentType.JSON)
                .setRequestBody(updateTenantBody)
                .setTargetStatusCode(200).performRequest();
        // getting the updated Tenant to assert
        String tenantId = RestActions.getResponseJSONValue(updateTenantRes,"id");
        Response getTenantRes = apiObject.buildNewRequest("tenants/" + tenantId, RestActions.RequestType.GET).performRequest();
        String tenantName = RestActions.getResponseJSONValue(getTenantRes,"name");
        String tenantEmail = RestActions.getResponseJSONValue(getTenantRes,"email");
        String tenantPhone = RestActions.getResponseJSONValue(getTenantRes,"phone");
        // assert on the new tenant
        Assertions.assertEquals("fady",tenantName);
        Assertions.assertEquals("fady@gmail.com",tenantEmail);
        Assertions.assertEquals("01231122554",tenantPhone);
    }
}
