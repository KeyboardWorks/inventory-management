package keyboard.works.model.response;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoodsIssueItemResponse {

	private String id;
	
	private BigDecimal issued;
	
	private ProductResponse product;
	
	private ProductPackagingResponse productPackaging;
	
}
