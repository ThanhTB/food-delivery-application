package com.dev.core.mapper;

import com.dev.core.dto.AccountDTO;
import com.dev.entity.db.AccountEntity;
import com.dev.entity.mapper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "jsr330", uses = MapperSupport.class)
public interface AccountMapper extends EntityMapper<AccountEntity, AccountDTO> {
}
