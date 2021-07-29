package keyboard.works.service;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import keyboard.works.entity.GoodsIssue;
import keyboard.works.model.request.GoodsIssueRequest;
import keyboard.works.service.validation.group.CreateData;

@Validated(CreateData.class)
public interface GoodsIssueCreateService {

	GoodsIssue createGoodsIssue(@Valid GoodsIssueRequest request);
	
}
