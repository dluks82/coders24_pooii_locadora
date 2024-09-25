package model.customer;

import enums.CustomerType;

public class Individual extends Customer {

    public Individual(String id, String name, String numberPhone, String documentId, CustomerType type) {
        super(id, name, numberPhone, documentId, type);
    }


    @Override
    public void setDocumentId(String documentId) {
        if(documentId != null && !documentId.isEmpty() && documentId.length() == 11){
            this.documentId = documentId;
        }

    }
}
