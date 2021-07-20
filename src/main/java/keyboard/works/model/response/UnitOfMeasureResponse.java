package keyboard.works.model.response;

import keyboard.works.entity.UnitOfMeasureType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnitOfMeasureResponse {

	private String id;
	
	private String code;
	
	private String name;
	
	private UnitOfMeasureType type;
	
}
