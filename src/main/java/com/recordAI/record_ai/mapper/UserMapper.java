package com.recordAI.record_ai.mapper;


import com.recordAI.record_ai.dto.UserDto;
import com.recordAI.record_ai.mapper.base.BaseMapper;
import com.recordAI.record_ai.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<UserDto, User> {
}
