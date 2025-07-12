package com.recordAI.record_ai.mapper;

import com.recordAI.record_ai.dto.WeeklySummaryDto;
import com.recordAI.record_ai.mapper.base.BaseMapper;
import com.recordAI.record_ai.model.WeeklySummary;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WeeklySummaryMapper extends BaseMapper<WeeklySummaryDto, WeeklySummary> {

}
