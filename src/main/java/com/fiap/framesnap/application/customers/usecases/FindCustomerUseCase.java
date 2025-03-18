package com.fiap.framesnap.application.customers.usecases;

import com.fiap.framesnap.application.customers.gateways.CustomerGateway;
import com.fiap.framesnap.entities.customers.Customer;

import java.util.Optional;

public class FindCustomerUseCase {

    private final CustomerGateway customerGateway;

    public FindCustomerUseCase(CustomerGateway customerGateway) {
        this.customerGateway = customerGateway;
    }

    public Optional<Customer> findCustomer(String document) {
        return customerGateway.findCustomer(document);
    }
}
