package model.customer;

import enums.CustomerType;

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

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumberPhone(String numberPhone) {
        NumberPhone = numberPhone;
    }

    public void setType(CustomerType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", NumberPhone='" + NumberPhone + '\'' +
                ", documentId='" + documentId + '\'' +
                ", type=" + type +
                '}';
    }
}
