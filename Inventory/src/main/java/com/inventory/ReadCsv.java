package com.inventory;
import java.io.*;
import java.util.*;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import lombok.Builder;

public class ReadCsv {

    private static HashMap<String, Items> inventoryItems = new HashMap<>();
    private static HashSet<String> cards = new HashSet<>();
    @SuppressWarnings("resource")
    public static void main(String[] args) throws Exception
    {
        new FileOutputStream("src/main/resources/input/TotalAmountOutput.csv").close();
        new FileOutputStream("src/main/resources/input/ErrorMessage.txt").close();
        System.out.println("Your input File is :");
        List<String[]> allRows =readCSVFile("src/main/resources/input/Dataset.csv");
        List<String[]> allCards =readCSVFile("src/main/resources/input/Cards.csv");
        List<String[]> inputs =readCSVFile("Input.csv");

        //read csv
        for(String[] row : allRows){
            Items item= toItems(Arrays.toString(row));
            if(item!=null)
                inventoryItems.put(item.getItem(), item);

        }
        for(String[] card:allCards)
        {
            cards.add(card[0]);

        }
        EssentialsCategory essentials = new EssentialsCategory();
        LuxuryCategory luxury = new LuxuryCategory();
        MiscCategory misc = new MiscCategory();

        //make use of chain of responsibility pattern
        essentials.setNextChainOfResponsibility(luxury);
        luxury.setNextChainOfResponsibility(misc);

        Set<String> validation = new HashSet<>();

        double totalAmount=0;
        Set<String> alreadyReadEssentials = new HashSet<>();
        Set<String> alreadyReadLuxury = new HashSet<>();
        Set<String> alreadyReadMisc = new HashSet<>();
        for(String[] map : inputs) {
            cards.add(map[2]);

            double amount= essentials.calculateAmount(inventoryItems,map[0],Integer.parseInt(map[1]),validation,alreadyReadEssentials,alreadyReadLuxury,alreadyReadMisc);
            totalAmount+=amount;
        }
        if(CalculateTotal.getTotalEssential()<0)
            validation.addAll(alreadyReadEssentials);

        if(CalculateTotal.getTotalLuxury()<0)
            validation.addAll(alreadyReadLuxury);

        if(CalculateTotal.getTotalMisc()<0)
            validation.addAll(alreadyReadMisc);

        List<String[]> updatedCards = new LinkedList<>();
        String[] header= new String[]{"CardNumber"};
        updatedCards.add(header);
        for(String card :cards)
        {
            String[] addCard= new String[]{card};
            updatedCards.add(addCard);
        }


        CSVWriter writer = new CSVWriter(new FileWriter("src/main/resources/input/Cards.csv"), ',');
        writer.writeAll(updatedCards);
        writer.flush();
        writer.close();

        // write total amount in output file
        if(validation.size()==0) {
            List<String[]> populateFile= new LinkedList<>();
            populateFile.add(new String[]{"Amt Paid"});
            populateFile.add(new String[]{""+totalAmount});
            writer = new CSVWriter(new FileWriter("src/main/resources/input/TotalAmountOutput.csv"), ',');
            writer.writeAll(populateFile);
            writer.flush();
            writer.close();
        }
        else
        {
            System.out.println("Please correct quantities");
            File file = new File("src/main/resources/input/ErrorMessage.txt");

            try (BufferedWriter bf = new BufferedWriter(new FileWriter(file))) {

                // create new BufferedWriter for the output file
                bf.write("Please correct quantities.");
                bf.newLine();

                // iterate map entries
                for ( String input : validation) {

                    // put key and value separated by a colon

                    bf.write(input);

                    // new line
                    bf.newLine();
                }

                bf.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private static List<String[]> readCSVFile(String filePath) throws IOException {
        CSVReader reader = new CSVReader(new FileReader(filePath), ',', '"', 1);
        return  reader.readAll();

    }

    private static Items toItems(String column) {
        column = column.substring(1,column.length()-1);
        String[] columns = column.split(",");
        if(columns.length<4)
             return null;


        return Items.builder().Category(toValue(columns[0].trim()))
                .item(columns[1].trim())
                .quantity(Integer.valueOf(columns[2].trim()))
                .price(Double.valueOf(columns[3].trim()))
                .build();
    }

    static Category toValue(String category)
    {
        return switch (category) {
            case "Essential" -> Category.CATEGORY_ESSENTIALS;
            case "Luxury" -> Category.CATEGORY_LUXURY;
            case "Miscellaneous" -> Category.CATEGORY_MISCELLENEOUS;
            default -> Category.CATEGORY_MISCELLENEOUS;
        };
    }
}

