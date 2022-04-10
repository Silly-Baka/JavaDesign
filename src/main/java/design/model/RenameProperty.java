package design.model;

/**
 * User: 86176
 * Date: 2022/4/8
 * Time: 16:59
 * Description:
 */
public class RenameProperty {
    private String prefix;
    private Integer startNum;
    private Integer numBits;

    public RenameProperty(){

    }
    public RenameProperty(String prefix, Integer startNum, Integer numBits) {
        this.prefix = prefix;
        this.startNum = startNum;
        this.numBits = numBits;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public Integer getStartNum() {
        return startNum;
    }

    public void setStartNum(Integer startNum) {
        this.startNum = startNum;
    }

    public Integer getNumBits() {
        return numBits;
    }

    public void setNumBits(Integer numBits) {
        this.numBits = numBits;
    }

    @Override
    public String toString() {
        return "RenameProperty{" +
                "prefix='" + prefix + '\'' +
                ", startNum=" + startNum +
                ", numBits=" + numBits +
                '}';
    }
}
