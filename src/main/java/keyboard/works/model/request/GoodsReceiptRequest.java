package keyboard.works.model.request;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import keyboard.works.service.validation.group.CreateData;
import keyboard.works.service.validation.group.UpdateData;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoodsReceiptRequest {

	@NotNull(message = "Code is mandatory", groups = {CreateData.class, UpdateData.class})
	@NotBlank(message = "Code cannot blank", groups = {CreateData.class, UpdateData.class})
	@Size(message = "Code length must greather than 4 ", min = 5, groups = {CreateData.class, UpdateData.class})
	private String code;

	@NotNull(message = "Date is mandatory", groups = {CreateData.class, UpdateData.class})
	private LocalDate date;
	
	private String note;
	
	@NotNull(message = "Items is mandatory", groups = {CreateData.class, UpdateData.class})
	@Size(min = 1)
	@Valid
	private List<GoodsReceiptItemRequest> items;
	
}
