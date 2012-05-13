package models;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import play.i18n.Messages;

/**
 * Default distances available
 * @author antoine
 *
 */
public enum DISTANCE {
	
	_5000M("5000m", 5000),
	_10KM("10km", 10000),
	_10MILES("10mi", 16093),
	_20KM("20km", 20000),
	_20MILES("20mi", 32186),
	HALF_MARATHON("hm", 21097),
	MARATHON("m", 42195),
	_50KM("50km", 50000),
	_100KM("100km", 100000),
	;
	private String code;
	private int meters;
	private String label;
	
	private DISTANCE(String code, int meters) {
		this.code = code;
		this.meters = meters;
		this.label = "distance." + code;
	}

	
	private static Map<String, DISTANCE> lookup = new HashMap<String, DISTANCE>();
	static {
		for (DISTANCE distance : DISTANCE.values()) {
			lookup.put(distance.code, distance);
		}
	}
	
	/**
	 * Get a DISTANCE object by its code
	 * @param code
	 * @return
	 */
	public static DISTANCE lookup(String code) {
		return lookup.get(code);
	}
	
	/**
	 * Get a Map of key/values to be used in html select elements
	 * @param code
	 * @return
	 */
	public static Map<String, String> options() {
		Map<String, String> options = new LinkedHashMap<String, String>();
		for (DISTANCE distance : DISTANCE.values()) {
			lookup.put(distance.code, distance);
			options.put(distance.code, Messages.get(distance.label));
		}
		return options;
	}
	
	
	public String getLabel() {
		return this.label;
	}
	
	public String getCode() {
		return this.code;
	}
	
	public int getMeters() {
		return meters;
	}

}
