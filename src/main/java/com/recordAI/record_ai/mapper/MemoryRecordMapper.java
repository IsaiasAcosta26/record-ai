package com.recordAI.record_ai.mapper;


import com.recordAI.record_ai.dto.MemoryRecordDto;
import com.recordAI.record_ai.mapper.base.BaseMapper;
import com.recordAI.record_ai.model.MemoryRecord;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemoryRecordMapper extends BaseMapper<MemoryRecordDto, MemoryRecord> {
}
