package com.example.dossier.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "dossier", url = "${deal.url}")
public interface DossierFeignClient {

    @PostMapping(value = "/deal/document/{applicationId}/sign")
    void signDocuments(@RequestParam Long applicationId);

}
