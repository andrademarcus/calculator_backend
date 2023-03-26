package com.demo.calculator.controller;

import com.demo.calculator.service.UserRecordService;
import com.demo.calculator.response.UserRecordPageDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/userRecord")
public class UserRecordController {

    private final UserRecordService userRecordService;

    public UserRecordController(UserRecordService userRecordService) {
        this.userRecordService = userRecordService;

    }

    @GetMapping("/findAll")
    @Operation(summary = "Find all user (session) records", security = @SecurityRequirement(name = "bearer-token"))
    public UserRecordPageDTO findAll(
            @RequestParam(name ="startDate", required = false)
            @DateTimeFormat(pattern = "MM/dd/yyyy") LocalDate startDate,
            @RequestParam(name = "endDate", required = false)
            @DateTimeFormat(pattern = "MM/dd/yyyy") LocalDate endDate,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize) { // Authentication auth

        UserRecordPageDTO results  = userRecordService.findAll(null,
                startDate, endDate, sortBy, sortDirection, page, pageSize);
        return results;
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete user record", security = @SecurityRequirement(name = "bearer-token"))
    public void delete(@PathVariable Long id) {
        userRecordService.delete(id);
    }


}
