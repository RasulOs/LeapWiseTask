package leapwise.task.parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import leapwise.task.persistence.model.RootNode;
import leapwise.task.service.MainService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;


public class JSParser {

    private final Logger logger = LogManager.getLogger(JSParser.class);
    private final Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    public String evaluateExpression(String expression, RootNode root) {

        logger.info("Json Validation Schema to persist: {},\n Input boolean expression for the schema evaluation: {}",
                gson.toJson(root), expression);


        String boolExpression = regexBooleanExpression(expression);

        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine engine = factory.getEngineByName("JavaScript");


        String result = "";

        try {
            engine.eval("expression = " + gson.toJson(root) + ";");
            result = engine.eval(boolExpression).toString();
            logger.info("Evaluated boolean expression result: {}", result);

        } catch (ScriptException e) {
            logger.error("Error while scripting a boolean expression", e);
        }

        return result;
    }

    private String regexBooleanExpression(String expression) {

        logger.info("The logical expression before regex {}", expression);

        String result = expression
                .replaceAll("customer", "expression.customer")
                .replaceAll("(==)", "===")
                .replaceAll("\\b((OR)|(or))\\b", "||")
                .replaceAll("\\b((AND)|(and))\\b", "&&");

        logger.info("The logical expression after regex {}", result);

        return result;
    }
}
