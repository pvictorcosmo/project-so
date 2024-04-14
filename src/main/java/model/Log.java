package model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;

public class Log{
    private static BufferedWriter bufferedWriter;
    private static FileWriter fileWriter;
    public static void doLog(List<Car> carros){
        StringBuilder builder = new StringBuilder();
        for(Car carro : carros){
            builder.append("ID: " + carro.getId() + " DIRECAO: " + carro.getCarDirection() + " ESTADO: " + carro.getApplicationState() + "\r\n");
        }
        doLog(builder.toString());
    }
    public static void doLog(Car carro){
        doLog("ID: " + carro.getId() + " DIRECAO: " + carro.getCarDirection() + " ESTADO: " + carro.getApplicationState() + "\r\n");
    }
    public static void doLog(String texto){
        try {
            fileWriter = new FileWriter("log.txt", true);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("------------------------------------------------------------------------\r\n" + texto + "\r\n------------------------------------------------------------------------\r\n");
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
        System.out.println("------------------------------------------------------------------------");
        System.out.println(texto);
        System.out.println("------------------------------------------------------------------------");
    }
}
