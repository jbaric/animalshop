package sk.baric.animalshop.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

/**
 * Throw this exception, when no product ids are defined by the user during submiting an order.
 * 
 * @author Juraj Baric
 *
 */
@SuppressWarnings("serial")
public class ValueAlreadyExistsException extends HttpClientErrorException {

	private final String entityName;

	private final String columnName;

	private final String columnValue;
	
	public ValueAlreadyExistsException(String entityName, String columnName, String columnValue) {
		super(HttpStatus.UNPROCESSABLE_ENTITY);
		this.entityName = entityName;
		this.columnName = columnName;
		this.columnValue = columnValue;
	}

	public String getColumnName() {
		return columnName;
	}

	public String getColumnValue() {
		return columnValue;
	}
	
	public String getEntityName() {
		return entityName;
	}

	@Override
	public String getMessage() {
		return entityName + " with " + columnName + " '" + columnValue + "' already exists";
	}

}
