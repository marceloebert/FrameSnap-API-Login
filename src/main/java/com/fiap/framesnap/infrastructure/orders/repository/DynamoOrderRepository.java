package com.fiap.framesnap.infrastructure.orders.repository;

import com.fiap.framesnap.application.orders.gateways.OrderGateway;
import com.fiap.framesnap.entities.orders.Order;
import com.fiap.framesnap.infrastructure.orders.entity.OrderEntity;
import com.fiap.framesnap.application.customers.gateways.CustomerGateway;
import com.fiap.framesnap.entities.customers.Customer;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class DynamoOrderRepository implements OrderGateway {

    private final DynamoDbTable<OrderEntity> orderTable;
    private final CustomerGateway customerGateway;

    public DynamoOrderRepository(DynamoDbEnhancedClient enhancedClient, CustomerGateway customerGateway) {
        this.orderTable = enhancedClient.table("Orders", TableSchema.fromBean(OrderEntity.class));
        this.customerGateway = customerGateway;
    }

    @Override
    public Order save(Order order) {
        OrderEntity entity = new OrderEntity(order);
        orderTable.putItem(entity);
        return order;
    }



    @Override
    public List<Order> findAll() {
        return orderTable.scan().items().stream()
                .map(entity -> {
                    Customer customer = customerGateway.findCustomerById(entity.getCustomerId())
                            .orElse(null);
                    return entity.toOrder(customer);
                })
                .toList();
    }



    @Override
    public Optional<Order> findById(UUID id) {
        OrderEntity entity = orderTable.getItem(r -> r.key(k -> k.partitionValue(id.toString())));
        if (entity == null) return Optional.empty();

        Customer customer = customerGateway.findCustomerById(entity.getCustomerId())
                .orElse(null); // ✅ Buscamos o cliente antes de converter

        return Optional.of(entity.toOrder(customer));
    }
}
