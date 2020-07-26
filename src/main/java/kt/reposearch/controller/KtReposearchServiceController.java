package kt.reposearch.controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.tmatesoft.svn.core.io.SVNRepository;

import kt.reposearch.constants.RepoSearchModelContants;
import kt.reposearch.dtos.RepoSearchRequestDTO;
import kt.reposearch.dtos.RepoSearchResponseDTO;
import kt.reposearch.exception.RepoSearchException;
import kt.reposearch.helpers.SearchRepositoryHelper;
import kt.reposearch.util.RepositoryUtil;

@RestController
public class KtReposearchServiceController {
	private static final String CLAZZ_NAME = KtReposearchServiceController.class.getName();
	private static final Logger _logger = Logger.getLogger(CLAZZ_NAME);

	@GetMapping(value = "/")
	public String sayHello() {
		return "Hello!!! Welcome To KT RepoSearch !! Searching SVN repo is made easy";
	}
	
	@GetMapping(value = "/searchrepo")
	public RepoSearchResponseDTO executeRepositorySearch(@RequestBody RepoSearchRequestDTO repoSearchRequestDTO) {
		String methodName = "executeRepositorySearch()";
		_logger.entering(CLAZZ_NAME, methodName);
		RepoSearchResponseDTO repoSearchResponseDTO = null;
		;
		String message = RepoSearchModelContants.SUCCESS;
		SVNRepository repository = null;
		List filesList = null;

		String username = repoSearchRequestDTO.getUsername();
		String password = repoSearchRequestDTO.getPassword();
		String url = repoSearchRequestDTO.getUrl();
		String keyword = repoSearchRequestDTO.getKeyword();
		String fileTypes = repoSearchRequestDTO.getFileTypes();
		String folderPatterns = repoSearchRequestDTO.getFolderPatterns();
		boolean isTags = repoSearchRequestDTO.isTags();
		boolean isBranches = repoSearchRequestDTO.isBranches();
		boolean isTrunk = repoSearchRequestDTO.isTrunk();
		boolean isCaseSensitive = repoSearchRequestDTO.isCaseSensitive();
		try {
			RepositoryUtil.setupRepository();
			repository = RepositoryUtil.autheticateRepository(username, password, url);
		} catch (Exception e) {

		}
		try {
			long startTime = System.currentTimeMillis();

			filesList = new SearchRepositoryHelper().searchFile(keyword, url, repository, isTags, isBranches, isTrunk,
					isCaseSensitive, fileTypes, folderPatterns);

			long endTime = System.currentTimeMillis();

			_logger.logp(Level.INFO, CLAZZ_NAME, methodName, "Time taken for the search :: "
					+ RepositoryUtil.calculateSearchTime(startTime, endTime) / 1000 + " seconds");

			if (filesList.size() == 0) {
				// message = getLocalizedValue("error.no.records");
				throw new RepoSearchException("no Records");
			} else {
				for (Object obj : filesList) {
					if (obj != null && StringUtils.isNotBlank(obj.toString())) {
						String filesStr = obj.toString();
						String[] filesArr = StringUtils.split(filesStr, ",");
						if (filesArr.length == 5) {
							repoSearchResponseDTO = new RepoSearchResponseDTO();
							repoSearchResponseDTO.setFileName(filesArr[0]);
							repoSearchResponseDTO.setFilePath(filesArr[1]);
							repoSearchResponseDTO.setFileName(filesArr[2]);
							repoSearchResponseDTO.setModifiedDateTime(filesArr[3]);
							repoSearchResponseDTO.setFileRevision(filesArr[4]);

						}

					}

				}
				// req.setAttribute("FilesList", filesList);
			}

		} catch (OutOfMemoryError ome) {
			_logger.logp(Level.INFO, CLAZZ_NAME, methodName, "OutOfMemoryError", ome);
			// message = getLocalizedValue("error.search.memory");
			throw new RepoSearchException("OutOfMemoryError");
		} catch (Exception e) {
			_logger.logp(Level.INFO, CLAZZ_NAME, methodName, "Exception", e);
			// message = getLocalizedValue("error.search");
			throw new RepoSearchException("Generic Exception");
		}

		_logger.exiting(CLAZZ_NAME, methodName);
		return repoSearchResponseDTO;
	}
	
}
