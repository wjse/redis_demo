package com.krr.demo;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ExcelTest {

    private static final String BUILDING_REGEX = "\\d+栋";

    private static final Pattern BUILDING_PATTERN = Pattern.compile(BUILDING_REGEX);

    private static final int BUILDING_UNUSED_ROW_NUM = 3;

    public static void main(String[] args) throws IOException {
        Workbook workbook = WorkbookFactory.create(new File("/Users/k66/Downloads/1.xlsx"));
        Sheet sheet = workbook.getSheetAt(0);
        int lastRowNum = sheet.getLastRowNum();
        List<Building> list = new ArrayList<>(30);
        for(int i = 0 ; i < lastRowNum; i++){
            String buildingName = getBuildingName(sheet , i);
            if(null != buildingName){
                Building building = getBuilding(sheet , buildingName , i + 1 + BUILDING_UNUSED_ROW_NUM);
                list.add(building);
            }
        }

        genExcelResult(list);
        genExcelResultForRoom(list);
    }

    private static final Map<Integer , FloorName> F_NAME_MAPPING = new HashMap<Integer , FloorName>(){{
        put(1 , new FloorName("一层" , "1F" , "01"));
        put(2 , new FloorName("二层" , "2F" , "02"));
        put(3 , new FloorName("三层" , "3F" , "03"));
        put(4 , new FloorName("四层" , "4F" , "04"));
        put(5 , new FloorName("五层" , "5F" , "05"));
        put(6 , new FloorName("六层" , "6F" , "06"));
        put(7 , new FloorName("七层" , "7F" , "07"));
        put(8 , new FloorName("八层" , "8F" , "08"));
        put(9 , new FloorName("九层" , "9F" , "09"));
        put(10 , new FloorName("十层" , "10F" , "10"));
        put(-1 , new FloorName("负一层" , "B1F" , "-1"));
        put(-2 , new FloorName("负二层" , "B2F" , "-2"));
    }};

    private static void genExcelResult(List<Building> list) throws IOException {
        List<ExcelResult> results = new ArrayList<>();
        for(Building building : list){
            String buildingCode = genBuildingCode(building.buildingName);
            building.floorMap.forEach((k , v) -> {
                FloorName floorNameObj = F_NAME_MAPPING.get(k);
                ExcelResult er = new ExcelResult();
                er.floorName = String.format("%s%s" , buildingCode , floorNameObj.cn);
                er.floorCode = String.format("%s%s" , buildingCode , floorNameObj.code);
                er.buildingCode = buildingCode;
                er.floorEN = floorNameObj.en;
                er.buildArea = v.buildArea;
                er.useArea = v.useArea;
                results.add(er);
            });
        }

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        for(int i = 0 ; i < results.size(); i++){
            ExcelResult er = results.get(i);
            Row row = sheet.createRow(i);
            row.createCell(0).setCellValue(er.floorName);
            row.createCell(1).setCellValue(er.floorCode);
            row.createCell(2).setCellValue(er.buildingCode);
            row.createCell(3).setCellValue(er.floorEN);
            row.createCell(4).setCellValue(er.buildArea.doubleValue());
            row.createCell(5).setCellValue(er.useArea.doubleValue());
            row.createCell(6).setCellValue(er.buildArea.doubleValue());
        }
        workbook.write(new FileOutputStream("/Users/k66/Downloads/2.xlsx"));
    }

    private static void genExcelResultForRoom(List<Building> list) throws IOException {
        List<ExcelResultForRoom> results = new ArrayList<>();
        for(Building building : list){
            String buildingCode = genBuildingCode(building.buildingName);
            building.floorMap.forEach((k , v) -> {
                FloorName floorNameObj = F_NAME_MAPPING.get(k);
                String floorCode = floorNameObj.code;
                List<ExcelResultForRoom> roomList = v.list.stream().map(room -> {
                    ExcelResultForRoom erRoom = new ExcelResultForRoom();
                    erRoom.buildingCode = buildingCode;
                    erRoom.floorCode = String.format("%s%s" , buildingCode , floorCode);
                    erRoom.posCode = room.roomName < 10 ? String.format("%s0%s" , erRoom.floorCode , room.roomName) : String.format("%s%s", erRoom.floorCode , room.roomName);
                    erRoom.posName = String.format("%s#" , erRoom.posCode);
                    erRoom.buildArea = room.buildArea;
                    erRoom.useArea = room.useArea;
                    return erRoom;
                }).collect(Collectors.toList());
                results.addAll(roomList);
            });
        }

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        for(int i = 0 ; i < results.size(); i++){
            ExcelResultForRoom er = results.get(i);
            Row row = sheet.createRow(i);
            row.createCell(0).setCellValue(er.buildingCode);
            row.createCell(1).setCellValue(er.floorCode);
            row.createCell(2).setCellValue(er.posCode);
            row.createCell(3).setCellValue(er.posName);
            row.createCell(4).setCellValue(er.buildArea.doubleValue());
            row.createCell(5).setCellValue(er.useArea.doubleValue());
            row.createCell(6).setCellValue(er.buildArea.doubleValue());
        }
        workbook.write(new FileOutputStream("/Users/k66/Downloads/3.xlsx"));
    }

    private static String genBuildingCode(String buildingName){
        int buildingNum = Integer.parseInt(buildingName.replace("栋" , ""));
        String sBuildingNum = buildingNum < 10 ? String.format("0%s" , buildingNum) : String.valueOf(buildingNum);
        if(buildingNum < 15){
            return String.format("W%s" , sBuildingNum);
        }else if(buildingNum == 15){
            return String.format("S%s" , sBuildingNum);
        }else{
            return String.format("E%s" , sBuildingNum);
        }
    }

    private static Building getBuilding(Sheet sheet , String buildingName , int buildingStartIndex){
        Building building = new Building(buildingName , new HashMap<>());
        for(int i = buildingStartIndex; i < sheet.getLastRowNum(); i++){
            Row row = sheet.getRow(i);
            String value = row.getCell(0).getStringCellValue();
            if(null == value || "".equals(value.trim())){
                break;
            }

            int dFloor = (int) row.getCell(3).getNumericCellValue();
            if(building.floorMap.get(dFloor) == null){
                building.floorMap.put(dFloor , new Floor(dFloor));
            }

            int roomName = (int) row.getCell(4).getNumericCellValue();
            double buildArea = row.getCell(5).getNumericCellValue();
            double useArea = row.getCell(6).getNumericCellValue();
            Room room = new Room(roomName , BigDecimal.valueOf(buildArea) , BigDecimal.valueOf(useArea));
            building.floorMap.get(dFloor).addRoom(room);
        }

        return building;
    }

    private static String getBuildingName(Sheet sheet , int rowIndex){
        Row row = sheet.getRow(rowIndex);
        if(null == row){
            System.out.printf("% row is null.%n" , rowIndex);
            System.exit(0);
            return null;
        }

        Cell cell = row.getCell(0);
        if(null == cell){
            System.out.printf("% row's cell[0] is null.%n" , rowIndex);
            return null;
        }

        String value = cell.getStringCellValue();
        if(null == value){
            System.out.printf("% row's cell[0]'s value is null.%n" , rowIndex);
            return null;
        }

        return BUILDING_PATTERN.matcher(value).matches() ? value : null;
    }
}

class Building{
    String buildingName;
    Map<Integer , Floor> floorMap;

    Building(String buildingName , Map<Integer, Floor> floorMap){
        this.buildingName = buildingName;
        this.floorMap = floorMap;
    }

}

class Floor{
    int floorName;
    BigDecimal buildArea;
    BigDecimal useArea;

    List<Room> list = new ArrayList<>();

    Floor(int floorName) {
        this.floorName = floorName;
    }

    public BigDecimal getBuildArea() {
        return this.list.stream().map(room -> room.buildArea).reduce((a , b) -> a.add(b).divide(BigDecimal.ONE , RoundingMode.HALF_DOWN)).get();
    }

    public BigDecimal getUseArea(){
        return this.list.stream().map(room -> room.useArea).reduce((a , b) -> a.add(b).divide(BigDecimal.ONE , RoundingMode.HALF_DOWN)).get();
    }

    public void addRoom(Room room) {
        this.list.add(room);
        this.buildArea = this.list.stream().map(r -> r.buildArea).reduce((a , b) -> a.add(b).divide(BigDecimal.ONE , RoundingMode.HALF_DOWN)).get();
        this.useArea = this.list.stream().map(r -> r.useArea).reduce((a , b) -> a.add(b).divide(BigDecimal.ONE , RoundingMode.HALF_DOWN)).get();
    }
}

class Room {
    int roomName;

    BigDecimal buildArea;
    BigDecimal useArea;

    Room(int roomName, BigDecimal buildArea, BigDecimal useArea) {
        this.roomName = roomName;
        this.buildArea = buildArea;
        this.useArea = useArea;
    }
}

class FloorName{
    String cn;
    String en;

    String code;

    public FloorName(String cn, String en , String code) {
        this.cn = cn;
        this.en = en;
        this.code = code;
    }
}

class ExcelResult{
    String floorName;
    String floorCode;
    String buildingCode;

    String floorEN;

    BigDecimal buildArea;
    BigDecimal useArea;
}

class ExcelResultForRoom{
    String buildingCode;
    String floorCode;
    String posCode;
    String posName;
    BigDecimal buildArea;
    BigDecimal useArea;
}