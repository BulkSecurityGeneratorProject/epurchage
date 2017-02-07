package com.softage.epurchase.service.mapper;

import com.softage.epurchase.domain.*;
import com.softage.epurchase.service.dto.DepartmentDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Department and its DTO DepartmentDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DepartmentMapper {

    DepartmentDTO departmentToDepartmentDTO(Department department);

    List<DepartmentDTO> departmentsToDepartmentDTOs(List<Department> departments);

    @Mapping(target = "departmentreqs", ignore = true)
    @Mapping(target = "departmentords", ignore = true)
    Department departmentDTOToDepartment(DepartmentDTO departmentDTO);

    List<Department> departmentDTOsToDepartments(List<DepartmentDTO> departmentDTOs);
}
