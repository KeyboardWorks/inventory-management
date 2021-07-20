package keyboard.works.model.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import keyboard.works.entity.UnitOfMeasureType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnitOfMeasureRequest {

	@NotNull(message = "Code is mandatory")
	@Size(min = 5, message = "Code length must greather than 4")
	private String code;
	
	@NotNull(message = "Name is mandatory")
	@Size(min = 5, message = "Name length must greather than 4")
	private String name;
	
	@NotNull(message = "Type is mandatory")
	private UnitOfMeasureType type;
}
