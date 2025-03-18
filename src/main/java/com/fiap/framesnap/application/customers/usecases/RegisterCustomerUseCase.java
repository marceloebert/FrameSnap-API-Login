package com.fiap.framesnap.application.customers.usecases;

import com.fiap.framesnap.entities.customers.Customer;
import com.fiap.framesnap.application.customers.gateways.CustomerGateway;

public class RegisterCustomerUseCase {

    private final CustomerGateway customerGateway;

    public RegisterCustomerUseCase(CustomerGateway customerGateway) {
        this.customerGateway = customerGateway;
    }

    public Customer save(Customer customer) {

        return customerGateway.save(customer);
    }
}