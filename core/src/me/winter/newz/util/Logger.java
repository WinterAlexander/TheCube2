package me.winter.newz.util;

/**
 * A model for an object to log ouput.
 * Used by classes that should be reused amount multiple projects.
 *
 * Created by 1541869 on 2016-10-24.
 */
public interface Logger
{
	void debug(String message);
	void debug(String message, Exception ex);

	void info(String message);
	void info(String message, Exception ex);

	void warn(String message);
	void warn(String message, Exception ex);

	void error(String message);
	void error(String message, Exception ex);

	void setLogLevel(Level level);
	Level getLogLevel();

	public static enum Level
	{
		NONE, ERROR, WARNING, INFO, DEBUG;
	}
}
