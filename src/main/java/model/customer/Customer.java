package model.customer;

import enums.CustomerType;

import java.io.Serializable;

public abstract class Customer implements Serializable {
    private static final long serialVersionUID = 1L;

    protected String id;
    protected String name;
    protected String phoneNumber;
    protected String documentId;
    protected CustomerType type;

    public Customer(String id, String name, String phoneNumber, String documentId, CustomerType type) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getDocumentId() {
        return documentId;
    }

    public CustomerType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", documentId='" + documentId + '\'' +
                ", type=" + type +
                '}';
    }
}
