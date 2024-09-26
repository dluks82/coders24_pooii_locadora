package enums;

public enum CustomerType {
    INDIVIDUAL("Pessoa Física"),
    LEGALENTITY("Pessoa Jurídica");

    private final String description;

    CustomerType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
