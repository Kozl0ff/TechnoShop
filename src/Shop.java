import org.sqlite.SQLiteException;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.management.NotificationEmitter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Shop {

    private static Object optionComputer;

    public static void deleteComputerById(int id, Connection connection) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement("delete from Computer where id=?");
        preparedStatement.setInt(1,id);
        preparedStatement.execute();
    }

    public static void deleteComputerByModel(String Model, Connection connection) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement("delete from Computer where Model=?");
        preparedStatement.setString(1,Model);
        preparedStatement.execute();
    }

    public static void deletePhoneById(int id, Connection connection)throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement("delete from Phone where id=?");
        preparedStatement.setInt(1,id);
        preparedStatement.execute();
    }

    private static Compter parseComputer(Node comps){

        Compter c = new Compter();
        NodeList compItems = comps.getChildNodes();

        for(int j = 0 ; j < compItems.getLength(); j++){

            Node compItem = compItems.item(j);
            if(compItem.getNodeName().equals("#text"))continue;

            switch (compItem.getNodeName()){
                case "Manufacture":
                    c.setManufacture(compItem.getTextContent());
                    break;
                case "Model":
                    c.setModel(compItem.getTextContent());
                    break;
                case "Processor":
                    c.setProcessor(compItem.getTextContent());
                    break;
                case "GPU":
                    c.setGPU(compItem.getTextContent());
                    break;
                case "RAM":
                    c.setRam(compItem.getTextContent());
                    break;
                case "OS":
                    c.setOS(compItem.getTextContent());
                    break;
                case "price":
                    c.setPrice(Double.valueOf(compItem.getTextContent()));
                    break;
                case "options":
                    List<OptionComputer> optionComputers = parseOptionComputer(compItem);
                    c.setOptions(compItem.getTextContent());
                    break;
            }
        }
        return c;
    }

    private static List<OptionComputer> parseOptionComputer(Node compItem) {

        List<OptionComputer> optionComputers = new ArrayList<>();
        NodeList options = compItem.getChildNodes();

        for (int k = 0; k < options.getLength(); k++) {
            Node option = options.item(k);

            if (option.getNodeName().equals("#text")) continue;
            OptionComputer optionComputer = new OptionComputer();
            NodeList optionItems = option.getChildNodes();

            for (int l = 0; l < optionItems.getLength(); l++) {
                Node optionItem = optionItems.item(l);

                if (optionItem.getNodeName().equals("#text")) continue;
                switch (optionItem.getNodeName()) {
                    case "Manufacture":
                        // optionComputer.setManufacture(optionItem.getTextContent());
                        break;
                }
            }
            optionComputers.add(optionComputer);
        }
        return optionComputers;
    }

    private static void updateComputerList(ArrayList<Compter> comps, Document doc){

        Element root = doc.createElement("shops");

        for (Compter c:comps){

            Element Computer = doc.createElement("computer");
            Element Manufacture = doc.createElement("Manufacture");
            Element Model = doc.createElement("Model");
            Element Processor = doc.createElement("Processor");
            Element GPU = doc.createElement("GPU");
            Element RAM = doc.createElement("RAM");
            Element OS = doc.createElement("OS");
            Element Price = doc.createElement("Price");

            Manufacture.setTextContent("Производитель: " + c.getManufacture() + " ");
            Model.setTextContent("Модель: " + c.getModel() + " ");
            Processor.setTextContent("Процессор: " + c.getProcessor() + " ");
            GPU.setTextContent("Графическая карта: " + c.getGPU() + " ");
            RAM.setTextContent("Оперативная память: " + c.getRam() + " ");
            OS.setTextContent("Операционная система: " + c.getOS() + " ");
            Price.setTextContent("Цена: " + c.getPrice() + " ");

            Manufacture.appendChild(Manufacture);
            Model.appendChild(Model);
            Processor.appendChild(Processor);
            GPU.appendChild(GPU);
            RAM.appendChild(RAM);
            OS.appendChild(OS);
            Price.appendChild(Price);

        }
        doc.getDocumentElement().appendChild((Node)root);
    }

    private static void readComputerItems(Node comps){

        NodeList compItems = comps.getChildNodes();

        for(int j=0;j < compItems.getLength(); j++){

            Node compItem = compItems.item(j);
            if(compItem.getNodeName().equals("#text")) continue;
            System.out.println("\t\t" + compItem.getNodeName());

            if (compItem.getNodeName().equals("option")) {

        //        readComputerItems(compItem);

            }
            {
                System.out.println("\t\t---" + compItem.getTextContent());

                System.out.println();
            }
        }
    }

    private static void readAttrComputer(Node comps){

        NamedNodeMap attrComputer = comps.getAttributes();

        for(int j = 0; j < attrComputer.getLength(); j++){

            Node att = attrComputer.item(j);
            System.out.println(att.getNodeName() + "-");
            System.out.println(att.getTextContent() + ";");

        }
    }

    private static void parseXML12(Document document){

        Element element = (Element) document.getDocumentElement();
        System.out.println(element.getTagName());
        NodeList nodeList = element.getChildNodes();

        for (int i = 0; i< nodeList.getLength(); i++){
            Node comps = nodeList.item(i);
            if(comps.getNodeName().equals("#text"))continue;
            System.out.println("\t" + comps.getNodeName() + "(");
            readAttrComputer(comps);
            System.out.println(")");
            readComputerItems(comps);

        }
    }



    @SuppressWarnings("empty-statement")

    public static void main(String[] args)throws SQLException , IOException, ParserConfigurationException , SAXException , SQLiteException ,InterruptedException{

        System.out.println();
        System.out.println("Computer Shop");
        System.out.println();

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("Error load JDBC");
            return;
        }

        Connection connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:Shop.db");
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, Charset.forName("cp1251")));

        ArrayList<Compter> comps = new ArrayList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
//        Document document = builder.parse("shop.xml");

        String n = "Проверка подключения...";
        if(connection.isValid(3)){
            n = " ";
            System.out.println("Подкючение установлено. Все готово к работе");
        }



        System.out.println(n);
        //System.out.println("Тестовый вывод");

        Statement statement = connection.createStatement();

        ResultSet rs1 = statement.executeQuery("select id,Manufacture,Model,Processor,GPU,RAM,MaxRam,OS,ROM,ScreenDiagonal,Weight,BattaryCapacity,WorkTime,price from Computer");
        while (rs1.next()) {
            int id = rs1.getInt(1);
            String Manufacture = rs1.getString(2);
            String Model = rs1.getString(3);
            String Processor = rs1.getString(4);
            String GPU = rs1.getString(5);
            String RAM = rs1.getString(6);
            String maxRam = rs1.getString(7);
            String OS = rs1.getString(8);
            String rom = rs1.getString(9);
            String screenDiagonal = rs1.getString(10);
            String weight = rs1.getString(11);
            String battaryCapacity = rs1.getString(12);
            String workTime = rs1.getString(13);
            Double price = rs1.getDouble(14);
            Compter comp = new Compter(id, Manufacture, Model, Processor, GPU, RAM, maxRam, OS, rom, screenDiagonal, weight, battaryCapacity, workTime, price);
            comps.add(comp);

        }

        for (Compter c : comps) {

           //System.out.println(c);

        }


        String temp = "";
        int id = 0;
        String model = "";
        do {
            System.out.println("Кто вы: (Client),(Staff),(Buyer)");
            String question = reader.readLine();
            {
                if (question.equalsIgnoreCase("Client")) {


                    System.out.println("Выберите желаемую операцию: (Show),(Buy By Id) ");
                    String question1 = reader.readLine();

                    Statement statement1 = connection.createStatement();
                    ResultSet rs = statement1.executeQuery("select * from Computer");

                    if (question1.equalsIgnoreCase("Show")) {

                        System.out.println("Выберите категорию: (Computer),(Phone)");
                        String question2 = reader.readLine();

                        if (question2.equalsIgnoreCase("Computer")) {

//                    parseXML12(document);
                            System.out.println("----Компьютеры----");

                            String m = "Asus' or '1'='1";

                            rs = statement1.executeQuery("select * from Computer where Manufacture like '" + m + "' ");
                            while (rs.next()) {
                                System.out.println("Номер компьютера на складе: " + rs.getInt("id") + " ");
                                System.out.println("Производитель: " + rs.getString("Manufacture") + " ");
                                System.out.println("Модель: " + rs.getString("Model") + " ");
                                System.out.println("Процессор: " + rs.getString("Processor") + " ");
                                System.out.println("Графический процессор: " + rs.getString("GPU") + " ");
                                System.out.println("Оперативная память: " + rs.getString("RAM") + " ");
                                System.out.println("Максимальная оперативная память: " + rs.getString("MaxRAM") + " ");
                                System.out.println("Операционная систама: " + rs.getString("OS") + " ");
                                System.out.println("Объем встроенного накопителя: " + rs.getString("ROM") + " ");
                                System.out.println("Размер экрана: " + rs.getString("ScreenDiagonal") + " ");
                                System.out.println("Вес(кг): " + rs.getString("Weight") + " ");
                                System.out.println("Емкость батареи: " + rs.getString("BattaryCapacity") + " ");
                                System.out.println("Время работы: " + rs.getString("WorkTime") + " ");
                                System.out.println("Цена(руб): " + rs.getDouble("price"));
                                System.out.println();

                            }
                        }
                        else if (question2.equalsIgnoreCase("Phone")) {

                            ResultSet rs2 = statement1.executeQuery("select * from Phone");

                            while (rs.next()) {
                                System.out.println("Id: " + rs2.getInt("id") + " ");
                                System.out.println("Производитель: " + rs2.getString("Manufacture") + " ");
                                System.out.println("Модель: " + rs2.getString("Model") + " ");
                                //System.out.println("Количество ядер процессора: " + rs2.getString("Corecount") + " ");
                                System.out.println("Оперативная память: " + rs2.getString("RAM") + " ");
                                System.out.println("Операционная система: " + rs2.getString("OS") + " ");
                                System.out.println("Объем встроенного накопителя: " + rs2.getString("ROM") + " ");
                                System.out.println("Емкость батареи: " + rs2.getString("BattaryCapacity") + " ");
                                System.out.println("Оперативная память: " + rs2.getString("WorkTime") + " ");
                                System.out.println("Количество мегапискелей в камере: " + rs2.getString("BackCameraMegaPixel") + " ");
                                System.out.println("Цена(руб): " + rs2.getDouble("price"));
                                System.out.println();

                            }
                        }
                        else System.out.println("Упс такая таблица не найдена");
                    }

                    System.out.println(" Новый запрос?(Yes/No) ");
                    temp = reader.readLine();

                    if (!temp.equalsIgnoreCase("No") && !temp.equalsIgnoreCase("Yes")) {
                        System.out.println("Вы не написали ни 'Yes', ни 'No'. В любом случае, программа прощается с вами.");
                    }
                }
                if (question.equalsIgnoreCase("Staff")) {

                    String log="";
                    System.out.println("Введите логин:"+log);
                    String str = reader.readLine();

                    if(str.equalsIgnoreCase("admin")){

                    }else {
                        System.out.println("Не верный логин");

                        System.out.println(" Новый запрос?(Yes/No) ");
                        temp = reader.readLine();

                        if (!temp.equalsIgnoreCase("No") && !temp.equalsIgnoreCase("Yes")) {
                            System.out.println("Вы не написали ни 'Yes', ни 'No'. В любом случае, программа прощается с вами.");
                        }
                    }

                    int pass=0;
                    System.out.println("Введите пароль:" + pass);
                    String take = reader.readLine();

                    try{

                        pass = Integer.parseInt(take);

                    }catch (NumberFormatException e){

                        System.out.println("Неверный пароль");
                        continue;

                    }

                System.out.println("Выберите желаемую операцию: (Show),(Add),(Change Id),(Change Manufacture),(Change Model),(Delete) ");
                String question1 = reader.readLine();

                Statement statement1 = connection.createStatement();
                ResultSet rs = statement1.executeQuery("select * from Computer");

                if (question1.equalsIgnoreCase("Show")) {

                    System.out.println("Выберите категорию: (Computer),(Phone)");
                    String question2 = reader.readLine();

                    if (question2.equalsIgnoreCase("Computer")) {

//                    parseXML12(document);
                        System.out.println("----Компьютеры----");

                        String m = "Asus' or '1'='1";

                        rs = statement1.executeQuery("select * from Computer where Manufacture like '" + m + "' ");
                        while (rs.next()) {
                            System.out.println("Номер компьютера на складе: " + rs.getInt("id") + " ");
                            System.out.println("Производитель: " + rs.getString("Manufacture") + " ");
                            System.out.println("Модель: " + rs.getString("Model") + " ");
                            System.out.println("Процессор: " + rs.getString("Processor") + " ");
                            System.out.println("Графический процессор: " + rs.getString("GPU") + " ");
                            System.out.println("Оперативная память: " + rs.getString("RAM") + " ");
                            System.out.println("Максимальная оперативная память: " + rs.getString("MaxRAM") + " ");
                            System.out.println("Операционная систама: " + rs.getString("OS") + " ");
                            System.out.println("Объем встроенного накопителя: " + rs.getString("ROM") + " ");
                            System.out.println("Размер экрана: " + rs.getString("ScreenDiagonal") + " ");
                            System.out.println("Вес(кг): " + rs.getString("Weight") + " ");
                            System.out.println("Емкость батареи: " + rs.getString("BattaryCapacity") + " ");
                            System.out.println("Время работы: " + rs.getString("WorkTime") + " ");
                            System.out.println("Цена(руб): " + rs.getDouble("price"));
                            System.out.println();

                        }
                    }
                    else if (question2.equalsIgnoreCase("Phone")) {

                        ResultSet rs2 = statement1.executeQuery("select * from Phone");

                        while (rs.next()) {
                            System.out.println("Id: " + rs2.getInt("id") + " ");
                            System.out.println("Производитель: " + rs2.getString("Manufacture") + " ");
                            System.out.println("Модель: " + rs2.getString("Model") + " ");
                            //System.out.println("Количество ядер процессора: " + rs2.getString("Corecount") + " ");
                            System.out.println("Оперативная память: " + rs2.getString("RAM") + " ");
                            System.out.println("Операционная система: " + rs2.getString("OS") + " ");
                            System.out.println("Объем встроенного накопителя: " + rs2.getString("ROM") + " ");
                            System.out.println("Емкость батареи: " + rs2.getString("BattaryCapacity") + " ");
                            System.out.println("Время работы: " + rs2.getString("WorkTime") + " ");
                            System.out.println("Количество мегапискелей в камере: " + rs2.getString("BackCameraMegaPixel") + " ");
                            System.out.println("Цена(руб): " + rs2.getDouble("price"));
                            System.out.println();

                        }

                    }
                    else System.out.println("Упс такая таблица не найдена");
                }


                else if (question1.equals("Add")) {
                    System.out.println("Выберите желаемую таблицу: (Computer), (Phone)");
                    String question2 = reader.readLine();
                    if (question2.equals("Computer")) {
                        System.out.println("Название компьютера: ");
                        String Manufacture = reader.readLine();
                        System.out.println("Модель компьютера: ");
                        String Model = reader.readLine();
                        System.out.println("Процессор: ");
                        String Processor = reader.readLine();
                        System.out.println("Графическая карта: ");
                        String GPU = reader.readLine();
                        System.out.println("Оперативная память: ");
                        String RAM = reader.readLine();
                        System.out.println("Оперативная память: ");
                        String maxRam = reader.readLine();
                        System.out.println("Операционная система: ");
                        String OS = reader.readLine();
                        System.out.println("Оперативная память: ");
                        String rom = reader.readLine();
                        System.out.println("Оперативная память: ");
                        String screenDiagonal = reader.readLine();
                        System.out.println("Оперативная память: ");
                        String weight = reader.readLine();
                        System.out.println("Оперативная память: ");
                        String battaryCapacity = reader.readLine();
                        System.out.println("Оперативная память: ");
                        String workTime = reader.readLine();
                        System.out.println("Цена компьютера: ");
                        Double Price = Double.valueOf(reader.readLine());

                        Compter c1 = new Compter(Manufacture, Model, Processor, GPU, RAM, maxRam, OS, rom, screenDiagonal, weight, battaryCapacity, workTime, Price);
                        statement.execute("insert into Computer (Manufacture,Model,Processor,GPU,RAM,MaxRAM,OS,ROM,ScreenDiagonal,Weight,BattaryCapacity,WorkTime,Price) values ('"
                                + c1.getManufacture() + "','"
                                + c1.getModel() + "','"
                                + c1.getProcessor() + "','" +
                                c1.getGPU() + "','" +
                                c1.getRam() + "','" +
                                c1.getMaxRam() + "','" +
                                c1.getOS() + "','" +
                                c1.getRom() + "','" +
                                c1.getScreenDiagonal() + "','" +
                                c1.getWeihgt() + "','" +
                                c1.getBattaryCapacity() + "','" +
                                c1.getWorkTime() + "','" +
                                c1.getPrice() + "')");
                    }
                    else if (question2.equalsIgnoreCase("Phone")) {

                        System.out.println("Производитель: ");
                        String Manufacture = reader.readLine();
                        System.out.println("Модель: ");
                        String Model = reader.readLine();
//                System.out.println("Производитель: ");
//                String CoreCount = reader.readLine();
                        System.out.println("Оперативная память: ");
                        String RAM = reader.readLine();
                        System.out.println("Основная камера(MP): ");
                        String BackCameraMegaPixel = reader.readLine();
                        System.out.println("Размер экран(дюймы): ");
                        String screenDiagonal = reader.readLine();
                        System.out.println("Операционная система: ");
                        String os = reader.readLine();
                        System.out.println("Объем встроееного накопителя: ");
                        String rom = reader.readLine();
                        System.out.println("Емкость батареи: ");
                        String battaryCapacity = reader.readLine();
                        System.out.println("Время работы: ");
                        String workTime = reader.readLine();
                        System.out.println("Цена: ");
                        Double Price = Double.valueOf(reader.readLine());

                        Phone p1 = new Phone(Manufacture, Model, RAM, screenDiagonal, os, rom, battaryCapacity, workTime, BackCameraMegaPixel, Price);

                        statement.execute("insert into Phone (Manufacture,Model,RAM,ScreenDiagonal,OS,ROM,BattaryCapacity,WorkTime,BackCameraMegaPixel,Price) values ('"
                                + p1.getManufacture() + "','" +
                                p1.getModel() + "','" +
                                //p1.getCoreCount()+"','"+
                                p1.getRam() + "','" +
                                p1.getScreenDiagonal() + "','" +
                                p1.getOs() + "','" +
                                p1.getRom() + "','" +
                                p1.getBattaryCapacity() + "','" +
                                p1.getWorkTime() + "','" +
                                p1.getBackCameraMegaPixel() + "','" +
                                p1.getPrice() +
                                "')");

                    } else {

                        System.out.println("Упс. Таблица, в которую вы хотите добавить данные исчезла. Не волнуйтесь, мы скоро найдём её.");
                    }
                }
                else if (question1.equalsIgnoreCase("Change Id")) {

                    System.out.println("Выберите желаемую таблицу: (Computer), (Phone)");
                    String question2 = reader.readLine();

                    if (question2.equalsIgnoreCase("Computer")) {

                        System.out.println("Id компьютера: ");
                        String i = reader.readLine();

                        try {

                            id = Integer.parseInt(i);

                        } catch (NumberFormatException e) {

                            System.out.println("Нужно было ввести ЧИСЛО");
                            continue;

                        }



                        System.out.println("Производитель компьютера: ");
                        String Manufacture = reader.readLine();
                        System.out.println("Модель компьютера: ");
                        String Model = reader.readLine();
                        System.out.println("Цена компьютера: ");
                        Double Price = Double.valueOf(reader.readLine());

//                System.out.println("ИД покупателя ");
//
//                statement.execute("update Computer set Manufacture = '"+Manufacture+"' ,Model = '"+Model+"' ,Price= '"+Price+"' , where id="+id+"");

                    } else {

                        System.out.println("Упс. Таблица, которую вы хотели модифицировать, испугалась и убежала . Не волнуйтесь, мы скоро найдём её.");
                    }

                }else if(question1.equalsIgnoreCase("Change By Manufacture")){

                }
                else if (question1.equalsIgnoreCase("Delete")) {

                    System.out.println("Каким способом хотите вы списать товар:(Delete By Id),(Delete By Model)");
                    String qeus = reader.readLine();

                    if (qeus.equalsIgnoreCase("Delete By Id")){
                    System.out.println("Выберите желаемую таблицу: (Computer), (Phone)");
                    String question2 = reader.readLine();

                    if (question2.equals("Computer")) {

                        System.out.println("Id компьюьтера: ");
                        String i = reader.readLine();
                        id = Integer.parseInt(i);
                        deleteComputerById(id, connection);

                    } else if (question2.equalsIgnoreCase("Phone")) {

                        System.out.println("Id phone");
                        String i = reader.readLine();
                        id = Integer.parseInt(i);
                        deletePhoneById(id, connection);

                    } else {

                        System.out.println("Упс. Наша команда зачистки не может найти эту таблицу.  ");

                    }

                }else if(qeus.equalsIgnoreCase("Delete By Model")){

                        System.out.println("Выберите желаемую таблицу: (Computer), (Phone)");
                        String question2 = reader.readLine();
                        if (question2.equals("Computer")) {
                            System.out.println("Модель компьюьтера: ");
                            String i = reader.readLine();
                            model = String.valueOf(i);
                            deleteComputerByModel(model, connection);

                        }
                    }

                }else {

                    System.out.println("Упс. Наша программа не смогла понять, что вы от неё хотите.");

                }

                System.out.println(" Новый запрос?(Yes/No) ");

                temp = reader.readLine();
                if (!temp.equalsIgnoreCase("No") && !temp.equalsIgnoreCase("Yes")) {

                    System.out.println("Вы не написали ни 'Yes', ни 'No'. В любом случае, программа прощается с вами.");
                }
            }
            }
        }
        while (temp.equalsIgnoreCase("Yes"));

    }
}