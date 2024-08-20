package com.rgt.assignment.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Auth {
    AUTHORIZATION("Authorization"),
    BEARER("Bearer");

    private final String value;
}
