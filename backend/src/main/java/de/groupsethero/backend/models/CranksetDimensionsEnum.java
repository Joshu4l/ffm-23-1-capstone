package de.groupsethero.backend.models;
import lombok.Getter;

import java.util.List;


@Getter
public enum CranksetDimensionsEnum {

    VALUE_50_34(50, 34),
    VALUE_52_36(52, 36),
    VALUE_53_39(53, 39);

    private final int outerChainringTeeth;
    private final int innerChainringTeeth;

    private CranksetDimensionsEnum(int outerChainringTeeth, int innerChainringTeeth) {
        this.outerChainringTeeth = outerChainringTeeth;
        this.innerChainringTeeth = innerChainringTeeth;
    }

    public int getOuterChainringTeeth() {
        return outerChainringTeeth;
    }

    public int getInnerChainringTeeth() {
        return innerChainringTeeth;
    }

    public List<Integer> getChainringTeethPair() {
        return List.of(outerChainringTeeth, innerChainringTeeth);
    }
}
