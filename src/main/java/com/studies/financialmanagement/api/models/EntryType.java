package com.studies.financialmanagement.api.models;

public enum EntryType {

    EARNING("Earning"),
    EXPENSE("Expense");

    private final String description;

    EntryType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
