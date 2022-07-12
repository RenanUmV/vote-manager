package com.manager.votemanager.models.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CpfDto {

    private String cpf;
    private Boolean isValid;

}
