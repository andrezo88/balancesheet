package com.andre.balancesheet.util.mapper;

import com.andre.balancesheet.dto.UserResponseDto;
import com.andre.balancesheet.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

UserResponseDto convertUserToUserResponse(User user);

}
