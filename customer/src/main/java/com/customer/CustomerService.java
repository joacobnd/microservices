package com.customer;

import com.clients.fraud.FraudCheckResponse;
import com.clients.fraud.FraudClient;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final RestTemplate restTemplate;

    private final FraudClient fraudClient;


    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();


        //todo: check if email valid
        //todo: check if email not taken

        customerRepository.saveAndFlush(customer);
        //todo: check if fraudster


        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());

        if (fraudCheckResponse.isFraudster()) {
            throw new IllegalStateException("fraudster");
        }



        //todo: send notification
    }
}
