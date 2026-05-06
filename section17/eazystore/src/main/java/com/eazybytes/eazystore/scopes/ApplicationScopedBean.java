package com.eazybytes.eazystore.scopes;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Component
@ApplicationScope
@Getter
@Slf4j
public class ApplicationScopedBean {

    private int vistorCount;

    public ApplicationScopedBean() {
        log.info("ApplicationScopedBean initialized");
    }

    public void incrementVisitorCount() {
        vistorCount++;
    }
}
