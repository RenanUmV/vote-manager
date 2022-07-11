package com.manager.votemanager.service;


import com.manager.votemanager.config.CpfClient;
import com.manager.votemanager.dto.CpfDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CpfService {

    @Autowired
    private final CpfClient cpfClient;

    public CpfService(CpfClient cpfClient) {
        this.cpfClient = cpfClient;
    }

    @Retryable(maxAttempts = 3, backoff = @Backoff(3000))
    public boolean validateCpf(String cpf){

        log.info("Validating CPF");
        return cpfClient.validateCpf(cpf).getIsValid();
    }

    @Recover
    public boolean saveCpf(String cpf){

        CpfDto cpfDto = CpfDto.builder()
                .cpf(cpf)
                .isValid(true)
                .build();

        log.info("The service cannot be queried");
        return cpfDto.getIsValid();
    }
}
