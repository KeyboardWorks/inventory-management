package keyboard.works.model.request;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoodsReceiptRequest {

	@NotNull(message = "Code is mandatory")
	@NotBlank(message = "Code cannot blank")
	@Size(message = "Code length must greather than 4 ", min = 5)
	private String code;

	@NotNull(message = "Date is mandatory")
	private LocalDate date;
	
	private String note;
	
	@NotNull(message = "Items is mandatory")
	private List<GoodsReceiptItemRequest> items;
	
}
