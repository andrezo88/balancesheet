package com.andre.balancesheet.util.mapper;

import com.andre.balancesheet.dto.RegisterRequest;
import com.andre.balancesheet.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthenticationMapper {

    User toUser(RegisterRequest request);

}
