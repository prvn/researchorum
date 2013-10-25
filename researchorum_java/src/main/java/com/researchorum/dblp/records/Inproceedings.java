package com.researchorum.dblp.records;
import com.researchorum.dblp.util.RecordEnum;
import com.researchorum.dblp.util.Util;



public class Inproceedings extends Record {
	
	private boolean isInproceedingsTag;
	private String booktitle;
	private boolean isBookTitleTag;
	
	public Inproceedings() {
		setInproceedingsTag(true);
		setRecordType(RecordEnum.INPROCEEDINGS);
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

	public boolean isInproceedingsTag() {
		return isInproceedingsTag;
	}

	public void setInproceedingsTag(boolean isInproceedingsTag) {
		this.isInproceedingsTag = isInproceedingsTag;
	}

}
