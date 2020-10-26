package com.valid.pruebapractica.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.valid.pruebapractica.exception.ResourceNotFoundException;
import com.valid.pruebapractica.repository.PersonRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.valid.pruebapractica.model.Personas;

@RestController
@RequestMapping("/api/v1")
@Api(value="Servicios registro personas")
@CrossOrigin(origins = "*")
public class PersonController {

	@Autowired
	private PersonRepository personRepository;

	@ApiOperation(value="Operacion para consulta de personas", notes = "Consulta todas las personas registradas")
	@GetMapping("/persona")
	public List<Personas> getAllPerson() {
		return personRepository.findAll();
	}

	@GetMapping("/persona/{id}")
	@ApiOperation(value="Operacion para consulta de personas por Id", notes = "Consulta de las personas registradas por Id")
	public ResponseEntity<Personas> getPersonaById(@PathVariable(value = "id") Long personaId)
			throws ResourceNotFoundException {
		Personas personas = personRepository.findById(personaId)
				.orElseThrow(() -> new ResourceNotFoundException("Persona no encontrada para este id :: " + personaId));
		return ResponseEntity.ok().body( personas );
	}

	@PostMapping("/persona")
	@ApiOperation(value="Operacion para insercion de personas", notes = "Registra personas")
	public Personas createPersona(@Valid @RequestBody Personas personas) {
		return personRepository.save( personas );
	}

	@PutMapping("/persona/{id}")
	@ApiOperation(value="Operacion para consulta de personas", notes = "Consulta todas las personas registradas")
	public ResponseEntity<Personas> updatePerson(@PathVariable(value = "id") Long personaId,
												   @Valid @RequestBody Personas personasDetails) throws ResourceNotFoundException {
		Personas personas = personRepository.findById(personaId)
				.orElseThrow(() -> new ResourceNotFoundException("Persona no encontrada para este id :: " + personaId));

		personas.setProcesado( personasDetails.getProcesado());
		personas.setApellido( personasDetails.getApellido());
		personas.setNombre( personasDetails.getNombre());
		final Personas updatedPersonas = personRepository.save( personas );
		return ResponseEntity.ok( updatedPersonas );
	}

	@PutMapping("/persona/procesados")
	@ApiOperation(value="Operacion para actualizacion de procesados", notes = "Actualiza procesados")
	public ResponseEntity<String> updateProcess() {
		personRepository.updateProcess();
		return ResponseEntity.ok("process updated");
	}

	@DeleteMapping("/persona/{id}")
	@ApiOperation(value="Operacion para eliminar personas", notes = "Elimina personas registradas en base al id")
	public Map<String, Boolean> deletePerson(@PathVariable(value = "id") Long personaId)
			throws ResourceNotFoundException {
		Personas personas = personRepository.findById(personaId)
				.orElseThrow(() -> new ResourceNotFoundException("Persona no encontrada para este id :: " + personaId));

		personRepository.delete( personas );
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}
