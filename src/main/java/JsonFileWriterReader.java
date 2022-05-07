import java.io.*;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.core.type.TypeReference;

public class JsonFileWriterReader {
    private File file = new File("gameStat.json");
    private ObjectMapper mapper = new ObjectMapper();
    private static JsonFileWriterReader writerReader = new JsonFileWriterReader();
    private JsonFileWriterReader(){
        mapper.registerModule(new JavaTimeModule());
    }
    public static JsonFileWriterReader getInstance(){
        return writerReader;
    }
    public void appendToList(GameInfo info){
        try{
            List<GameInfo> statisticsList = new ArrayList<GameInfo>();
            if(file.exists()) {
                FileReader reader = new FileReader(file);
                statisticsList = mapper.readValue(reader,new TypeReference<ArrayList<GameInfo>>(){});
            }
            FileWriter writer = new FileWriter(file);
            statisticsList.add(info);
            mapper.writeValue(writer,statisticsList);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public List<Map.Entry<String,Integer>> getTopScoreList(){
        Map<String,Integer> statisticsMap = new HashMap<>();
        try(FileReader reader = new FileReader(file)){
            List<GameInfo> statisticsList = new ArrayList<GameInfo>();
            statisticsList = mapper.readValue(reader,new TypeReference<ArrayList<GameInfo>>(){});
            statisticsList.stream().forEach(element->{
                if(statisticsMap.containsKey(element.getWinner())) {
                    statisticsMap.put(element.getWinner(), Integer.valueOf(statisticsMap.get(element.getWinner()) + Integer.valueOf(1)));
                }else{
                    statisticsMap.put(element.getWinner(), Integer.valueOf(Integer.valueOf(1)));
                }
            });

        }catch(Exception e){
            e.printStackTrace();
        }
        return sortMap(statisticsMap).stream().limit(5).toList();
    }
    private List<Map.Entry<String,Integer>> sortMap(Map<String,Integer> map){
        List<Map.Entry<String,Integer>> list = new ArrayList<Map.Entry<String,Integer>>(map.entrySet());
        Collections.sort(list,(firstElement,secondElement)->{
            return secondElement.getValue().compareTo(firstElement.getValue());
        });
        return list;
    }


}
