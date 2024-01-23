package com.example.deal.api;

import com.example.deal.entity.Application;
import com.example.deal.service.DealService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "Админское Api для работы МС deal")
@RestController
@RequestMapping("/deal/admin")
@RequiredArgsConstructor
public class DealAdminController {

    private final DealService dealService;

    @GetMapping("/application")
    @Operation(
            summary = "Получить все заявки.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation"),
                    @ApiResponse(responseCode = "500", description = "Error processing the request")
            })
    public ResponseEntity<List<Application>> getAllApplications() {
        List<Application> applications = dealService.getAllApplications();
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/application/{applicationId}")
    @Operation(
            summary = "Получить заявку по id.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation"),
                    @ApiResponse(responseCode = "500", description = "Error processing the request")
            })
    public ResponseEntity<Application> getApplicationById(@RequestParam Long applicationId) {
        Application application = dealService.getApplicationById(applicationId);
        return ResponseEntity.ok(application);
    }
}
