package leapwise.task.controller;



import leapwise.task.persistence.model.RootNode;
import leapwise.task.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import leapwise.task.persistence.model.Expression;



@RestController
public class MainController {

	@Autowired
	private MainService mainService;

	@PostMapping(path = "/expression")
	public ResponseEntity<String> postExpression(@RequestBody Expression expression) {
		String id = mainService.saveExpression(expression);

		if(!StringUtils.hasLength(id)){
			return ResponseEntity.internalServerError().build();
		}

		return ResponseEntity.ok().body(id);
	}

	@PostMapping(path="/evaluate/{id}")
	public ResponseEntity<String> postEvaluate(@PathVariable int id, @RequestBody RootNode rootNode) {
		String result = mainService.executeLogicalExpression(id, rootNode);

		if(!StringUtils.hasLength(result)){
			return ResponseEntity.internalServerError().build();
		}

		return ResponseEntity.ok().body(result);
	}
}