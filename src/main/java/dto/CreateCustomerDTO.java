package dto;

import enums.CustomerType;

public record CreateCustomerDTO(
        CustomerType type,
        String name,
        String numberPhone,
        String documentId
) {
}
