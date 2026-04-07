package com.hotel.runners;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features  = "src/test/resources/features",
        glue      = "com.hotel.stepdefinitions",
        plugin    = {"pretty"},
        tags      = "@happy_path",
        monochrome = true
)
public class CucumberTestSuite {
}
