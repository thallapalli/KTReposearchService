package kt.reposearch.dtos;

public class RepoSearchResponseDTO {

	private String fileName;
	private String filePath;
	private String  modifiedDateTime;
    private String fileRevision;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getModifiedDateTime() {
		return modifiedDateTime;
	}
	public void setModifiedDateTime(String modifiedDateTime) {
		this.modifiedDateTime = modifiedDateTime;
	}
	public String getFileRevision() {
		return fileRevision;
	}
	public void setFileRevision(String fileRevision) {
		this.fileRevision = fileRevision;
	}
	public RepoSearchResponseDTO(String fileName, String filePath, String modifiedDateTime, String fileRevision) {
		super();
		this.fileName = fileName;
		this.filePath = filePath;
		this.modifiedDateTime = modifiedDateTime;
		this.fileRevision = fileRevision;
	}
	public RepoSearchResponseDTO() {
		super();
	}
    

}
