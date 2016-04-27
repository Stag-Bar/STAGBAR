package edu.nku.CSC440.stagbar.service;

import edu.nku.CSC440.stagbar.dataaccess.data.Alcohol;
import edu.nku.CSC440.stagbar.dataaccess.data.ReportItem;
import edu.nku.CSC440.stagbar.dataaccess.databaseConnection.Connect;

import javax.swing.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReportService extends BaseService {

	private static final ReportService REPORT_SERVICE = new ReportService();
	private static final Logger log = Logger.getLogger(ReportService.class.getName());


	private ReportService() {}

	public static ReportService getInstance() {
		return REPORT_SERVICE;
	}

	/** Calculates report data for all alcohol in database for given date range. */
	public Set<ReportItem> findReportDataForDateRange(LocalDate startDate, LocalDate endDate) {
		return Connect.getInstance().generateReport(startDate, endDate);
	}

	/** Calculates report data for all alcohol in database for today. */
	public Set<ReportItem> findReportDataForToday() {
		return findReportDataForDateRange(LocalDate.now(), LocalDate.now());
	}

	/**
	 * Calculates amount discrepancies for all alcohol in database for given date range.
	 *
	 * @return Map(alcohol, discrepancyAmount) of discrepancy results.
	 */
	public Map<Alcohol, Double> getDiscrepancies_Amount(LocalDate startDate, LocalDate endDate) {
		Set<ReportItem> reportItemSet = findReportDataForDateRange(startDate, endDate);
		Map<Alcohol, Double> results = new HashMap<>();
		for(ReportItem item : reportItemSet) results.put(item.getAlcohol(), item.getDiscrepency_Amount());
		return results;
	}

	/**
	 * Calculates bottle discrepancies for all alcohol in database for given date range.
	 *
	 * @return Map(alcohol, discrepancyAmount) of discrepancy results.
	 */
	public Map<Alcohol, Integer> getDiscrepancies_Bottles(LocalDate startDate, LocalDate endDate) {
		Set<ReportItem> reportItemSet = findReportDataForDateRange(startDate, endDate);
		Map<Alcohol, Integer> results = new HashMap<>();
		for(ReportItem item : reportItemSet) results.put(item.getAlcohol(), item.getDiscrepency_Bottles());
		return results;
	}

	public void printTable(JTable table) {
		try {
			if(!table.print()) {
				log.info("User cancelled printing");
			}
		} catch(java.awt.print.PrinterException e) {
			log.log(Level.SEVERE, "Cannot print %s%n", e);
		}
	}

}
