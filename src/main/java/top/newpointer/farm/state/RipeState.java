package top.newpointer.farm.state;

public class RipeState extends PlantState{

    public static final Integer CODE = 1;

    @Override
    public void grow() {
    }

    @Override
    public String water() {
        return "植物已经成熟，不需要浇水！";
    }

    @Override
    public String harvest() {
        System.err.println("收获方法还没写！");
        return "成功收获！";
    }
}
