package kt.reposearch.helpers;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNProperties;
import org.tmatesoft.svn.core.io.SVNRepository;

public class SearchRepositoryHelper {
	 private static final String CLAZZ_NAME = SearchRepositoryHelper.class.getName();
	    private static final Logger _logger = Logger.getLogger(CLAZZ_NAME);

	    List fileDetailsList = null;
	   // DateTimeUtils util = DateTimeUtils.


	    public List searchFile(String keyword, String url, SVNRepository repository, boolean searchTags,
	                           boolean searchBranches, boolean searchTrunk, boolean caseSensitive, String fileTypes,
	                           String folderPatterns) throws Exception {
	    	
	        String methodName = "searchFile()";
	        _logger.info("Start:-------------------"+methodName);
	        fileDetailsList = new ArrayList();

	        try {


	            SVNNodeKind nodeKind = repository.checkPath("", -1);

	            if (nodeKind == SVNNodeKind.NONE) {
	                _logger.logp(Level.INFO, CLAZZ_NAME, methodName, "There is no entry at '" + url + "'.");
	                throw new Exception("There is no entry at '" + url + "'.");
	            } else if (nodeKind == SVNNodeKind.DIR) {
	                listEntries(repository, "", keyword, searchTags, searchBranches, searchTrunk, caseSensitive, fileTypes,
	                            folderPatterns);


	            } else if (nodeKind == SVNNodeKind.FILE) {
	                _logger.logp(Level.INFO, CLAZZ_NAME, methodName, "The entry at '" + url + "' is a file!");
	                throw new Exception("The entry at '" + url + "' is a file!");
	            }

	        } catch (SVNException se) {
	            _logger.logp(Level.INFO, CLAZZ_NAME, methodName, "Unable to connect to SVN!");
	            throw new Exception("Unable to connect to SVN!");
	        } catch (Exception e) {
	            _logger.logp(Level.INFO, CLAZZ_NAME, methodName, "Not a valid URL!");
	            throw new Exception("Not a valid URL!");
	        }
	        _logger.info("End:-------------------"+methodName);
	        return fileDetailsList;
	    }

	    private boolean readFile(SVNRepository repository, String path, String keyword,
	                             boolean caseSensitive) throws SVNException {
	    	String methodName="readFile";
	    	  _logger.info("Strat:-------------------"+methodName);
	        String fileString = "";
	        boolean isKeywordFound = false;
	        SVNProperties svnFileProperties = new SVNProperties();
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        repository.getFile(path, -1, svnFileProperties, baos);

	        fileString = baos.toString();
	        if (caseSensitive) {
	            if (fileString.indexOf(keyword) > -1) {
	                isKeywordFound = true;
	            }
	        } else {
	            if (fileString.toLowerCase().indexOf(keyword.toLowerCase()) > -1) {
	                isKeywordFound = true;
	            }
	        }

	      //  String methodName="readFile";
	    	  _logger.info("End:-------------------"+methodName);
	        return isKeywordFound;
	    }

	    private void listEntries(SVNRepository repository, String path, String keyword, boolean searchTags,
	                             boolean searchBranches, boolean searchTrunk, boolean caseSensitive, String fileTypes,
	                             String folderPatterns) throws SVNException {
	        String methodName = "listEntries()";
	       // String methodName="readFile";
	    	  _logger.info("Strat:-------------------"+methodName);
	        boolean isKeywordFound = false;
	        Collection entries = repository.getDir(path, -1, null, (Collection) null);
	        Iterator iterator = entries.iterator();
	        while (iterator.hasNext()) {
	            SVNDirEntry entry = (SVNDirEntry) iterator.next();

	            if (entry.getKind() == SVNNodeKind.DIR) {
	                boolean folderPatternFound = checkFolderPattern(entry.getName(), folderPatterns);
	                if ((!searchTags && "tags".equals(entry.getName())) ||
	                    (!searchBranches && "branches".equals(entry.getName())) ||
	                    (!searchTrunk && "trunk".equals(entry.getName())) ||
	                    (folderPatterns != null && (!folderPatterns.equals("")) && folderPatternFound)) {
	                    _logger.logp(Level.FINEST, CLAZZ_NAME, methodName, "Ignored :: " + entry.getName());
	                    continue;
	                }

	                listEntries(repository, (path.equals("")) ? entry.getName() : path + "/" + entry.getName(), keyword,
	                            searchTags, searchBranches, searchTrunk, caseSensitive, fileTypes, folderPatterns);
	            } else if (entry.getKind() == SVNNodeKind.FILE) {
	                boolean fileTypeFound = checkFilePattern(entry.getName(), fileTypes);
	                if (fileTypes != null && (!fileTypes.equals("")) && (!fileTypeFound)) {
	                    _logger.logp(Level.FINEST, CLAZZ_NAME, methodName, "Ignored :: " + entry.getName());
	                    continue;
	                }
	                isKeywordFound =
	                    readFile(repository, (path.equals("")) ? entry.getName() : path + "/" + entry.getName(), keyword,
	                             caseSensitive);
	                _logger.logp(Level.FINEST, CLAZZ_NAME, methodName,
	                             entry.getURL() + "  Keyword found :: " + isKeywordFound);
	                if (isKeywordFound) {
	                    fileDetailsList.add(entry.getName() + "," + entry.getURL() + "," + entry.getAuthor() + "," 
	                                         + entry.getRevision());

	                }
	            }
	        }
	      //  String methodName="readFile";
	    	  _logger.info("End:-------------------"+methodName);
	    }

	    private static boolean checkFilePattern(String name, String patterns) {
	    	String methodName="checkFilePattern";
	    	  _logger.info("Strat:-------------------"+methodName);
	        boolean patternFound = false;
	        String patternsList[] = patterns.split(",");
	        for (int i = 0; i < patternsList.length; i++) {
	            String pattern = patternsList[i];
	            if (name.toLowerCase().indexOf("." + pattern.toLowerCase()) > -1) {
	                patternFound = true;
	                break;
	            }
	        }
	       // String methodName="checkFilePattern";
	    	  _logger.info("End:-------------------"+methodName);
	        return patternFound;
	    }

	    private static boolean checkFolderPattern(String name, String patterns) {
	    	String methodName="checkFolderPattern";
	    	  _logger.info("Strat:-------------------"+methodName);
	        boolean patternFound = false;
	        String patternsList[] = patterns.split(",");
	        for (int i = 0; i < patternsList.length; i++) {
	            String pattern = patternsList[i];
	            if (name.toLowerCase().indexOf(pattern.toLowerCase()) > -1) {
	                patternFound = true;
	                break;
	            }
	        }
	        _logger.info("End:-------------------"+methodName);
	        return patternFound;
	    }

}
