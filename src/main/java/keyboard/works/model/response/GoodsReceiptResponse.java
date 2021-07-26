package keyboard.works.model.response;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoodsReceiptResponse {

	private String id;
	
	private String code;
	
	private LocalDate date;
	
	private String note;
	
	private List<GoodsReceiptItemResponse> items;
	
}
