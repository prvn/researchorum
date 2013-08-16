package org.researchorum.dblp.records;

import java.util.ArrayList;
import java.util.List;

import org.researchorum.dblp.util.PublicationEnum;
import org.researchorum.dblp.util.RecordEnum;


public abstract class Record {
	private RecordEnum recordType;
	private PublicationEnum publicationType;
	private List<String> authorsList = new ArrayList<String>();
	private String title;
	private String year;
	private String sourceUrl;
	private String publisher;
	
	private boolean isTitleTag;
	private boolean isAuthorTag;
	private boolean isYearTag;
	private boolean isSourceUrlTag;
	private boolean isPublisherTag;
	
	public abstract void processTags(String rawName);
	public abstract void populateFields(String fieldValue);

	public RecordEnum getRecordType() {
		return recordType;
	}

	public void setRecordType(RecordEnum recordType) {
		this.recordType = recordType;
	}

	public PublicationEnum getPublicationType() {
		return publicationType;
	}

	public void setPublicationType(PublicationEnum publicationType) {
		this.publicationType = publicationType;
	}

	public List<String> getAuthorsList() {
		return authorsList;
	}

	public void setAuthorsList(List<String> authorsList) {
		this.authorsList = authorsList;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	public boolean isTitleTag() {
		return isTitleTag;
	}

	public void setTitleTag(boolean isTitleTag) {
		this.isTitleTag = isTitleTag;
	}

	public boolean isAuthorTag() {
		return isAuthorTag;
	}

	public void setAuthorTag(boolean isAuthorTag) {
		this.isAuthorTag = isAuthorTag;
	}

	public boolean isYearTag() {
		return isYearTag;
	}

	public void setYearTag(boolean isYearTag) {
		this.isYearTag = isYearTag;
	}

	public boolean isSourceUrlTag() {
		return isSourceUrlTag;
	}

	public void setSourceUrlTag(boolean isSourceUrlTag) {
		this.isSourceUrlTag = isSourceUrlTag;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public boolean isPublisherTag() {
		return isPublisherTag;
	}
	public void setPublisherTag(boolean isPublisherTag) {
		this.isPublisherTag = isPublisherTag;
	}
	
	
}
