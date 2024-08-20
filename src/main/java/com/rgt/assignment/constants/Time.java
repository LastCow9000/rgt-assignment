package com.rgt.assignment.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Time {
    TEN_MIN(1000 * 60 * 10L),
    TWENTY_FOUR(1000 * 60 * 60 * 24L);

    private final Long value;
}
