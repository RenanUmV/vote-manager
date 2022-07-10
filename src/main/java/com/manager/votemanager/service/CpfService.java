package com.manager.votemanager.service;


import com.manager.votemanager.config.CpfClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CpfService {

    @Autowired
    private final CpfClient cpfClient;

    public CpfService(CpfClient cpfClient) {
        this.cpfClient = cpfClient;
    }

    public boolean validateCpf(String cpf){

        log.info("Validating CPF");
        return cpfClient.validateCpf(cpf).getIsValid();
    }
}
