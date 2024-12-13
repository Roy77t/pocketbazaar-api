package com.pocketbazaar.api.controller;


import com.pocketbazaar.api.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Long>> getDashboardStats() {
        
        Map<String, Long> stats = new HashMap<>();
        stats.put("totalProducts", dashboardService.getTotalProducts());
        stats.put("totalUser", dashboardService.getTotalUser());
        stats.put("totalOrders", dashboardService.getTotalOrders());
        
        return ResponseEntity.ok(stats);
    }
}

