package com.rgt.assignment.service;

import com.rgt.assignment.constants.OrderStatus;
import com.rgt.assignment.dto.MenuDto;
import com.rgt.assignment.dto.OrdersResponseDto;
import com.rgt.assignment.entity.OrderItem;
import com.rgt.assignment.entity.Orders;
import com.rgt.assignment.entity.TableItem;
import com.rgt.assignment.entity.Tables;
import com.rgt.assignment.repository.OrderItemRepository;
import com.rgt.assignment.repository.OrdersRepository;
import com.rgt.assignment.repository.TableItemRepository;
import com.rgt.assignment.repository.TablesRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class OrdersService {
    private static final String NOT_EXIST_TABLE = "장바구니(테이블)가 존재하지 않습니다.";
    private static final String EMPTY_TABLE = "장바구니(테이블)가 비어있습니다.";
    private static final String INVALID_PRICE = "가격 정보가 없습니다.";

    private final OrdersRepository ordersRepository;
    private final TableItemRepository tableItemRepository;
    private final TablesRepository tablesRepository;
    private final OrderItemRepository orderItemRepository;

    @Transactional
    public OrdersResponseDto order(Long tablesId) throws ResponseStatusException {
        Tables table = getTable(tablesId);
        List<TableItem> tableItems = getTableItems(table);
        Integer totalPrice = getTotalPrice(tableItems);

        Orders order = saveOrder(table, totalPrice);
        List<OrderItem> savedOrderItems = saveOrderItems(tableItems, order);

        this.tableItemRepository.deleteAll(tableItems);

        return convertOrdersResponseDto(savedOrderItems, order, totalPrice);
    }

    private Tables getTable(Long tablesId) {
        return this.tablesRepository.findById(tablesId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, NOT_EXIST_TABLE));
    }

    private List<TableItem> getTableItems(Tables table) {
        List<TableItem> tableItems = this.tableItemRepository.findByTables(table);
        if (tableItems.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, EMPTY_TABLE);
        }
        return tableItems;
    }

    private Integer getTotalPrice(List<TableItem> tableItems) {
        return tableItems.stream()
                .map(item -> item.getMenu().getPrice() * item.getQuantity())
                .reduce(Integer::sum)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, INVALID_PRICE));
    }

    private Orders saveOrder(Tables table, Integer totalPrice) {
        return this.ordersRepository.save(Orders.builder()
                .table(table)
                .status(OrderStatus.CONFIRMED)
                .totalPrice(totalPrice)
                .build());
    }

    private List<OrderItem> saveOrderItems(List<TableItem> tableItems, Orders order) {
        List<OrderItem> orderItems = tableItems.stream()
                .map(tableItem -> OrderItem.builder()
                        .orders(order)
                        .menu(tableItem.getMenu())
                        .quantity(tableItem.getQuantity())
                        .price(tableItem.getMenu().getPrice())
                        .build())
                .toList();

        return this.orderItemRepository.saveAll(orderItems);
    }

    private OrdersResponseDto convertOrdersResponseDto(List<OrderItem> savedOrderItems, Orders order,
                                                       Integer totalPrice) {
        List<MenuDto> menuDtos = savedOrderItems.stream()
                .map(orderItem -> MenuDto.builder()
                        .menuId(orderItem.getMenu().getId())
                        .name(orderItem.getMenu().getName())
                        .price(orderItem.getPrice())
                        .quantity(orderItem.getQuantity())
                        .build())
                .toList();

        return OrdersResponseDto.builder()
                .orderId(order.getId())
                .menus(menuDtos)
                .status(OrderStatus.CONFIRMED)
                .totalPrice(totalPrice)
                .build();
    }
}
