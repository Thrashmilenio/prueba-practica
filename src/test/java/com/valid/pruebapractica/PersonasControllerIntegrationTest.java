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
	public void testGetAllPersonas() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/persona",
				HttpMethod.GET, entity, String.class);
		
		assertNotNull(response.getBody());
	}

	@Test
	public void testGetPersonasById() {
		Personas persona = restTemplate.getForObject(getRootUrl() + "/persona/1", Personas.class);
		System.out.println( persona.getNombre());
		assertNotNull( persona );
	}

	@Test
	public void testCreatePersonas() {
		Personas persona = new Personas();
		persona.setProcesado("false");
		persona.setNombre("admin");
		persona.setApellido("admin");

		ResponseEntity<Personas> postResponse = restTemplate.postForEntity(getRootUrl() + "/persona", persona, Personas.class);
		assertNotNull(postResponse);
		assertNotNull(postResponse.getBody());
	}

	@Test
	public void testUpdatePersonas() {
		int id = 1;
		Personas persona = restTemplate.getForObject(getRootUrl() + "/persona/" + id, Personas.class);
		persona.setNombre("Jeisson");
		persona.setApellido("Ruiz");

		restTemplate.put(getRootUrl() + "/persona/" + id, persona );

		Personas updatedPersonas = restTemplate.getForObject(getRootUrl() + "/persona/" + id, Personas.class);
		assertNotNull( updatedPersonas );
	}

	@Test
	public void testDeletePersonas() {
		int id = 2;
		Personas persona = restTemplate.getForObject(getRootUrl() + "/persona/" + id, Personas.class);
		assertNotNull( persona );

		restTemplate.delete(getRootUrl() + "/persona/" + id);

		try {
			persona = restTemplate.getForObject(getRootUrl() + "/persona/" + id, Personas.class);
		} catch (final HttpClientErrorException e) {
			assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
		}
	}
}
