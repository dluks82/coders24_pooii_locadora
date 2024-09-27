package model.customer;

import enums.CustomerType;

public class LegalEntity extends Customer {

    public LegalEntity(String id, String name, String numberPhone, String documentId, CustomerType type) {
        super(id, name, numberPhone, documentId, type);
    }

    @Override
    public void setDocumentId(String documentId) {
        if(documentId != null && documentId.length() == 14){
            this.documentId = documentId;
        }
    }
}
