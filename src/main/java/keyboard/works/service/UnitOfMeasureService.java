package keyboard.works.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import keyboard.works.model.request.UnitOfMeasureRequest;
import keyboard.works.model.response.UnitOfMeasureResponse;

@Validated
public interface UnitOfMeasureService {

	List<UnitOfMeasureResponse> getUnitOfMeasures();
	
	UnitOfMeasureResponse getUnitOfMeasure(String id);
	
	UnitOfMeasureResponse createUnitOfMeasure(@Valid UnitOfMeasureRequest request);
	
	UnitOfMeasureResponse updateUnitOfMeasure(String id, @Valid UnitOfMeasureRequest request);
	
	void deleteUnitOfMeasure(String id);
	
}
