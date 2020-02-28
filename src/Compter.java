import jdk.jfr.StackTrace;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Compter {

    private int id;
    private String Manufacture;
    private String Model;
    private String Processor;
    private String GPU;
    private String Ram;
    private String maxRam;
    private String OS;
    private String rom;
    private String screenDiagonal;
    private String weihgt;
    private String battaryCapacity;
    private String workTime;
    private double price;
    List<OptionComputer> options = new ArrayList<>();

    public String getMaxRam() {
        return maxRam;
    }

    public void setMaxRam(String maxRam) {
        this.maxRam = maxRam;
    }

    public void setRom(String rom) {
        this.rom = rom;
    }

    public void setScreenDiagonal(String screenDiagonal) {
        this.screenDiagonal = screenDiagonal;
    }

    public void setWeihgt(String weihgt) {
        this.weihgt = weihgt;
    }

    public void setBattaryCapacity(String battaryCapacity) {
        this.battaryCapacity = battaryCapacity;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public String getRom() {
        return rom;
    }

    public String getScreenDiagonal() {
        return screenDiagonal;
    }

    public String getWeihgt() {
        return weihgt;
    }

    public String getBattaryCapacity() {
        return battaryCapacity;
    }

    public String getWorkTime() {
        return workTime;
    }



    public Compter(String manufacture, String model, String processor, String GPU, String ram,String maxRam, String OS, String rom, String screenDiagonal, String weihgt, String battaryCapacity, String workTime, double price) {
        this.Manufacture = manufacture;
        this.Model = model;
        this.Processor = processor;
        this.GPU = GPU;
        this.Ram = ram;
        this.maxRam = maxRam;
        this.OS = OS;
        this.rom = rom;
        this.screenDiagonal = screenDiagonal;
        this.weihgt = weihgt;
        this.battaryCapacity = battaryCapacity;
        this.workTime = workTime;
        this.price = price;
    }

    public Compter(int id, String manufacture, String model, String processor, String GPU, String ram,String maxRam, String OS, String rom, String screenDiagonal, String weihgt, String battaryCapacity, String workTime, double price) {
        this.id = id;
        this.Manufacture = manufacture;
        this.Model = model;
        this.Processor = processor;
        this.GPU = GPU;
        this.Ram = ram;
        this.maxRam = maxRam;
        this.OS = OS;
        this.rom = rom;
        this.screenDiagonal = screenDiagonal;
        this.weihgt = weihgt;
        this.battaryCapacity = battaryCapacity;
        this.workTime = workTime;
        this.price = price;
    }



    public void setOptions(String computers) {
        this.options = options;
    }

    public List<OptionComputer> getOptions() {
        return options;
    }

    public Compter(String manufacture, String model, String processor, String GPU, String ram, String OS, double price) {
        this.Manufacture = manufacture;
        this.Model = model;
        this.Processor = processor;
        this.GPU = GPU;
        this.Ram = ram;
        this.OS = OS;
        this.price = price;
    }

    public Compter(String manufacture, String model, double price) {
        this.Manufacture = manufacture;
        this.Model = model;
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

    public String getProcessor() {
        return Processor;
    }

    public String getGPU() {
        return GPU;
    }

    public String getRam() {
        return Ram;
    }

    public String getOS() {
        return OS;
    }

    public double getPrice() {
        return price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setManufacture(String manufacture) {
        this.Manufacture = Manufacture;
    }

    public void setModel(String model) {
        Model = model;
    }

    public void setProcessor(String processor) {
        Processor = processor;
    }

    public void setGPU(String GPU) {
        this.GPU = GPU;
    }

    public void setRam(String ram) {
        Ram = ram;
    }

    public void setOS(String OS) {
        this.OS = OS;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Compter(int id, String manufacture, String model, String processor, String GPU, String ram, String OS, double price) {
        this.id = id;
        this.Manufacture = manufacture;
        this.Model = model;
        this.Processor = processor;
        this.GPU = GPU;
        this.Ram = ram;
        this.OS = OS;
        this.price = price;
    }

    public Compter(){

    }

    public Compter(int id){
        this.id = id;
    }

    public void addOption(OptionComputer o){
        options.add(o);
    }

    @Override
    public String toString() {
        return "Compter:" +
                "(id=" + id +
                ", Manufacture='" + Manufacture + '\'' +
                ", Model='" + Model + '\'' +
                ", Processor='" + Processor + '\'' +
                ", GPU='" + GPU + '\'' +
                ", Ram='" + Ram + '\'' +
                ", Max RAM='" + maxRam + '\'' +
                ", OS='" + OS + '\'' +
                ", ROM='" + rom + '\'' +
                ", Screen Diagonal='" + screenDiagonal + '\'' +
                ", Weight='" + weihgt + '\'' +
                ", Work Time='" + workTime + '\'' +
                ", Price=" + price +
                ')'+';';
    }

    public static Compter toComputer(ResultSet rs) throws SQLException{
        int id = rs.getInt(1);
        String Manufacture = rs.getString(2);
        String Model = rs.getString(3);
        String Processor = rs.getString(4);
        String GPU = rs.getString(5);
        String RAM = rs.getString(6);
        String maxRAM = rs.getString(7);
        String OS = rs.getString(8);
        String rom = rs.getString(9);
        String screenDiagonal = rs.getString(10);
        String weihgt = rs.getString(11);
        String battaryCapacity = rs.getString(12);
        String workTime = rs.getString(13);
        Double price = rs.getDouble(14);
        return new Compter(id, Manufacture, Model, Processor, GPU, RAM, maxRAM, OS, rom, screenDiagonal, weihgt, battaryCapacity, workTime, price);
    }
}

