package kt.reposearch.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class RepoSearchException extends RuntimeException {

	public RepoSearchException(String string) {
		// TODO Auto-generated constructor stub
	}

}
