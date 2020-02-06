package kt.reposearch.util;

import java.util.logging.Logger;

import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

public class RepositoryUtil {
	 private static final String CLAZZ_NAME = RepositoryUtil.class.getName();
	    private static final Logger _logger = Logger.getLogger(CLAZZ_NAME);

	public static void setupRepository() {
		DAVRepositoryFactory.setup();
		SVNRepositoryFactoryImpl.setup();
		FSRepositoryFactory.setup();
	}

	public static SVNRepository autheticateRepository(String name, String password, String url) throws SVNException {
		String methodName="autheticateRepository";
  	  _logger.info("Strat:-------------------"+methodName);
		SVNRepository repository = null;

		repository = SVNRepositoryFactory.create(SVNURL.parseURIDecoded(url));
		ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(name, password);
		repository.setAuthenticationManager(authManager);
		repository.testConnection();
		 _logger.info("End:-------------------"+methodName);
		return repository;
	}

	public static long calculateSearchTime(long startTime, long endTime) throws SVNException {
		long difference = endTime - startTime;
		return difference;
	}
}
