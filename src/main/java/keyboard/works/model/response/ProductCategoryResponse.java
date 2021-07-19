package keyboard.works.model.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCategoryResponse extends BaseProductCategoryResponse {

	private BaseProductCategoryResponse parent;
	
	private List<BaseProductCategoryResponse> childs;
	
}
