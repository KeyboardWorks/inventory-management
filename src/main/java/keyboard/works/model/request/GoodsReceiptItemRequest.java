package keyboard.works.model.request;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import keyboard.works.service.validation.group.CreateData;
import keyboard.works.service.validation.group.UpdateData;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoodsReceiptItemRequest {

	@NotNull(message = "Receipted is mandatory", groups = UpdateData.class)
	private String id;
	
	@NotNull(message = "Receipted is mandatory", groups = {CreateData.class, UpdateData.class})
	@Positive(message = "Receipted must greather than zero", groups = {CreateData.class, UpdateData.class})
	private BigDecimal receipted;
	
	@NotNull(message = "Price is mandatory", groups = {CreateData.class, UpdateData.class})
	@Positive(message = "Price must greather than zero", groups = {CreateData.class, UpdateData.class})
	private BigDecimal price;
	
	@NotNull(message = "Product To Base is mandatory", groups = {CreateData.class, UpdateData.class})
	private String product;
	
}
