package dto;

public record CreateCustomerDTO(
        String type,
        String name,
        String numberPhone,
        String documentId
) {
}
