package edu.nku.CSC440.stagbar.service;

import edu.nku.CSC440.stagbar.dataaccess.data.Alcohol;
import edu.nku.CSC440.stagbar.dataaccess.data.ReportItem;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ReportService extends BaseService {

	private static final ReportService REPORT_SERVICE = new ReportService();

	private ReportService() {}

	public static ReportService getInstance() {
		return REPORT_SERVICE;
	}

	/** Calculates report data for all alcohol in database for given date range. */
	public Set<ReportItem> findReportDataForDateRange(LocalDate startDate, LocalDate endDate) {
		//TODO: Fill this set via database. May be able to do it with a single query.
		return new HashSet<>();
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

}
