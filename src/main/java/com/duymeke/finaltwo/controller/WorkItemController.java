package com.duymeke.finaltwo.controller;


import com.duymeke.finaltwo.dto.WorkItemDto;
import com.duymeke.finaltwo.entity.Account;
import com.duymeke.finaltwo.repository.AccountRepository;
import com.duymeke.finaltwo.service.WorkItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/work-items")
public class WorkItemController {

    @Autowired
    private WorkItemService service;

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping
    public ResponseEntity<?> getAll() {
        Long accountId = getCurrentAccountId();
        return new ResponseEntity<>(service.getAll(accountId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Long accountId = getCurrentAccountId();
        WorkItemDto dto = service.getById(id, accountId);
        if (dto == null) {
            return new ResponseEntity<>("Work item not found or access denied", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody WorkItemDto dto) {
        Long accountId = getCurrentAccountId();
        if (accountId == null) {
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }
        dto.setAccountId(accountId);
        return new ResponseEntity<>(service.create(dto), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody WorkItemDto dto) {
        Long accountId = getCurrentAccountId();
        WorkItemDto updated = service.update(id, dto, accountId);
        if (updated == null) {
            return new ResponseEntity<>("Work item not found or access denied", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Long accountId = getCurrentAccountId();
        boolean deleted = service.delete(id, accountId);
        if (!deleted) {
            return new ResponseEntity<>("Work item not found or access denied", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Long getCurrentAccountId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        String email = authentication.getName();
        Account account = accountRepository.findByEmail(email);
        return account != null ? account.getId() : null;
    }
}

