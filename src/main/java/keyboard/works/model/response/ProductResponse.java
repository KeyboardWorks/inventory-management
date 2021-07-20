package keyboard.works.model.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponse {

	private String id;
	
	private String code;
	
	private String name;
	
	private BaseProductCategoryResponse productCategory;
	
	private List<ProductPackagingResponse> packagings;
	
}
