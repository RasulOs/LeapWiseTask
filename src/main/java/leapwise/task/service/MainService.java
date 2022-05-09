package leapwise.task.service;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import leapwise.task.controller.MainController;
import leapwise.task.persistence.model.RootNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import leapwise.task.persistence.CustomerRepo;
import leapwise.task.persistence.ExpressionRepo;
import leapwise.task.persistence.model.Expression;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service
public class MainService {

	private final Logger logger = LogManager.getLogger(MainService.class);
	private final Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

	@Autowired
	private CustomerRepo customerRepo;

	@Autowired
	private ExpressionRepo expressionRepo;
	
	public MainService(CustomerRepo customerRepo, ExpressionRepo expressionRepo) {
		this.customerRepo = customerRepo;
		this.expressionRepo = expressionRepo;
	}
	
	public String executeLogicalExpression(int id, RootNode rootNode) {
		
		customerRepo.save(rootNode);
		
		Expression expression = expressionRepo.getById(id);

		logger.info("Json Validation Schema to persist: {},\n Input boolean expression for the schema evaluation: {}", gson.toJson(rootNode), expression.getValue());
		
		String boolExpression = regexBooleanExpression(expression.getValue());

		logger.info("Replaced boolean expression: {}", boolExpression);
		
		ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine engine = factory.getEngineByName("JavaScript");
        
        String result = "";
        
        try {
			engine.eval("expression = " + gson.toJson(rootNode) + ";");
			result = engine.eval(boolExpression).toString();
			logger.info("Evaluated boolean expression result: {}", result);
			
		} catch (ScriptException e) {
			logger.error("Error while scripting a boolean expression", e);
		}
		
		return result;
	}

	private String regexBooleanExpression(String boolExpression) {
		return boolExpression
				.replaceAll("customer", "expression.customer")
				.replaceAll("(==)", "===")
				.replaceAll("\\b((OR)|(or))\\b", "||")
				.replaceAll("\\b((AND)|(and))\\b", "&&");
	}

	public String saveExpression(Expression expression) {

		expressionRepo.save(expression);

		return Integer.toString(expression.getId());
	}
}
