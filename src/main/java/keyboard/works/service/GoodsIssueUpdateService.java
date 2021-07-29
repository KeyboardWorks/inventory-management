package keyboard.works.service;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import keyboard.works.entity.GoodsIssue;
import keyboard.works.model.request.GoodsIssueRequest;
import keyboard.works.service.validation.group.UpdateData;

@Validated(UpdateData.class)
public interface GoodsIssueUpdateService {

	GoodsIssue updateGoodsIssue(String id, @Valid GoodsIssueRequest request);
	
}
