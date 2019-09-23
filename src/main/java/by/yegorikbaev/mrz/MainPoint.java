package by.yegorikbaev.mrz;

import by.yegorikbaev.mrz.bean.Configuration;

import java.io.IOException;

public class MainPoint {
    public static void main(String[] args) throws IOException {
        new ApplicationRunner().run(new Configuration());
    }
}
