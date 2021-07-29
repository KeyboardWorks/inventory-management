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
public class GoodsIssueItemRequest {

	@NotNull(message = "Id is mandatory", groups = UpdateData.class)
	private String id;
	
	@NotNull(message = "Issued is mandatory", groups = {CreateData.class, UpdateData.class})
	@Positive(message = "Issued must greather than zero", groups = {CreateData.class, UpdateData.class})
	private BigDecimal issued;
	
	@NotNull(message = "Product is mandatory", groups = {CreateData.class, UpdateData.class})
	private String product;

	@NotNull(message = "Product Packaging is mandatory", groups = {CreateData.class, UpdateData.class})
	private String productPackaging;
	
}
