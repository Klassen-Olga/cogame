package contract;

import contract.BaseClass;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification;
import io.restassured.response.ResponseOptions;

import static org.springframework.cloud.contract.verifier.assertion.SpringCloudContractAssertions.assertThat;
import static org.springframework.cloud.contract.verifier.util.ContractVerifierUtil.*;
import static com.toomuchcoding.jsonassert.JsonAssertion.assertThatJson;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;

@SuppressWarnings("rawtypes")
public class ContractVerifierTest extends BaseClass {

	@Test
	public void validate_shouldReturnMessages() throws Exception {
		// given:
			MockMvcRequestSpecification request = given();


		// when:
			ResponseOptions response = given().spec(request)
					.get("/events/20/messages");

		// then:
			assertThat(response.statusCode()).isEqualTo(200);
			assertThat(response.header("Content-Type")).isEqualTo("application/json");

		// and:
			DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
			assertThatJson(parsedJson).array().contains("['id']").isEqualTo("21");
			assertThatJson(parsedJson).array().contains("['userId']").isEqualTo("21");
			assertThatJson(parsedJson).array().contains("['userName']").isEqualTo("Dom");
			assertThatJson(parsedJson).array().contains("['eventId']").isEqualTo("20");
			assertThatJson(parsedJson).array().contains("['text']").isEqualTo("Great Event, Dom");
			assertThatJson(parsedJson).array().contains("['createdAt']").isEqualTo("2023-01-01T14:00");
			assertThatJson(parsedJson).array().contains("['id']").isEqualTo("22");
			assertThatJson(parsedJson).array().contains("['userId']").isEqualTo("22");
			assertThatJson(parsedJson).array().contains("['userName']").isEqualTo("Bom");
			assertThatJson(parsedJson).array().contains("['text']").isEqualTo("Great Event, Bom");
			assertThatJson(parsedJson).array().contains("['createdAt']").isEqualTo("2023-01-01T13:00");
	}

}
