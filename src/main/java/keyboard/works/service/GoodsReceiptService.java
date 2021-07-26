package keyboard.works.service;

import java.util.List;

import keyboard.works.entity.GoodsReceipt;

public interface GoodsReceiptService extends GoodsReceiptCreateService, GoodsReceiptUpdateService {

	List<GoodsReceipt> getGoodsReceipts();
	
	GoodsReceipt getGoodsReceipt(String id);
	
	void deleteGoodsReceipt(String id);
	
}
