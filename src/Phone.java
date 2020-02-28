import java.sql.ResultSet;
import java.sql.SQLException;

public class Phone {

    private int id;
    private String Manufacture;
    private String Model;
    //private String CoreCount;
    private String Ram;
    private String screenDiagonal;
    private String os;
    private String rom;
    private String battaryCapacity;
    private String workTime;
    private String BackCameraMegaPixel;
    private double price;

    public Phone(String manufacture, String model, String ram,String os, String screenDiagonal, String rom, String battaryCapacity, String workTime, String backCameraMegaPixel, double price) {
        this.Manufacture = manufacture;
        this.Model = model;
        this.Ram = ram;
        this.os = os;
        this.screenDiagonal = screenDiagonal;
        this.rom = rom;
        this.battaryCapacity = battaryCapacity;
        this.workTime = workTime;
        this.BackCameraMegaPixel = backCameraMegaPixel;
        this.price = price;
    }

    public void setScreenDiagonal(String screenDiagonal) {
        this.screenDiagonal = screenDiagonal;
    }

    public void setRom(String rom) {
        this.rom = rom;
    }

    public void setBattaryCapacity(String battaryCapacity) {
        this.battaryCapacity = battaryCapacity;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public String getScreenDiagonal() {
        return screenDiagonal;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getRom() {
        return rom;
    }

    public String getBattaryCapacity() {
        return battaryCapacity;
    }

    public String getWorkTime() {
        return workTime;
    }

    public String getOs() {
        return os;
    }

    public Phone(int id, String manufacture, String model, String ram, String os, String screenDiagonal, String rom, String battaryCapacity, String workTime, String backCameraMegaPixel, double price) {
        this.id = id;
        this.Manufacture = manufacture;
        this.Model = model;
        this.Ram = ram;
        this.os = os;
        this.screenDiagonal = screenDiagonal;
        this.rom = rom;
        this.battaryCapacity = battaryCapacity;
        this.workTime = workTime;
        this.BackCameraMegaPixel = backCameraMegaPixel;
        this.price = price;
    }

    public Phone(String manufacture, String model, String ram, String backCameraMegaPixel, double price) {
        this.Manufacture = manufacture;
        this.Model = model;
        //this.CoreCount = coreCount;
        this.Ram = ram;
        this.BackCameraMegaPixel = backCameraMegaPixel;
        this.price = price;
    }

    public Phone(int id, String manufacture, String model, String ram, String backCameraMegaPixel, double price) {
        this.id = id;
        this.Manufacture = manufacture;
        this.Model = model;
        //this.CoreCount = coreCount;
        this.Ram = ram;
        this.BackCameraMegaPixel = backCameraMegaPixel;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getManufacture() {
        return Manufacture;
    }

    public String getModel() {
        return Model;
    }

//    public String getCoreCount() {
//        return CoreCount;
//    }

    public String getRam() {
        return Ram;
    }

    public String getBackCameraMegaPixel() {
        return BackCameraMegaPixel;
    }

    public double getPrice() {
        return price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setManufacture(String manufacture) {
        Manufacture = manufacture;
    }

    public void setModel(String model) {
        Model = model;
    }

//    public void setCoreCount(String coreCount) {
//        CoreCount = coreCount;
//    }

    public void setRam(String ram) {
        Ram = ram;
    }

    public void setBackCameraMegaPixel(String backCameraMegaPixel) {
        BackCameraMegaPixel = backCameraMegaPixel;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "id=" + id +
                ", Manufacture='" + Manufacture + '\'' +
                ", Model='" + Model + '\'' +
               // ", CoreCount='" + CoreCount + '\'' +
                ", Ram='" + Ram + '\'' +
                ", BackCameraMegaPixel='" + BackCameraMegaPixel + '\'' +
                ", OS='" + os + '\'' +
                ", Ram='" + Ram + '\'' +
                ", Screen Diagonal='" + screenDiagonal + '\'' +
                ", ROM='" + rom + '\'' +
                ", Battary Capacity='" + battaryCapacity + '\'' +
                ", Work Time='" + workTime + '\'' +
                ", price=" + price +
                '}';
    }

    public static Phone toPhone(ResultSet rs)throws SQLException{
        Integer id = rs.getInt(1);
        String Manufacture = rs.getString(2);
        String Model = rs.getString(3);
        //String CoreCount = rs.getString(4);
        String RAM = rs.getString(4);
        String os = rs.getString(5);
        String BackCameraMegaPixel = rs.getString(6);
        String screenDiagonal = rs.getString(7);
        String rom = rs.getString(8);
        String battaryCapacity = rs.getString(9);
        String workTime = rs.getString(10);
        Double price = rs.getDouble(11);
        return new Phone(id, Manufacture, Model, RAM,os, BackCameraMegaPixel,screenDiagonal ,rom, battaryCapacity,workTime, price);
    }

}
