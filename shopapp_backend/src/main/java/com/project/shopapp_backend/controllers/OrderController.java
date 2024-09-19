package com.project.shopapp_backend.controllers;
import com.project.shopapp_backend.dtos.CategoryDTO;
import com.project.shopapp_backend.dtos.OrderDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders")

public class OrderController
{
    @PostMapping("")
    public ResponseEntity<?> createOrder(@RequestBody @Valid OrderDTO orderDTO,
                                         BindingResult result)
    {
        try
        {
            if (result.hasErrors())
            {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(f -> f.getDefaultMessage())
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            return ResponseEntity.ok("them moi order thanh cong");
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        }
    @GetMapping("/{user_id}")
    public ResponseEntity<?> getOrders(@Valid @PathVariable("user_id") long userId)
    {
        try
        {
            return ResponseEntity.ok("danh sach order cua user_id la");
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/{id}")
    //cong viec cua Admin
    public ResponseEntity<?> updateOrder(@Valid @PathVariable long id,
                                         @Valid @RequestBody OrderDTO orderDTO)
    {
            return ResponseEntity.ok("cap nhat thong tin 1 order");
    }
    @DeleteMapping("/{id}")
    //cong viec cua Admin
    public ResponseEntity<?> deleteOrder(@Valid @PathVariable long id)
    {
        //xoa mem => cap nhat truong active=false
        return ResponseEntity.ok("xoa thanh cong");
    }
}

