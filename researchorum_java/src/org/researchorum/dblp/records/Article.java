package org.researchorum.dblp.records;
import org.researchorum.dblp.util.RecordEnum;
import org.researchorum.dblp.util.Util;



public class Article extends Record{
	
	private boolean isArticleTag;
	private String journal;
	private boolean isJournalTag;
	
	public Article() {
		setArticleTag(true);
		setRecordType(RecordEnum.ARTICLE);
	}

	public String getJournal() {
		return journal;
	}

	public void setJournal(String journal) {
		this.journal = journal;
	}

	public boolean isArticleTag() {
		return isArticleTag;
	}

	public void setArticleTag(boolean isArticle) {
		this.isArticleTag = isArticle;
	}
	
	public boolean isJournalTag() {
		return isJournalTag;
	}

	public void setJournalTag(boolean isJournalTag) {
		this.isJournalTag = isJournalTag;
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
        if(Util.JOURNAL_TAG.equals(rawName)) {
        	setJournalTag(true);
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
        if(isJournalTag()) {
        	this.setJournal(fieldValue);
        	setJournalTag(false);
        }
        if(isPublisherTag()) {
        	this.setPublisher(fieldValue);
        	setPublisherTag(false);
        }
	}
}
