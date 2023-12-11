package vlad.kuchuk.taskmanagementsystem.user.dto;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import vlad.kuchuk.taskmanagementsystem.security.entity.UserEntity;

@Mapper(unmappedTargetPolicy = ReportingPolicy.WARN, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    UserEntity toEntity(UserDto userDto);

    UserDto toDto(UserEntity userEntity);

    SmallUserDto toSmallDto(UserEntity userEntity);
}