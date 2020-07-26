package kt.reposearch.dtos;

public class RepoSearchRequestDTO {
	private String username;
	private String password;
	private String url;
	private String keyword;
	boolean isTags;
    boolean isBranches;
    boolean isTrunk;
    boolean isCaseSensitive;
    private String fileTypes ;
    private String folderPatterns;

	public RepoSearchRequestDTO() {
		super();
	}
	
	public RepoSearchRequestDTO(String username, String password, String url, String keyword, boolean isTags,
			boolean isBranches, boolean isTrunk, boolean isCaseSensitive, String fileTypes,String folderPatterns) {
		super();
		this.username = username;
		this.password = password;
		this.url = url;
		this.keyword = keyword;
		this.isTags = isTags;
		this.isBranches = isBranches;
		this.isTrunk = isTrunk;
		this.isCaseSensitive = isCaseSensitive;
		this.fileTypes = fileTypes;
		this.folderPatterns=folderPatterns;
	}

	public String getFolderPatterns() {
		return folderPatterns;
	}

	public void setFolderPatterns(String folderPatterns) {
		this.folderPatterns = folderPatterns;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public boolean isTags() {
		return isTags;
	}

	public void setTags(boolean isTags) {
		this.isTags = isTags;
	}

	public boolean isBranches() {
		return isBranches;
	}

	public void setBranches(boolean isBranches) {
		this.isBranches = isBranches;
	}

	public boolean isTrunk() {
		return isTrunk;
	}

	public void setTrunk(boolean isTrunk) {
		this.isTrunk = isTrunk;
	}

	public boolean isCaseSensitive() {
		return isCaseSensitive;
	}

	public void setCaseSensitive(boolean isCaseSensitive) {
		this.isCaseSensitive = isCaseSensitive;
	}

	public String getFileTypes() {
		return fileTypes;
	}

	public void setFileTypes(String fileTypes) {
		this.fileTypes = fileTypes;
	}
	
	
}
