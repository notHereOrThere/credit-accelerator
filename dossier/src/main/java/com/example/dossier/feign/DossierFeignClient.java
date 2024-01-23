package com.example.dossier.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "dossier", url = "${deal.url}")
public interface DossierFeignClient {

    @PostMapping(value = "/deal/document/{applicationId}/sign")
    void signDocuments(@PathVariable Long applicationId);

}
