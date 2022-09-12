package com.dalfredi.bookkeepingapi.service;

import static com.dalfredi.bookkeepingapi.utils.Constants.CUSTOMER;
import static com.dalfredi.bookkeepingapi.utils.Constants.ID;

import com.dalfredi.bookkeepingapi.exception.ResourceNotFoundException;
import com.dalfredi.bookkeepingapi.model.Customer;
import com.dalfredi.bookkeepingapi.payload.CustomerDTO;
import com.dalfredi.bookkeepingapi.payload.api.ApiResponse;
import com.dalfredi.bookkeepingapi.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerDTO addCustomer(CustomerDTO customerRequest) {
        Customer customer = new Customer();
        customer.setName(customer.getName());
        customer.setTgUsername(customer.getTgUsername());
        customerRepository.save(customer);
        return CustomerDTO.of(customer);
    }

    public CustomerDTO getCustomerById(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(
                () -> new ResourceNotFoundException(CUSTOMER, ID, customerId));
        return CustomerDTO.of(customer);
    }

    public CustomerDTO updateCustomer(Long customerId,
                                      CustomerDTO customerRequest) {
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(
                () -> new ResourceNotFoundException(CUSTOMER, ID, customerId));
        customer.setName(customerRequest.getName());
        customer.setTgUsername(customer.getTgUsername());
        customerRepository.save(customer);
        return CustomerDTO.of(customer);
    }

    public ApiResponse deleteCustomerById(Long customerId) {
        if (customerRepository.existsById(customerId)) {
            customerRepository.deleteById(customerId);
            return new ApiResponse(Boolean.TRUE,
                "You successfully deleted customer");
        } else {
            throw new ResourceNotFoundException(CUSTOMER, ID, customerId);
        }
    }
}
