package com.project.shopapp_backend.controllers;

import com.project.shopapp_backend.dtos.OrderDetailDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/order_details")
public class OrderDetailController
{
    @PostMapping
    public ResponseEntity<?> createOrderDetail(@Valid @RequestBody OrderDetailDTO orderDetailDTO)
    {
        return ResponseEntity.ok("them moi 1 order detail thanh cong!");
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetail(@Valid @PathVariable Long id)
    {
        return ResponseEntity.ok("getOrderDetail with id " + id);
    }
    // lấy ra danh sách các orderdetail của 1 order nao do
    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getOrderDetails(@Valid @PathVariable Long orderId)
    {
        return ResponseEntity.ok("getOrderDetails with orderId ="+orderId);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> udateOrderDetail(@Valid @PathVariable Long id,
                                              @RequestBody OrderDetailDTO newOrderDetailData)
    {
        return ResponseEntity.ok("udateOrderDetail with id " + id+ ",newOrderDetailData :"+newOrderDetailData);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderDetail(@Valid @PathVariable Long id)
    {
        return ResponseEntity.noContent().build();
    }
}
