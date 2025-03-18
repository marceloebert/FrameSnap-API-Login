package com.fiap.framesnap.infrastructure.customers.controller.mapper;

import com.fiap.framesnap.entities.customers.Customer;
import com.fiap.framesnap.infrastructure.customers.controller.dto.CustomerRequest;
import com.fiap.framesnap.infrastructure.customers.controller.dto.CustomerResponse;
import org.springframework.stereotype.Component;

@Component
public class CustomerDTOMapper {

    public Customer toCustomer(CustomerRequest customerRequest) {
        return new Customer(
                null,
                customerRequest.getName(),
                customerRequest.getDocument(),
                customerRequest.getMail()
        );
    }

    public CustomerResponse toCustomerResponse(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getDocument(),
                customer.getMail()
        );
    }


}
