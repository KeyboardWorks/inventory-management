package keyboard.works.service.impl;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import keyboard.works.entity.UnitOfMeasure;
import keyboard.works.model.request.UnitOfMeasureRequest;
import keyboard.works.model.response.UnitOfMeasureResponse;
import keyboard.works.repository.UnitOfMeasureRepository;
import keyboard.works.service.UnitOfMeasureService;
import keyboard.works.utils.ResponseHelper;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

	@Autowired
	private UnitOfMeasureRepository unitOfMeasureRepository;
	
	@Override
	public List<UnitOfMeasureResponse> getUnitOfMeasures() {
		return ResponseHelper.createResponses(UnitOfMeasureResponse.class, unitOfMeasureRepository.findAll());
	}

	@Override
	public UnitOfMeasureResponse getUnitOfMeasure(String id) {
		
		UnitOfMeasure unitOfMeasure = loadUnitOfMeasure(id);
		
		return ResponseHelper.createResponse(UnitOfMeasureResponse.class, unitOfMeasure);
	}

	@Override
	public UnitOfMeasureResponse createUnitOfMeasure(@Valid UnitOfMeasureRequest request) {
		
		UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
		BeanUtils.copyProperties(request, unitOfMeasure);
		
		unitOfMeasure = unitOfMeasureRepository.save(unitOfMeasure);
		
		return ResponseHelper.createResponse(UnitOfMeasureResponse.class, unitOfMeasure);
	}

	@Override
	public UnitOfMeasureResponse updateUnitOfMeasure(String id, @Valid UnitOfMeasureRequest request) {
		
		UnitOfMeasure unitOfMeasure = loadUnitOfMeasure(id);
		BeanUtils.copyProperties(request, unitOfMeasure);
		
		unitOfMeasure = unitOfMeasureRepository.save(unitOfMeasure);
		
		return ResponseHelper.createResponse(UnitOfMeasureResponse.class, unitOfMeasure);
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
	
}
