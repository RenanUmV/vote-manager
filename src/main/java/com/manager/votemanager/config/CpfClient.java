package com.manager.votemanager.config;

import com.manager.votemanager.models.v1.dto.CpfDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "cpfValidator", url = "https://cpf-api-almfelipe.herokuapp.com")
public interface CpfClient {

    @RequestMapping(method = RequestMethod.GET, value = "/cpf/{cpf}")
    CpfDto validateCpf(@PathVariable String cpf);

}
