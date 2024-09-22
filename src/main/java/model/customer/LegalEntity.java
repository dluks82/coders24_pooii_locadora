package model.customer;

public class LegalEntity extends Customer {

    public LegalEntity(String id, String name, String numberPhone, String documentId) {
        super(id, name, numberPhone, documentId);
    }

    @Override
    public void setDocumentId(String documentId) {
        if(documentId != null && !documentId.isEmpty() && documentId.length() == 14){
            this.documentId = documentId;
        }
    }
}
