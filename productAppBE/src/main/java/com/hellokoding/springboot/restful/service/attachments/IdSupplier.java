package com.hellokoding.springboot.restful.service.attachments;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class IdSupplier implements Supplier<String> {

    @Override
    public String get() {
        // https://stackoverflow.com/questions/4267475/generating-8-character-only-uuids
        // note that such id is not guaranteed to be unique!
        return RandomStringUtils.randomAlphanumeric(8).toLowerCase();
    }

}
