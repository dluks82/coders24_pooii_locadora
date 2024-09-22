package model.customer;

public abstract class Customer {

    protected String id;
    protected String name;
    protected String NumberPhone;
    protected String documentId;


    public Customer(String id, String name, String numberPhone, String documentId) {
        this.id = id;
        this.name = name;
        this.NumberPhone = numberPhone;
        setDocumentId(documentId);

    }

    public abstract void setDocumentId(String documentId);


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNumberPhone() {
        return NumberPhone;
    }

    public String getDocumentId() {
        return documentId;
    }

}
