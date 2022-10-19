package no.hvl.dat100ptc.oppgave4;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSData;
import no.hvl.dat100ptc.oppgave2.GPSDataConverter;
import no.hvl.dat100ptc.oppgave2.GPSDataFileReader;
import no.hvl.dat100ptc.oppgave3.GPSUtils;

public class GPSComputer {
	
	private GPSPoint[] gpspoints;
	
	public GPSComputer(String filename) {

		GPSData gpsdata = GPSDataFileReader.readGPSFile(filename);
		gpspoints = gpsdata.getGPSPoints();

	}

	public GPSComputer(GPSPoint[] gpspoints) {
		this.gpspoints = gpspoints;
	}
	
	public GPSPoint[] getGPSPoints() {
		return this.gpspoints;
	}
	
	// beregn total distances (i meter)
	public double totalDistance() {

		double distance = 0;
		for (int i = 1; i < gpspoints.length; i++) {
			distance += GPSUtils.distance(gpspoints[i - 1], gpspoints[i]);
		}
		return distance;
		

	}

	// beregn totale høydemeter (i meter)
	public double totalElevation() {

		double elevation = 0;

		for (int i = 0; i < gpspoints.length; i++) {
			if (elevation < gpspoints[i].getElevation())
				elevation = gpspoints[i].getElevation();

		}
		;
		return elevation;

	}

	// beregn total tiden for hele turen (i sekunder)
	public int totalTime() {
		int time = 0;
		int time1 = 0;
		int time2 = 0;

		time1 = gpspoints[0].getTime();

		for (int i = 0; i < gpspoints.length; i++) {
			time2 = gpspoints[i].getTime();

		}
		time = time2 - time1;
		return time;
	
	}
		
	// beregn gjennomsnitshastighets mellom hver av gps punktene

	public double[] speeds() {
		double speed;
		double[] speeds = new double[gpspoints.length - 1];
		for (int i = 1; i < gpspoints.length; i++) {
			speed = GPSUtils.speed(gpspoints[i - 1], gpspoints[i]);
			speeds[i - 1] = speed;

		}
		return speeds;
		

	}
	
	public double maxSpeed() {
		
		double maxspeed = 0;
		double[] fart = speeds();

		for (int i = 1; i < fart.length; i++) {
			if (fart[i] > fart[i - 1])
				maxspeed = fart[i];
			else
				maxspeed = fart[i - 1];
		}
		return maxspeed;
		
		
	}

	public double averageSpeed() {

		double average = 0;
		
		average = (totalDistance() / totalTime()) * 3.6;
		return average;
		
	}

	/*
	 * bicycling, <10 mph, leisure, to work or for pleasure 4.0 bicycling,
	 * general 8.0 bicycling, 10-11.9 mph, leisure, slow, light effort 6.0
	 * bicycling, 12-13.9 mph, leisure, moderate effort 8.0 bicycling, 14-15.9
	 * mph, racing or leisure, fast, vigorous effort 10.0 bicycling, 16-19 mph,
	 * racing/not drafting or >19 mph drafting, very fast, racing general 12.0
	 * bicycling, >20 mph, racing, not drafting 16.0
	 */

	// conversion factor m/s to miles per hour
	public static double MS = 2.236936;

	// beregn kcal gitt weight og tid der kjøres med en gitt hastighet
	public double kcal(double weight, int secs, double speed) {

		double kcal;

		// MET: Metabolic equivalent of task angir (kcal x kg-1 x h-1)
		double met = 0;		
		double speedmph = speed * MS;

		if (speedmph < 10)
			met = 4.0;
		else if (speedmph >= 10 && speedmph <= 11.9)
			met = 6.0;
		else if (speedmph >= 12 && speedmph <= 13.9)
			met = 8.0;
		else if (speedmph >= 14 && speedmph <= 15.9)
			met = 10.0;
		else if (speedmph >= 16 && speedmph <= 20)
			met = 12.0;
		else if (speedmph > 20)
			met = 16.0;
		kcal = met * weight * secs / 3600;
		return kcal;
		
	}

	public double totalKcal(double weight) {

		double totalkcal = 0;
		double[] nyTab = speeds();
		for (int i = 0; i < nyTab.length; i++) {

			double kcal = kcal(weight, gpspoints[i + 1].getTime() - gpspoints[i].getTime(), nyTab[i]);

			totalkcal += kcal;
		}
		return totalkcal;


	}
	
	private static double WEIGHT = 80.0;
	
	public void displayStatistics() {

		System.out.println("==============================================");

		System.out.println("Total Time:	" + GPSUtils.formatTime(totalTime()));
		System.out.println("Total Distance:" + GPSUtils.formatDouble(totalDistance()));
		System.out.println("Total Elevation:" + GPSUtils.formatDouble(totalElevation()));
		System.out.println("Max Speed:	" + GPSUtils.formatDouble(maxSpeed()));
		System.out.println("Average speed:	" + GPSUtils.formatDouble(averageSpeed()));
		System.out.println("Energy:	" + GPSUtils.formatDouble(totalKcal(WEIGHT)));
	}

}
