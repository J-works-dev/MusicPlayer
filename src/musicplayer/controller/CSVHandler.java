package musicplayer.controller;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CSVHandler {
    private static int columnCount = 0;
    private static String[] title;
    private static boolean isTitle = true;
    
    public static ObservableList<Playlist> readCSV(File fileName) {

        ObservableList<RowData> results = FXCollections.observableArrayList();
        CSVReader reader;
//        System.out.println("CSV handler accessed");
        try {
            reader = new CSVReader(new FileReader(fileName));
            String[] nextLine;
            RowData nextResult;
            
            while ((nextLine = reader.readNext()) != null) {
                if (isTitle) {
                    setTitle(nextLine);
                    setColumnCount(getTitle().length);
                    isTitle = false;
                } else {
                    nextResult = new RowData();
                    switch(getColumnCount()) {
                        case 1:
                            nextResult.setData1(nextLine[0]);
                            break;
                        case 2:
                            nextResult.setData1(nextLine[0]);
                            nextResult.setData2(nextLine[1]);
                            break;
                        case 3:
                            nextResult.setData1(nextLine[0]);
                            nextResult.setData2(nextLine[1]);
                            nextResult.setData3(nextLine[2]);
                            break;
                        case 4:
                            nextResult.setData1(nextLine[0]);
                            nextResult.setData2(nextLine[1]);
                            nextResult.setData3(nextLine[2]);
                            nextResult.setData4(nextLine[3]);
                            break;
                        case 5:
                            nextResult.setData1(nextLine[0]);
                            nextResult.setData2(nextLine[1]);
                            nextResult.setData3(nextLine[2]);
                            nextResult.setData4(nextLine[3]);
                            nextResult.setData5(nextLine[4]);
                            break;
                        case 6:
                            nextResult.setData1(nextLine[0]);
                            nextResult.setData2(nextLine[1]);
                            nextResult.setData3(nextLine[2]);
                            nextResult.setData4(nextLine[3]);
                            nextResult.setData5(nextLine[4]);
                            nextResult.setData6(nextLine[5]);
                            break;
                    }
                    results.add(nextResult);
                }
            }
            
//            results.remove(0);

        } catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println("result: " + results);
        return results;
    }

    public static void writeCSV(ObservableList<RowData> results) {

        CSVWriter writer;
        File file = new File("editedData.csv");
        String[] nextData = new String[getColumnCount()];
        
        try {
            writer = new CSVWriter(new FileWriter(file));
            
            writer.writeNext(getTitle());

            for (RowData data : results) {
                switch(getColumnCount()) {
                    case 1:
                        nextData[0] = data.getData1();
                        break;
                    case 2:
                        nextData[0] = data.getData1();
                        nextData[1] = data.getData2();
                        break;
                    case 3:
                        nextData[0] = data.getData1();
                        nextData[1] = data.getData2();
                        nextData[2] = data.getData3();
                        break;
                    case 4:
                        nextData[0] = data.getData1();
                        nextData[1] = data.getData2();
                        nextData[2] = data.getData3();
                        nextData[3] = data.getData4();
                        break;
                    case 5:
                        nextData[0] = data.getData1();
                        nextData[1] = data.getData2();
                        nextData[2] = data.getData3();
                        nextData[3] = data.getData4();
                        nextData[4] = data.getData5();
                        break;
                    case 6:
                        nextData[0] = data.getData1();
                        nextData[1] = data.getData2();
                        nextData[2] = data.getData3();
                        nextData[3] = data.getData4();
                        nextData[4] = data.getData5();
                        nextData[5] = data.getData6();                        
                        break;
                }
                
                writer.writeNext(nextData);
            }
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getColumnCount() {
        return columnCount;
    }

    public static void setColumnCount(int aColumnCount) {
        columnCount = aColumnCount;
    }

    public static String[] getTitle() {
        return title;
    }

    public static void setTitle(String[] aTitle) {
        title = aTitle;
    }
}
