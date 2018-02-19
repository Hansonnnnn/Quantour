package vo;

/**
 * Created by xiezhenyu on 2017/4/8.
 * 该类为购物车界面所用VO,即为用户所选择的股票（自选股）VO
 */
public class TrolleyVO {
    //股票名称
    private String name;
    //股票ID
    private int id;

    public TrolleyVO(String name, int id)
    {
        this.name = name;
        this.id  = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}
}
