package com.dev.core.mapper;

import com.dev.core.dto.ProductDTO;
import com.dev.entity.db.ProductEntity;
import com.dev.entity.mapper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "jsr330", uses = MapperSupport.class)
public interface ProductMapper extends EntityMapper<ProductEntity, ProductDTO> {
}
