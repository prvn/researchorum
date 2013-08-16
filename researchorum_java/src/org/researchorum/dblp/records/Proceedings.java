package org.researchorum.dblp.records;

import java.util.ArrayList;
import java.util.List;

import org.researchorum.dblp.util.RecordEnum;
import org.researchorum.dblp.util.Util;


public class Proceedings extends Record {
	
	private boolean isProceedingsTag;
	private String booktitle;
	private boolean isBookTitleTag;
	private boolean isEditorTag;
	private List<String> editorList = new ArrayList<String>();;
	
	public Proceedings() {
		setProceedingsTag(true);
		setRecordType(RecordEnum.PROCEEDINGS);
	}

	@Override
	public void processTags(String rawName) {
		if(Util.TITLE_TAG.equals(rawName)) {
        	setTitleTag(true);
        	return;
        }
        if(Util.AUTHOR_TAG.equals(rawName)) {
        	setAuthorTag(true);
        	return;
        }
        if(Util.EDITOR_TAG.equals(rawName)) {
        	setEditorTag(true);
        	return;
        }
        if(Util.YEAR_TAG.equals(rawName)) {
        	setYearTag(true);
        	return;
        }
        if(Util.SOURCE_URL_TAG.equals(rawName)) {
        	setSourceUrlTag(true);
        	return;
        }
        if(Util.BOOKTITLE_TAG.equals(rawName)) {
        	setBookTitleTag(true);
        	return;
        }
        if(Util.PUBLISHER_TAG.equals(rawName)) {
        	setPublisherTag(true);
        	return;
        }
	}

	@Override
	public void populateFields(String fieldValue) {
		if(isTitleTag()) {
            this.setTitle(fieldValue);
            setTitleTag(false);
        }
        if(isAuthorTag()) {
        	this.getAuthorsList().add(fieldValue);
        	setAuthorTag(false);
        }
        if(isEditorTag()) {
        	this.getEditorList().add(fieldValue);
        	setEditorTag(false);
        }
        if(isYearTag()) {
        	this.setYear(fieldValue);
        	setYearTag(false);
        }
        if(isSourceUrlTag()) {
        	this.setSourceUrl(fieldValue);
        	setSourceUrlTag(false);
        }
        if(isBookTitleTag()) {
        	this.setBooktitle(fieldValue);
        	setBookTitleTag(false);
        }
        if(isPublisherTag()) {
        	this.setPublisher(fieldValue);
        	setPublisherTag(false);
        }
	}

	public boolean isProceedingsTag() {
		return isProceedingsTag;
	}

	public void setProceedingsTag(boolean isProceedingsTag) {
		this.isProceedingsTag = isProceedingsTag;
	}

	public boolean isBookTitleTag() {
		return isBookTitleTag;
	}

	public void setBookTitleTag(boolean isBookTitleTag) {
		this.isBookTitleTag = isBookTitleTag;
	}

	public String getBooktitle() {
		return booktitle;
	}

	public void setBooktitle(String booktitle) {
		this.booktitle = booktitle;
	}

	public List<String> getEditorList() {
		return editorList;
	}

	public void setEditorList(List<String> editorList) {
		this.editorList = editorList;
	}

	public boolean isEditorTag() {
		return isEditorTag;
	}

	public void setEditorTag(boolean isEditorTag) {
		this.isEditorTag = isEditorTag;
	}

}
