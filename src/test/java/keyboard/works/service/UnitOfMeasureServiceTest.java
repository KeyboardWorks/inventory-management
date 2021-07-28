package keyboard.works.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import keyboard.works.entity.UnitOfMeasure;
import keyboard.works.entity.UnitOfMeasureType;
import keyboard.works.model.request.UnitOfMeasureRequest;

@SpringBootTest
@Sql(scripts = {"/sql/unit-of-measure-delete.sql", "/sql/unit-of-measure-insert.sql"})
public class UnitOfMeasureServiceTest {

	@Autowired
	private UnitOfMeasureService unitOfMeasureService;
	
	@Test
	public void getUnitOfMeasuresTest() {
		
		List<UnitOfMeasure> unitOfMeasures = unitOfMeasureService.getUnitOfMeasures();
		
		assertNotNull(unitOfMeasures);
		assertEquals(2, unitOfMeasures.size());
	}
	
	@Test
	@Sql(scripts = "/sql/unit-of-measure-delete.sql")
	public void getUnitOfMeasures_EmptyTest() {
		
		List<UnitOfMeasure> unitOfMeasure = unitOfMeasureService.getUnitOfMeasures();
		
		assertNotNull(unitOfMeasure);
		assertEquals(0, unitOfMeasure.size());
	}
	
	@Test
	public void getUnitOfMeasureTest() {
		
		UnitOfMeasure unitOfMeasure = unitOfMeasureService.getUnitOfMeasure("001");
		
		assertNotNull(unitOfMeasure);
	}
	
	@Test
	public void getUnitOfMeasure_NotFoundTest() {
		
		assertThrows(EntityNotFoundException.class, () -> {
			unitOfMeasureService.getUnitOfMeasure("003");
		});
		
	}
	
	@Test
	public void createUnitOfMeasureTest() {
		
		UnitOfMeasureRequest request = new UnitOfMeasureRequest();
		request.setCode("Unit Of Measure Code 3");
		request.setName("Unit Of Measure Name 3");
		request.setType(UnitOfMeasureType.AREA);
		
		UnitOfMeasure unitOfMeasure = unitOfMeasureService.createUnitOfMeasure(request);
	
		assertNotNull(unitOfMeasure);
		assertNotNull(unitOfMeasure.getId());
	}
	
	@Test
	public void createUnitOfMeasure_ValidationTest() {
		UnitOfMeasureRequest request = new UnitOfMeasureRequest();
		
		assertThrows(ConstraintViolationException.class, () -> {
			unitOfMeasureService.createUnitOfMeasure(request);
		});
		
	}
	
	@Test
	public void updateUnitOfMeasureTest() {
		
		UnitOfMeasureRequest request = new UnitOfMeasureRequest();
		request.setCode("Unit Of Measure Code 3");
		request.setName("Unit Of Measure Name 3");
		request.setType(UnitOfMeasureType.WEIGHT);
		
		UnitOfMeasure unitOfMeasure = unitOfMeasureService.updateUnitOfMeasure("001", request);
		
		assertNotNull(unitOfMeasure);
		assertEquals("Unit Of Measure Code 3", unitOfMeasure.getCode());
		assertEquals("Unit Of Measure Name 3", unitOfMeasure.getName());
		assertEquals(UnitOfMeasureType.WEIGHT, unitOfMeasure.getType());
		
	}
	
	@Test
	public void updateUnitOfMeasure_NotFoundTest() {
		UnitOfMeasureRequest request = new UnitOfMeasureRequest();
		request.setCode("Unit Of Measure Code 3");
		request.setName("Unit Of Measure Name 3");
		request.setType(UnitOfMeasureType.WEIGHT);
		
		assertThrows(EntityNotFoundException.class, () -> {
			unitOfMeasureService.updateUnitOfMeasure("003", request);
		});
	}
	
	@Test
	public void updateUnitOfMeasure_ValidationTest() {
		UnitOfMeasureRequest request = new UnitOfMeasureRequest();
		
		assertThrows(ConstraintViolationException.class, () -> {
			unitOfMeasureService.updateUnitOfMeasure("001", request);
		});
	}
	
	@Test
	public void deleteUnitOfMeasureTest() {
		
		unitOfMeasureService.deleteUnitOfMeasure("001");
		
		assertThrows(EntityNotFoundException.class, () -> {
			unitOfMeasureService.getUnitOfMeasure("001");
		});
		
	}
	
	@Test
	public void deleteUnitOfMeasure_NotFoundTest() {
		
		assertThrows(EntityNotFoundException.class, () -> {
			unitOfMeasureService.getUnitOfMeasure("003");
		});
		
	}
	
	@Test
	public void getDefaultUnitOfMeasureTest() {
		
		UnitOfMeasure unitOfMeasure = unitOfMeasureService.getDefaultUnitOfMeasure();
		
		assertNotNull(unitOfMeasure);
		assertNotNull(unitOfMeasure.getId());
		assertEquals("PCS", unitOfMeasure.getCode());
		assertEquals("Piece", unitOfMeasure.getName());
		assertEquals(UnitOfMeasureType.UNIT, unitOfMeasure.getType());
		
	}
	
}
