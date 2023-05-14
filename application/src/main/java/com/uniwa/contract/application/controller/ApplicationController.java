package com.uniwa.contract.application.controller;

import com.uniwa.contract.application.entity.ApplicationEntity;
import com.uniwa.contract.application.enumeration.ApplicationStatusEnum;
import com.uniwa.contract.application.exception.ApplicationNotFoundException;
import com.uniwa.contract.application.service.ApplicationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/application")
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping("/v1/{username}")
    public ResponseEntity<ApplicationEntity> create(@PathVariable(name = "username") String username) {

        ApplicationEntity application = applicationService.create(username);
        return ResponseEntity.created(URI.create("/v1/routes" + username)).body(application);
    }

    @GetMapping(value = "/v1/user/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ApplicationEntity>> retrieveByUsername(@PathVariable(name = "username") String username) {

        return ResponseEntity.ok(applicationService.retrieveByUsername(username));
    }

    @GetMapping(value = "/v1/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApplicationEntity> retrieve(@PathVariable(name = "id") Long applicationId) {

        return ResponseEntity.ok(applicationService.retrieve(applicationId));
    }

    @PutMapping("/v1/{id}/{status}")
    public ResponseEntity<Void> update(@PathVariable(name = "id") Long applicationId,
                                       @PathVariable(name = "status") ApplicationStatusEnum status) {

        applicationService.update(applicationId, status);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/v1/{id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") Long applicationId) {

        applicationService.delete(applicationId);
        return ResponseEntity.accepted().build();
    }

    @ExceptionHandler({ ApplicationNotFoundException.class })
    public ResponseEntity<Object> handleApplicationNotFoundException(Exception e) {

        return new ResponseEntity<>(e.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

}
