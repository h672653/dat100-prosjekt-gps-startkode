package no.hvl.dat100ptc.oppgave3;

import static java.lang.Math.*;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.*;

import no.hvl.dat100ptc.TODO.*
;
public class GPSUtils {

	public static double findMax(double[] da) {
		
		double max; 
		
		max = da[0];
		
		for (double d : da) {
			if (d > max) {
				max = d;
			}
		}
		
		return max;
	}

	public static double findMin(double[] da) {

		double min;

	min = da[0];
	
	for(double d : da) {
		if (d < min) {
			min = d;
		}
	}
		
		return min;
//(findMin) finne minste tallet ved å flytte, ved å utvide for-løkke.
	}

	public static double[] getLatitudes(GPSPoint[] gpspoints) {

	double[] latitudes = new double [gpspoints.length];
	
	for (int i = 0; i < latitudes.length; i++) {
		latitudes[i] = gpspoints[i].getLatitude();
	}
	return latitudes;
	//(getLatitudes)tabell med gpspunkter, for-løkke
	//nytabell med double og kopierer verdiene
	//getLatitudes metoden fra GPSPoint klassen
	}
	
	public static double[] getLongitudes(GPSPoint[] gpspoints) {

		double[] longitude = new double[gpspoints.length];
		
		for (int i = 0; i < gpspoints.length; i++) {
			longitude[i] = gpspoints[i].getLongitude();
		}
		return longitude;
		//(getLongitudes) samme men med longitude/lengdegrader
	}
	private static int R = 6371000; // jordens radius

	public static double distance(GPSPoint gpspoint1, GPSPoint gpspoint2) {

	double d; 
	double latitude1, longitude1, latitude2, longitude2;
	
	latitude1 = gpspoint1.getLatitude();
	latitude1 = Math.toRadians(latitude1);
	
	latitude2 = gpspoint2.getLatitude();
	latitude2 = Math.toRadians(latitude2);
	
	double deltaLat =  latitude2 - latitude1;
			
	longitude1 = gpspoint1.getLongitude();
	longitude1 = Math.toRadians(longitude1);
	
	longitude2 = gpspoint2.getLongitude();
	longitude2 = Math.toRadians(longitude2);
	
	double deltaLongi = longitude2 - longitude1;
	
	//formel
	double a = Math.pow(Math.sin(deltaLat / 2) , 2)
			+ (Math.cos(latitude1)*Math.cos(latitude2)) * Math.pow(Math.sin(deltaLongi / 2), 2); 
			
	
	double c = 2 * Math.atan2((Math.sqrt(a)) , (Math.sqrt((1-a))));
	
	
	d = R * c;
	
	return d;
	// haversinformel til å regne distansen mellom 2 punkter
	// nye double verdier for latitudes og longitudes
	}

	public static double speed(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		int secs;
		double speed;

int time1 = gpspoint1.getTime();
int time2 = gpspoint2.getTime();

secs = time2 - time1;

double distance = distance(gpspoint1, gpspoint2) /secs ;
speed = distance* 3.6;

return speed;
//(speed) du kan finne gjennomsnitt hastighet mellom 2 punkter
// fart = distanse / tid
	}
	public static String formatTime(int secs) {

		String timestr;
		String TIMESEP = ":";

		int hh = secs / 3600;
		int rest = secs % 3600;
		int mm = rest / 60;
		int rest2 = rest % 60;

		String hr = String.format("%02d", hh);
		String min = String.format("%02d", mm);
		String sec = String.format("%02d", rest2);

		timestr = String.format("%1$10s", "  " + hr + TIMESEP + min + TIMESEP + sec);

		return timestr;
	//(formatTime)Returnerer tid etter midnatt på gitt form. Gjør om tid i sekund til timer,
	// minutt og rest sekund. Streng skal vere 10 tegn lang.
	}
	private static int TEXTWIDTH = 10;

	public static String formatDouble(double d) {

		
		String str;
		double svar = (double) Math.round(d * 100.0) / 100.0;

		str = String.format("%1$" + TEXTWIDTH + "s", svar);
		return str;
		
		//(formatDouble) bruker string.format metoden med math.round for å runde tallet til 2 desimaler
		//
	}
}
