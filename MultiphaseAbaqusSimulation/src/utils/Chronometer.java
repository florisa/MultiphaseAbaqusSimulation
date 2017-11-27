package utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Chronometer for profiling pieces of code (snippets).
 * 
 * It could be used with the DEFAULT implementation of Cron, or with custom
 * crons, by passing a name for the custom cron.
 * 
 *
 */
public final class Chronometer {

	private Map<String, Cron> cronMap;

	private Chronometer() {
		cronMap = new HashMap<String, Cron>();
		cronMap.put("DEFAULT", new Cron());
	}

	/**
	 * Static Factory Method which creates a new Chronometer instance.
	 * 
	 * @return new Chronometer instance
	 */
	public static Chronometer newInstance() {
		return new Chronometer();
	}

	/**
	 * Starts the DEFAULT chronometer.
	 * 
	 * @param snippetMarker
	 *            Name or simple marker of snippet to be profiled
	 */
	public void start(String snippetMarker) {
		this.getCronInstance("DEFAULT").start(snippetMarker);
	}

	/**
	 * Pauses the DEFAULT chronometer.
	 */
	public void pause() {
		this.getCronInstance("DEFAULT").pause();
	}

	/**
	 * Stops the DEFAULT chronometer and prints on console the elapsed time.
	 * 
	 * @param snippetMarker
	 *            Name or simple marker of snippet to be profiled
	 */
	public void stop(String snippetMarker) {
		this.getCronInstance("DEFAULT").stop(snippetMarker);
	}

	/**
	 * Starts custom chronometer.
	 * 
	 * @param cronName
	 *            Name of instance of the chronometer to be manipulated
	 * @param snippetMarker
	 *            Name or simple marker of snippet to be profiled
	 */
	public void start(String cronName, String snippetMarker) {
		this.getCronInstance(cronName).start(snippetMarker);
	}

	/**
	 * Pauses custom chronometer.
	 * 
	 * @param cronName
	 *            Name of instance of the chronometer to be manipulated
	 */
	public void pause(String cronName) {
		this.getCronInstance(cronName).pause();
	}

	/**
	 * Stop custom chronometer and prints on console the elapsed time.
	 * 
	 * @param cronName
	 *            Name of instance of the chronometer to be manipulated
	 * @param snippetMarker
	 *            Name or simple marker of snippet to be profiled
	 */
	public void stop(String cronName, String snippetMarker) {
		this.getCronInstance(cronName).stop(snippetMarker);
	}

	/**
	 * Retrieves the Cron instance for cronName passed. If it don't exists,
	 * create and returns a new instance.
	 * 
	 * @param cronName
	 *            Name of instance of the chronometer to be manipulated
	 * @return The Cron instance for cronName passed
	 */
	private Cron getCronInstance(String cronName) {
		if (!this.cronMap.containsKey(cronName)) {
			this.cronMap.put(cronName, new Cron());
		}

		return this.cronMap.get(cronName);
	}

	/**
	 * A helper class for chronometer operations.
	 * 
	 * 
	 *
	 */
	private final class Cron {

		private long startValue;
		private long stopValue;
		private long elapsedTime;
		private boolean paused;

		/**
		 * Starts this chron.
		 * 
		 * @param snippetMarker
		 *            Name or simple marker of snippet to be profiled
		 */
		public void start(String snippetMarker) {
			this.startValue = System.currentTimeMillis();

			if (!paused) {
				System.out.println("----------------------");
				System.out.println("Timer started: "
						+ new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date(this.startValue)) + ": "
						+ snippetMarker);
				System.out.println("----------------------");
			}

			this.paused = false;
		}

		/**
		 * Pauses this chron.
		 */
		public void pause() {
			this.stopValue = System.currentTimeMillis();
			this.elapsedTime += (this.stopValue - this.startValue);
			this.paused = true;
		}

		/**
		 * Stops this chron and prints on console the elapsed time.
		 * 
		 * @param snippetMarker
		 *            Name or simple marker of snippet to be profiled
		 */
		public void stop(String snippetMarker) {
			if (!this.paused) {
				this.stopValue = System.currentTimeMillis();
				this.elapsedTime += this.stopValue - this.startValue;
			}

			System.out.println("----------------------");
			System.out.println("Timer ended: " + snippetMarker);
			System.out.println("----------------------");
			System.out.println("Total Time: " + this.elapsedTime + " miliseconds.");
			System.out.println("----------------------\n");

			this.reset();
		}

		/**
		 * Resets this Cron instance.
		 * 
		 * @return This Cron
		 */
		private Cron reset() {
			this.startValue = 0;
			this.stopValue = 0;
			this.elapsedTime = 0;
			this.paused = false;

			return this;
		}
	}

}