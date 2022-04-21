package com.relations.relations.many2many.common;
public enum QuestionCategory {
    SPORT("SPORT"),
    BUSINESS("BUSINESS"),
    EDUCATION("EDUCATION"),
    HUMAN("HUMAN");
    private final String category;
    QuestionCategory(String category){
        this.category =category;
    }
    public String getStatus() {
        return this.category;
    }
}
