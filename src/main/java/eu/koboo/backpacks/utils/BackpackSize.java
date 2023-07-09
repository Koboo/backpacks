package eu.koboo.backpacks.utils;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Getter
public enum BackpackSize {

    ONE(9),
    TWO(18),
    THREE(27),
    FOUR(36),
    FIVE(45),
    SIX(54),
    ;

    int slotAmount;
}
