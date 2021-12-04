package top.newpointer.farm.proxy;

import java.util.List;

public interface Buyer {
    Double sell(Integer farmerId, Integer speciesId, Integer number);
     Double sellList(Integer farmerId, List<Integer> speciesIdList, List<Integer> numberList);
}
