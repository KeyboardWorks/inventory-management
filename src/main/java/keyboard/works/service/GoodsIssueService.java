package keyboard.works.service;

import java.util.List;

import keyboard.works.entity.GoodsIssue;

public interface GoodsIssueService extends GoodsIssueCreateService, GoodsIssueUpdateService {

	List<GoodsIssue> getGoodsIssues();
	
	GoodsIssue getGoodsIssue(String id);
	
	void deleteGoodsIssue(String id);
	
}
