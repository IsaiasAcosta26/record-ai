package com.recordAI.record_ai.mapper;

import com.recordAI.record_ai.dto.SuggestionMessageDto;
import com.recordAI.record_ai.mapper.base.BaseMapper;
import com.recordAI.record_ai.model.SuggestionMessage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SuggestionMessageMapper extends BaseMapper<SuggestionMessageDto, SuggestionMessage> {
}
