package com.duymeke.finaltwo.service.impl;


import com.duymeke.finaltwo.dto.AccountCreateDto;
import com.duymeke.finaltwo.dto.AccountDto;
import com.duymeke.finaltwo.entity.Account;
import com.duymeke.finaltwo.entity.Role;
import com.duymeke.finaltwo.mapper.AccountMapper;
import com.duymeke.finaltwo.repository.AccountRepository;
import com.duymeke.finaltwo.repository.RoleRepository;
import com.duymeke.finaltwo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository repository;
    @Autowired
    private AccountMapper mapper;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<AccountDto> getAll() {
        return mapper.toDtoList(repository.findAll());
    }

    @Override
    public AccountDto getById(Long id) {
        return mapper.toDto(repository.findById(id).orElse(null));
    }

    @Override
    public AccountDto create(AccountCreateDto accountCreateDto) {
        if (Objects.isNull(accountCreateDto)) return null;

        Account response = new Account();
        Account check = repository.findByEmail(accountCreateDto.getEmail());

        if (check == null) {
            Account account = new Account();
            account.setUsername(accountCreateDto.getUsername());
            account.setEmail(accountCreateDto.getEmail());
            account.setPassword(passwordEncoder.encode(accountCreateDto.getPassword()));
            List<Role> roles = List.of(roleRepository.findByName("ROLE_USER"));
            account.setRoles(roles);
            response = repository.save(account);
        }
        return mapper.toDto(response);
    }

    @Override
    public AccountDto update(Long id, AccountCreateDto accountCreateDto) {
        Account old = repository.findById(id).orElse(null);
        if (Objects.isNull(accountCreateDto) || Objects.isNull(old)) return null;

        old.setUsername(accountCreateDto.getUsername());
        old.setEmail(accountCreateDto.getEmail());

        if (accountCreateDto.getPassword() != null && !accountCreateDto.getPassword().isEmpty()) {
            old.setPassword(passwordEncoder.encode(accountCreateDto.getPassword()));
        }
        return mapper.toDto(repository.save(old));
    }

    @Override
    public boolean delete(Long id) {
        repository.deleteById(id);
        return Objects.isNull(getById(id));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = repository.findByEmail(username);
        if (Objects.nonNull(account)) {
            return account;
        }
        throw new UsernameNotFoundException("Account not found");
    }
}

