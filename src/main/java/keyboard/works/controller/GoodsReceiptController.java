package keyboard.works.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import keyboard.works.model.GenericResponse;
import keyboard.works.model.request.GoodsReceiptRequest;
import keyboard.works.model.response.GoodsReceiptResponse;
import keyboard.works.service.GoodsReceiptService;
import keyboard.works.utils.GenericResponseHelper;
import keyboard.works.utils.ResponseHelper;

@RestController
@RequestMapping(
	path = "/goods-receipts",
	produces = MediaType.APPLICATION_JSON_VALUE
)
public class GoodsReceiptController {
	
	@Autowired
	private GoodsReceiptService goodsReceiptService;

	@GetMapping
	public GenericResponse<List<GoodsReceiptResponse>> getGoodsReceipts() {
		
		List<GoodsReceiptResponse> responses = ResponseHelper.createResponses(GoodsReceiptResponse.class, goodsReceiptService.getGoodsReceipts());
		
		return GenericResponseHelper.ok(responses);
	}
	
	@GetMapping(path = "{id}")
	public GenericResponse<GoodsReceiptResponse> getGoodsReceipt(@PathVariable("id") String id) {
		
		GoodsReceiptResponse response = ResponseHelper.createResponse(GoodsReceiptResponse.class, goodsReceiptService.getGoodsReceipt(id));
		
		return GenericResponseHelper.ok(response);
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<GoodsReceiptResponse> createGoodsReceipt(@RequestBody GoodsReceiptRequest request) {
		
		GoodsReceiptResponse response = ResponseHelper.createResponse(GoodsReceiptResponse.class, goodsReceiptService.createGoodsReceipt(request));
		
		return GenericResponseHelper.ok(response);
	}
	
	@PutMapping(
		path = "{id}",
		consumes = MediaType.APPLICATION_JSON_VALUE
	)
	public GenericResponse<GoodsReceiptResponse> updateGoodsReceipt(@PathVariable("id") String id, @RequestBody GoodsReceiptRequest request) {
		
		GoodsReceiptResponse response = ResponseHelper.createResponse(GoodsReceiptResponse.class, goodsReceiptService.updateGoodsReceipt(id, request));
		
		return GenericResponseHelper.ok(response);
	}
	
	@DeleteMapping(path = "{id}")
	public GenericResponse<?> deleteGoodsReceipt(@PathVariable("id") String id) {
		
		goodsReceiptService.deleteGoodsReceipt(id);
		
		return GenericResponseHelper.ok();
	}
	
}
