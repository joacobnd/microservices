package com.customer;

import com.amqp.RabbitMQMessageProducer;
import com.clients.fraud.FraudCheckResponse;
import com.clients.fraud.FraudClient;
import com.clients.n0tification.NotificationClient;
import com.clients.n0tification.NotificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final FraudClient fraudClient;

    private final RabbitMQMessageProducer rabbitMQMessageProducer;


    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .build();


        //todo: check if email valid
        //todo: check if email not taken

        customerRepository.saveAndFlush(customer);
        //todo: check if fraudster


        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());

        if (fraudCheckResponse.isFraudster()) {
            throw new IllegalStateException("fraudster");
        }

        NotificationRequest notificationRequest = new NotificationRequest(
                customer.getId(),
                customer.getEmail(),
                String.format("Hi %s, welcome to Amigoscode course....", customer.getFirstName())
        );

        rabbitMQMessageProducer.publish(
                notificationRequest,
                "internal-exchange",
                "internal.notification.routing-key"
        );




    }
}
