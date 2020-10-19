package sk.baric.animalshop.rest.exceptions;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

/**
 * Throw when some products on an submited order are missing
 * 
 * @author Juraj Baric
 *
 */
@SuppressWarnings("serial")
public class MissingProductsException extends HttpClientErrorException {
	
	private final HashSet<Long> ids;

	public MissingProductsException(HashSet<Long> ids) {
		super(HttpStatus.UNPROCESSABLE_ENTITY);
		this.ids = ids;
	}

	/**
	 * @return the ids
	 */
	public HashSet<Long> getIds() {
		return ids;
	}
	
	@Override
	public String getMessage() {
		Set<String> idsTexts = ids.stream().map(id -> id.toString()).collect(Collectors.toSet());
		String idsText = String.join(", ", idsTexts);
		return "Missing products: " + idsText;
	}

}
