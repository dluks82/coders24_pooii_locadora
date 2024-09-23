package dto;

import utils.CustomerType;

public record CreateCustomerDTO(
        CustomerType type,
        String name,
        String numberPhone,
        String documentId
) {
}
