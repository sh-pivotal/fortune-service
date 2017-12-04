package smoke;

import org.assertj.core.api.BDDAssertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

/**
 * @author Marcin Grzejszczak
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntegrationTests.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
@EnableAutoConfiguration
public class IntegrationTests {

	@Value("${application.url}") String applicationUrl;

	RestTemplate restTemplate = new RestTemplate();

	@Test
	public void should_return_a_foo_fortune() {
		ResponseEntity<String> response = this.restTemplate
				.getForEntity("http://" + this.applicationUrl + "/", String.class);

		BDDAssertions.then(response.getStatusCodeValue()).isEqualTo(200);

		// Filter out the known Hystrix fallback responses
		BDDAssertions.then(response.getBody()).doesNotContain("The fortuneteller will be back soon.");
	}

}

