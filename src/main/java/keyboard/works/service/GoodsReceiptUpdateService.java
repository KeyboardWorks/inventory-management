package keyboard.works.service;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import keyboard.works.entity.GoodsReceipt;
import keyboard.works.model.request.GoodsReceiptRequest;
import keyboard.works.service.validation.group.UpdateData;

@Validated(UpdateData.class)
public interface GoodsReceiptUpdateService {

	GoodsReceipt updateGoodsReceipt(String id, @Valid GoodsReceiptRequest request);
	
}
