package com.testtask.demo.utils;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class UtilsMethodsForApp {

    private static List<String> infoList = new ArrayList<>();

    public static List<String> getInstruction(String instructionPath) {
        if (infoList.isEmpty()) {
            try (BufferedReader bufferedReader =
                         new BufferedReader(
                                 new FileReader(
                                         System.getProperty("user.dir") + instructionPath))) {
                while (bufferedReader.ready()) {
                    infoList.add(bufferedReader.readLine());
                }
            } catch (IOException e) {

                log.error("Cannot read file: " + e);
            }
        }
        return infoList;
    }

}
