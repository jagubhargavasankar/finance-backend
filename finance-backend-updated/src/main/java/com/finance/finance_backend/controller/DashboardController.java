package com.finance.finance_backend.controller;

import com.finance.finance_backend.dto.response.DashboardResponse;
import com.finance.finance_backend.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @PreAuthorize("hasAnyAuthority('ADMIN','ANALYST')")
    @GetMapping("/summary")
    public ResponseEntity<DashboardResponse> getSummary() {
        return ResponseEntity.ok(dashboardService.getSummary());
    }

    @GetMapping("/category-wise")
    public ResponseEntity<Map<String, Double>> getCategoryWise() {
        return ResponseEntity.ok(dashboardService.getCategoryWiseData());
    }

    @GetMapping("/trends")
    public ResponseEntity<Map<String, Double>> getTrends() {
        return ResponseEntity.ok(dashboardService.getTrends());
    }
}
