package keyboard.works.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import keyboard.works.entity.UnitOfMeasure;
import keyboard.works.model.request.UnitOfMeasureRequest;

@Validated
public interface UnitOfMeasureService {

	List<UnitOfMeasure> getUnitOfMeasures();
	
	UnitOfMeasure getUnitOfMeasure(String id);
	
	UnitOfMeasure createUnitOfMeasure(@Valid UnitOfMeasureRequest request);
	
	UnitOfMeasure updateUnitOfMeasure(String id, @Valid UnitOfMeasureRequest request);
	
	void deleteUnitOfMeasure(String id);
	
}
