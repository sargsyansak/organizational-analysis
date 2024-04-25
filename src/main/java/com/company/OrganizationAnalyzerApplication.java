package com.company;

import com.company.organization.service.impl.CsvReaderServiceImpl;
import com.company.organization.service.impl.EmployeeAnalyzerServiceImpl;
import com.company.organization.service.CsvReaderService;
import com.company.organization.service.EmployeeAnalyzerService;
import com.company.organization.exception.CsvIOException;
import com.company.organization.exception.InvalidCsvDataException;
import com.company.organization.exception.InvalidCsvStructureException;
import com.company.organization.data.Employee;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.String.format;

public class OrganizationAnalyzerApplication {
    private final CsvReaderService csvReader;
    private final EmployeeAnalyzerService employeeAnalyzer;
    private static final Logger logger = Logger.getLogger(OrganizationAnalyzerApplication.class.getName());

    public OrganizationAnalyzerApplication(CsvReaderService csvReader, EmployeeAnalyzerService employeeAnalyzer) {
        this.csvReader = csvReader;
        this.employeeAnalyzer = employeeAnalyzer;
    }

    public void run(String fileName) {
        if (fileName == null || fileName.isBlank()) {
            logger.warning("File name is empty or null.");
            return;
        }

        try {
            Map<Integer, Employee> employees = csvReader.readEmployeesDataFromCsvFile(fileName);
            if (!employees.isEmpty()) {
                logger.info("Analyzing employee data...");
                employeeAnalyzer.processAnalyze(employees);
            } else {
                String message = format("No employee data found in the file: %s", fileName);
                logger.warning(message);
            }
        } catch (InvalidCsvDataException | InvalidCsvStructureException | CsvIOException e) {
            logger.log(Level.SEVERE, "An error occurred while processing the CSV file.", e);
        }
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            logger.warning("Please provide the path to the employee information CSV file.");
            return;
        }

        CsvReaderService csvReader = new CsvReaderServiceImpl();
        EmployeeAnalyzerService employeeAnalyzer = new EmployeeAnalyzerServiceImpl();

        OrganizationAnalyzerApplication application = new OrganizationAnalyzerApplication(csvReader, employeeAnalyzer);
        application.run(args[0]);
    }
}