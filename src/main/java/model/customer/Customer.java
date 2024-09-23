package model.customer;

import utils.CustomerType;

public abstract class Customer {

    protected String id;
    protected String name;
    protected String NumberPhone;
    protected String documentId;
    protected CustomerType type;


    public Customer(String id, String name, String numberPhone, String documentId, CustomerType type) {
        this.id = id;
        this.name = name;
        this.NumberPhone = numberPhone;
        setDocumentId(documentId);
        this.type = type;
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

    public CustomerType getType() { return type; }

}
