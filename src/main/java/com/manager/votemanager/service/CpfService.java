package com.manager.votemanager.service;


import com.manager.votemanager.config.CpfClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CpfService {

    @Autowired
    private final CpfClient cpfClient;

    public CpfService(CpfClient cpfClient) {
        this.cpfClient = cpfClient;
    }

    public boolean validateCpf(String cpf){

        return cpfClient.validateCpf(cpf).getIsValid();
    }
}
