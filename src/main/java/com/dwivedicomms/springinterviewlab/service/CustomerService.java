package com.dwivedicomms.springinterviewlab.service;

import com.dwivedicomms.springinterviewlab.domain.Customer;
import com.dwivedicomms.springinterviewlab.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public List<String> getAllCustomerEmails() {
        return customerRepository.findAll().stream()
                .map(Customer::getEmail)
                .toList();

    }

    public Long getCustomerPhoneOrDefault(String email) {
        return customerRepository.findByEmail(email)
                .map(Customer::getPhone)
                .filter(Objects::nonNull)
                .orElse(0L);
    }
}
