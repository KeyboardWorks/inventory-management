package keyboard.works.service.impl;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import keyboard.works.entity.UnitOfMeasure;
import keyboard.works.entity.UnitOfMeasureType;
import keyboard.works.model.request.UnitOfMeasureRequest;
import keyboard.works.repository.UnitOfMeasureRepository;
import keyboard.works.service.UnitOfMeasureService;

@Service
@Transactional(rollbackOn = Exception.class)
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

	@Autowired
	private UnitOfMeasureRepository unitOfMeasureRepository;
	
	@Override
	public List<UnitOfMeasure> getUnitOfMeasures() {
		return unitOfMeasureRepository.findAll();
	}

	@Override
	public UnitOfMeasure getUnitOfMeasure(String id) {
		
		UnitOfMeasure unitOfMeasure = loadUnitOfMeasure(id);
		
		return unitOfMeasure;
	}

	@Override
	public UnitOfMeasure createUnitOfMeasure(@Valid UnitOfMeasureRequest request) {
		
		UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
		BeanUtils.copyProperties(request, unitOfMeasure);
		
		unitOfMeasure = unitOfMeasureRepository.save(unitOfMeasure);
		
		return unitOfMeasure;
	}

	@Override
	public UnitOfMeasure updateUnitOfMeasure(String id, @Valid UnitOfMeasureRequest request) {
		
		UnitOfMeasure unitOfMeasure = loadUnitOfMeasure(id);
		BeanUtils.copyProperties(request, unitOfMeasure);
		
		unitOfMeasure = unitOfMeasureRepository.save(unitOfMeasure);
		
		return unitOfMeasure;
	}

	@Override
	public void deleteUnitOfMeasure(String id) {
		
		UnitOfMeasure unitOfMeasure = loadUnitOfMeasure(id);
		
		unitOfMeasureRepository.delete(unitOfMeasure);
	}

	private UnitOfMeasure loadUnitOfMeasure(String id) {
		
		UnitOfMeasure unitOfMeasure = unitOfMeasureRepository.findById(id).orElseThrow(() -> {
			throw new RuntimeException("Unit of measure not found !");
		});
	
		return unitOfMeasure;
	}

	@Override
	public UnitOfMeasure getDefaultUnitOfMeasure() {
		
		UnitOfMeasure unitOfMeasure = unitOfMeasureRepository.findByCode("PCS").orElse(null);
		
		if(unitOfMeasure == null) 
			unitOfMeasure = createDefaultUnitOfMeasure();
			
		return unitOfMeasure;
	}
	
	private UnitOfMeasure createDefaultUnitOfMeasure() {
		
		UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
		unitOfMeasure.setCode("PCS");
		unitOfMeasure.setName("Piece");
		unitOfMeasure.setType(UnitOfMeasureType.UNIT);
		
		unitOfMeasure = unitOfMeasureRepository.save(unitOfMeasure);
		
		return unitOfMeasure;
	}
	
}
