package model.customer;

public class Individual extends Customer {

    public Individual(String id, String name, String numberPhone, String documentId) {
        super(id, name, numberPhone, documentId);
    }


    @Override
    public void setDocumentId(String documentId) {
        if(documentId != null && !documentId.isEmpty() && documentId.length() == 11){
            this.documentId = documentId;
        }

    }
}
