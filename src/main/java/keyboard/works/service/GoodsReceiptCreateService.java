package keyboard.works.service;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import keyboard.works.entity.GoodsReceipt;
import keyboard.works.model.request.GoodsReceiptRequest;
import keyboard.works.service.validation.group.CreateData;

@Validated(CreateData.class)
public interface GoodsReceiptCreateService {

	GoodsReceipt createGoodsReceipt(@Valid GoodsReceiptRequest request);
	
}
