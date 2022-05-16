package leapwise.task.service;

import leapwise.task.parser.BooleanExpressionParser;
import leapwise.task.parser.JSParser;
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

		String result = "";

		if (expression.getName().trim().toLowerCase().equals("complex logical expression")) {
			BooleanExpressionParser booleanParser = new BooleanExpressionParser();

			result = booleanParser.evaluateExpression(expression.getValue(), rootNode);

			logger.info("Evaluated boolean expression result: {}", result);

		}  else if (expression.getName().trim().toLowerCase().equals("javascript engine")) {

			JSParser javaScriptParser = new JSParser();

			result = javaScriptParser.evaluateExpression(expression.getValue(), rootNode);
			logger.info("Evaluated JavaScript expression result: {}", result);
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
