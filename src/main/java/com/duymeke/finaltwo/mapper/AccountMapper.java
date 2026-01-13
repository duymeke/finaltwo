package com.duymeke.finaltwo.mapper;

import com.duymeke.finaltwo.dto.AccountCreateDto;
import com.duymeke.finaltwo.dto.AccountDto;
import com.duymeke.finaltwo.entity.Account;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {WorkItemMapper.class})
public interface AccountMapper {

    AccountDto toDto(Account account);
    Account toEntity(AccountCreateDto dto);
    List<AccountDto> toDtoList(List<Account> accounts);
}
