package com.manager.votemanager.service;

import com.manager.votemanager.config.CpfClient;
import com.manager.votemanager.models.v1.dto.CpfDto;
import com.manager.votemanager.service.v1.CpfService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CpfServiceTest {

    @InjectMocks
    private CpfService cpfService;

    @Mock
    CpfClient cpfClient;

    private static final String VALID_CPF = "12345678910";

    private static final String INVALID_CPF = "1234567891";


    private CpfDto mockCpfDto(String cpf, boolean isValid) {
        return new CpfDto(cpf, isValid);
    }

    @Test
    void ShouldValidateCpfWhenCpfParamHas11Characters() {
        when(cpfClient.validateCpf(VALID_CPF)).thenReturn(mockCpfDto(VALID_CPF, true));

        boolean result = cpfService.validateCpf(VALID_CPF);

        assertTrue(result);
    }

    @Test
    void ShouldValidateCpfWhenCpfParamHas10Characters() {
        when(cpfClient.validateCpf(INVALID_CPF)).thenReturn(mockCpfDto(INVALID_CPF, false));

        boolean result = cpfService.validateCpf(INVALID_CPF);

        assertFalse(result);
    }
}
