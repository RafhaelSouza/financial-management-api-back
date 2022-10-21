package com.studies.financialmanagement.api.repositories.listener;

import com.studies.financialmanagement.FinancialManagementApplication;
import com.studies.financialmanagement.api.models.Entry;
import com.studies.financialmanagement.api.storage.S3;
import org.springframework.util.StringUtils;

import javax.persistence.PostLoad;

public class AttachmentEntryListener {

    /*@PostLoad
    public void postLoad(Entry entry) {
        if (StringUtils.hasText(entry.getAttachment())) {
            S3 s3 = FinancialManagementApplication.getBean(S3.class);
            entry.setAttachmentUrl(s3.setUrl(entry.getAttachment()));
        }
    }*/

}
