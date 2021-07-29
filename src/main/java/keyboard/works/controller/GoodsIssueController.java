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
import keyboard.works.model.request.GoodsIssueRequest;
import keyboard.works.model.response.GoodsIssueResponse;
import keyboard.works.service.GoodsIssueService;
import keyboard.works.utils.GenericResponseHelper;
import keyboard.works.utils.ResponseHelper;

@RestController
@RequestMapping(
	path = "/goods-issues",
	produces = MediaType.APPLICATION_JSON_VALUE
)
public class GoodsIssueController {

	@Autowired
	private GoodsIssueService goodsIssueService;
	
	@GetMapping
	public GenericResponse<List<GoodsIssueResponse>> getGoodsIssues() {
		
		List<GoodsIssueResponse> responses = ResponseHelper.createResponses(GoodsIssueResponse.class, goodsIssueService.getGoodsIssues());
		
		return GenericResponseHelper.ok(responses);
	}
	
	@GetMapping(path = "{id}")
	public GenericResponse<GoodsIssueResponse> getGoodsIssue(@PathVariable("id") String id) {
		
		GoodsIssueResponse response = ResponseHelper.createResponse(GoodsIssueResponse.class, goodsIssueService.getGoodsIssue(id));
		
		return GenericResponseHelper.ok(response);
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<GoodsIssueResponse> createGoodsIssue(@RequestBody GoodsIssueRequest request) {
		
		GoodsIssueResponse response = ResponseHelper.createResponse(GoodsIssueResponse.class, goodsIssueService.createGoodsIssue(request));
		
		return GenericResponseHelper.ok(response);
	}
	
	@PutMapping(
		path = "{id}",
		consumes = MediaType.APPLICATION_JSON_VALUE
	)
	public GenericResponse<GoodsIssueResponse> updateGoodsIssue(@PathVariable("id") String id, @RequestBody GoodsIssueRequest request) {
		
		GoodsIssueResponse response = ResponseHelper.createResponse(GoodsIssueResponse.class, goodsIssueService.updateGoodsIssue(id, request));
		
		return GenericResponseHelper.ok(response);
	}
	
	@DeleteMapping(path = "{id}")
	public GenericResponse<?> deleteGoodsIssue(@PathVariable("id") String id) {
		
		goodsIssueService.deleteGoodsIssue(id);
		
		return GenericResponseHelper.ok();
	}
	
}
