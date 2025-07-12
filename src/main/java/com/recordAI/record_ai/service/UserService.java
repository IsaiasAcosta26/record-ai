package com.recordAI.record_ai.service;

import com.recordAI.record_ai.dto.UserDto;
import com.recordAI.record_ai.mapper.UserMapper;
import com.recordAI.record_ai.model.User;
import com.recordAI.record_ai.repository.UserRepository;
import com.recordAI.record_ai.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserService extends BaseServiceImpl<User, UserDto> {

    public UserService(UserRepository repository, UserMapper mapper) {
        super(repository, mapper);
    }
}
