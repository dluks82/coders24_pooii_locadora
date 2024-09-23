package dto;

public record CreateCustomerDTO(
        utils.CustomerType type,
        String name,
        String numberPhone,
        String documentId
) {
}
