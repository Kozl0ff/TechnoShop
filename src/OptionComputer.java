public class OptionComputer {

    private String Manufacture;
    private String Model;
    private String Processor;
    private String GPU;
    private String Ram;
    private String OS;
    private double price;

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

    public void setManufacture(String manufacture) {
        Manufacture = manufacture;
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

    @Override
    public String toString() {
        return "OptionComputer{" +
                "Manufacture='" + Manufacture + '\'' +
                ", Model='" + Model + '\'' +
                ", Processor='" + Processor + '\'' +
                ", GPU='" + GPU + '\'' +
                ", Ram='" + Ram + '\'' +
                ", OS='" + OS + '\'' +
                ", price=" + price +
                '}';
    }
}
