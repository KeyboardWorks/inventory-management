package keyboard.works.model.request;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductPackagingRequest {

	@NotNull(message = "Quantity To Base is mandatory")
	@Positive(message = "Quantity To Base must greather than zero")
	private BigDecimal quantityToBase;
	
	@NotNull(message = "Unit Of Measure is mandatory")
	@NotBlank(message = "Unit Of Measure cannot blank")
	private String unitOfMeasure;
	
	@NotNull(message = "Product is mandatory")
	@NotBlank(message = "Product cannot blank")
	private String product;
	
}
