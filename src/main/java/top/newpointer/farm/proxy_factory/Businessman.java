package top.newpointer.farm.proxy_factory;

public abstract class Businessman implements Buyer{
    protected abstract Double preSell(Integer farmerId, Integer speciesId, Integer number);
    protected abstract Double postSell(Integer farmerId, Integer speciesId, Integer number, Double systemMoney);

}
