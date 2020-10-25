package com.valid.pruebapractica.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.valid.pruebapractica.exception.ResourceNotFoundException;
import com.valid.pruebapractica.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.valid.pruebapractica.model.Personas;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class PersonController {

	@Autowired
	private PersonRepository personRepository;

	@GetMapping("/persona")
	public List<Personas> getAllPerson() {
		return personRepository.findAll();
	}

	@GetMapping("/persona/{id}")
	public ResponseEntity<Personas> getPersonaById(@PathVariable(value = "id") Long personaId)
			throws ResourceNotFoundException {
		Personas personas = personRepository.findById(personaId)
				.orElseThrow(() -> new ResourceNotFoundException("Persona no encontrada para este id :: " + personaId));
		return ResponseEntity.ok().body( personas );
	}

	@PostMapping("/persona")
	public Personas createPersona(@Valid @RequestBody Personas personas) {
		return personRepository.save( personas );
	}

	@PutMapping("/persona/{id}")
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
	public ResponseEntity<String> updateProcess() {
		personRepository.updateProcess();
		return ResponseEntity.ok("process updated");
	}

	@DeleteMapping("/persona/{id}")
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
