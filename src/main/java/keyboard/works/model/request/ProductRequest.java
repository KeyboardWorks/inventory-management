package keyboard.works.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {

	@NotNull(message = "Code is mandatory")
	@NotBlank(message = "Code cannot blank")
	@Size(message = "Code length must greather than 4 ", min = 5)
	private String code;
	
	@NotNull(message = "Name is mandatory")
	@NotBlank(message = "Name cannot blank")
	@Size(message = "Name length must greather than 4 ", min = 5)
	private String name;
	
	@NotNull(message = "Unit Of Measure is mandatory")
	@NotBlank(message = "Unit Of Measure cannot blank")
	private String unitOfMeasure;
	
}
