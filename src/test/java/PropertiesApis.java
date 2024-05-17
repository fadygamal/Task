import com.shaft.api.RestActions;
import com.shaft.driver.DriverFactory;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

public class PropertiesApis {

    // getting All Properties
    @Test
    public void getAllProperties(){
        RestActions apiObject = DriverFactory.getAPIDriver("www.demo.com");
        apiObject.buildNewRequest("Properties", RestActions.RequestType.GET)
                .setTargetStatusCode(200).performRequest();
    }

    // create a new Property
    @Test
    public void postProperty(){
        RestActions apiObject = DriverFactory.getAPIDriver("www.demo.com");

        JSONObject createPropertiesBody = new JSONObject();
        createPropertiesBody.put("id:","1");
        createPropertiesBody.put("name:","Ahmed");
        createPropertiesBody.put("address:","St101");
        createPropertiesBody.put("rent:",1000);
        createPropertiesBody.put("is_available","Yes");

        Response createPropertiesRes = apiObject.buildNewRequest("Properties", RestActions.RequestType.POST)
                .setContentType(ContentType.JSON)
                .setRequestBody(createPropertiesBody)
                .setTargetStatusCode(201).performRequest();
        // getting the posted Properties to assert
        String PropertyId = RestActions.getResponseJSONValue(createPropertiesRes,"id");
        Response getPropertyRes = apiObject.buildNewRequest("Properties/" + PropertyId, RestActions.RequestType.GET).performRequest();
        String PropertyName = RestActions.getResponseJSONValue(getPropertyRes,"name");
        String PropertyAddress = RestActions.getResponseJSONValue(getPropertyRes,"address");
        String PropertyRent = RestActions.getResponseJSONValue(getPropertyRes,"rent");
        String PropertyIs_available = RestActions.getResponseJSONValue(getPropertyRes,"is_available");
        // assert on the new Property
        Assertions.assertEquals("Ahmed",PropertyName);
        Assertions.assertEquals("St101",PropertyAddress);
        Assertions.assertEquals("1000",PropertyRent);
        Assertions.assertEquals("Yes",PropertyIs_available);
    }
    //getting Property by id
    @Test
    public void getPropertyById(){
        RestActions apiObject = DriverFactory.getAPIDriver("www.demo.com");
        Response getPropertyRes = apiObject.buildNewRequest("Properties/" + "1", RestActions.RequestType.GET).performRequest();
        String PropertyName = RestActions.getResponseJSONValue(getPropertyRes,"name");
        String PropertyAddress = RestActions.getResponseJSONValue(getPropertyRes,"address");
        String PropertyRent = RestActions.getResponseJSONValue(getPropertyRes,"rent");
        String PropertyIs_available = RestActions.getResponseJSONValue(getPropertyRes,"is_available");
        // assert on the Property
        Assertions.assertEquals("Ahmed",PropertyName);
        Assertions.assertEquals("St101",PropertyAddress);
        Assertions.assertEquals("1000",PropertyRent);
        Assertions.assertEquals("Yes",PropertyIs_available);
    }
    // deleting A Property by id
    @Test
    public void deleteProperty(){
        RestActions apiObject = DriverFactory.getAPIDriver("www.demo.com");
        Response deletingProperty = apiObject.buildNewRequest("Properties/" + "1", RestActions.RequestType.DELETE)
                .setTargetStatusCode(204).performRequest();
        // assert on deleted Property
        String deletePropertytBody = RestActions.getResponseBody(deletingProperty);
        Assertions.assertEquals("deleted",deletePropertytBody);

    }
    // update a Property by id
    @Test
    public void updateProperty(){
        RestActions apiObject = DriverFactory.getAPIDriver("www.demo.com");

        JSONObject updatePropertiesBody = new JSONObject();
        updatePropertiesBody.put("id:","2");
        updatePropertiesBody.put("name:","fady");
        updatePropertiesBody.put("address:","St11");
        updatePropertiesBody.put("rent:",100);
        updatePropertiesBody.put("is_available","No");

        Response updatePropertyRes = apiObject.buildNewRequest("Properties/" + "1", RestActions.RequestType.PUT)
                .setContentType(ContentType.JSON)
                .setRequestBody(updatePropertiesBody)
                .setTargetStatusCode(200).performRequest();
        // getting the updated Property to assert
        String PropertyId = RestActions.getResponseJSONValue(updatePropertyRes,"id");
        Response getPropertyRes = apiObject.buildNewRequest("Properties/" + PropertyId, RestActions.RequestType.GET).performRequest();
        String PropertyName = RestActions.getResponseJSONValue(getPropertyRes,"name");
        String PropertyAddress = RestActions.getResponseJSONValue(getPropertyRes,"address");
        String PropertyRent = RestActions.getResponseJSONValue(getPropertyRes,"rent");
        String PropertyIs_available = RestActions.getResponseJSONValue(getPropertyRes,"is_available");
        // assert on the Property
        Assertions.assertEquals("fady",PropertyName);
        Assertions.assertEquals("St11",PropertyAddress);
        Assertions.assertEquals("100",PropertyRent);
        Assertions.assertEquals("No",PropertyIs_available);
    }
}
