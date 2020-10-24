package com.valid.pruebapractica;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import com.valid.pruebapractica.model.Personas;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PersonasControllerIntegrationTest {
	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	private String getRootUrl() {
		return "http://localhost:" + port;
	}

	@Test
	public void contextLoads() {

	}

	@Test
	public void testGetAllEmployees() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/employees",
				HttpMethod.GET, entity, String.class);
		
		assertNotNull(response.getBody());
	}

	@Test
	public void testGetEmployeeById() {
		Personas personas = restTemplate.getForObject(getRootUrl() + "/employees/1", Personas.class);
		System.out.println( personas.getNombre());
		assertNotNull( personas );
	}

	@Test
	public void testCreateEmployee() {
		Personas personas = new Personas();
		personas.setProcesado("admin@gmail.com");
		personas.setNombre("admin");
		personas.setApellido("admin");

		ResponseEntity<Personas> postResponse = restTemplate.postForEntity(getRootUrl() + "/employees", personas, Personas.class);
		assertNotNull(postResponse);
		assertNotNull(postResponse.getBody());
	}

	@Test
	public void testUpdateEmployee() {
		int id = 1;
		Personas personas = restTemplate.getForObject(getRootUrl() + "/employees/" + id, Personas.class);
		personas.setNombre("admin1");
		personas.setApellido("admin2");

		restTemplate.put(getRootUrl() + "/employees/" + id, personas );

		Personas updatedPersonas = restTemplate.getForObject(getRootUrl() + "/employees/" + id, Personas.class);
		assertNotNull( updatedPersonas );
	}

	@Test
	public void testDeleteEmployee() {
		int id = 2;
		Personas personas = restTemplate.getForObject(getRootUrl() + "/employees/" + id, Personas.class);
		assertNotNull( personas );

		restTemplate.delete(getRootUrl() + "/employees/" + id);

		try {
			personas = restTemplate.getForObject(getRootUrl() + "/employees/" + id, Personas.class);
		} catch (final HttpClientErrorException e) {
			assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
		}
	}
}
