package keyboard.works.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import keyboard.works.model.GenericResponse;
import keyboard.works.model.request.UnitOfMeasureRequest;
import keyboard.works.model.response.UnitOfMeasureResponse;
import keyboard.works.service.UnitOfMeasureService;
import keyboard.works.utils.GenericResponseHelper;

@RestController
@RequestMapping(
	path = "unit-of-measures",
	produces = MediaType.APPLICATION_JSON_VALUE
)
public class UnitOfMeasureController {

	@Autowired
	private UnitOfMeasureService unitOfMeasureService;
	
	@GetMapping
	public GenericResponse<List<UnitOfMeasureResponse>> getUnitOfMeasures() {
		return GenericResponseHelper.ok(unitOfMeasureService.getUnitOfMeasures());
	}
	
	@GetMapping(path = "{id}")
	public GenericResponse<UnitOfMeasureResponse> getUnitOfMeasure(@PathVariable("id") String id) {
		return GenericResponseHelper.ok(unitOfMeasureService.getUnitOfMeasure(id));
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<UnitOfMeasureResponse> createUnitOfMeasure(@RequestBody UnitOfMeasureRequest request) {
		return GenericResponseHelper.ok(unitOfMeasureService.createUnitOfMeasure(request));
	}
	
	@PutMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<UnitOfMeasureResponse> updateUnitOfMeasure(@PathVariable("id") String id, @RequestBody UnitOfMeasureRequest request) {
		return GenericResponseHelper.ok(unitOfMeasureService.updateUnitOfMeasure(id, request));
	}
	
	@DeleteMapping(path = "{id}")
	public GenericResponse<?> deleteUnitOfMeasure(@PathVariable("id") String id) {
		
		unitOfMeasureService.deleteUnitOfMeasure(id);
		
		return GenericResponseHelper.ok();
	}
	
}
