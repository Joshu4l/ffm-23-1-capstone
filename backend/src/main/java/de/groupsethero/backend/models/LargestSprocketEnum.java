package de.groupsethero.backend.models;
import lombok.Getter;

@Getter
public enum LargestSprocketEnum {

    VALUE_28(28),
    VALUE_32(32),
    VALUE_34(34),
    VALUE_36(36),
    VALUE_40(40);

    private final int value;

    private LargestSprocketEnum(int value) {
        this.value = value;
    }

}

