package me.winter.newz.util;

/**
 *
 * Created by 1541869 on 2016-10-24.
 */
public class JLogger implements Logger
{
	private java.util.logging.Logger jLogger;

	public JLogger(String name)
	{
		jLogger = java.util.logging.Logger.getLogger(name);
	}

	public JLogger(String name, String resourceBundleName)
	{
		jLogger = java.util.logging.Logger.getLogger(name, resourceBundleName);
	}

	@Override
	public void debug(String message)
	{
		jLogger.log(java.util.logging.Level.FINE, message);
	}

	@Override
	public void debug(String message, Exception ex)
	{
		jLogger.log(java.util.logging.Level.FINE, message, ex);
	}

	@Override
	public void info(String message)
	{
		jLogger.log(java.util.logging.Level.INFO, message);
	}

	@Override
	public void info(String message, Exception ex)
	{
		jLogger.log(java.util.logging.Level.INFO, message, ex);
	}

	@Override
	public void warn(String message)
	{
		jLogger.log(java.util.logging.Level.WARNING, message);
	}

	@Override
	public void warn(String message, Exception ex)
	{
		jLogger.log(java.util.logging.Level.WARNING, message, ex);
	}

	@Override
	public void error(String message)
	{
		jLogger.log(java.util.logging.Level.SEVERE, message);
	}

	@Override
	public void error(String message, Exception ex)
	{
		jLogger.log(java.util.logging.Level.SEVERE, message, ex);
	}

	@Override
	public void setLogLevel(Level level)
	{
		switch(level)
		{
			case DEBUG:
				jLogger.setLevel(java.util.logging.Level.FINE);
				break;

			case INFO:
				jLogger.setLevel(java.util.logging.Level.INFO);
				break;

			case WARNING:
				jLogger.setLevel(java.util.logging.Level.WARNING);
				break;

			case ERROR:
				jLogger.setLevel(java.util.logging.Level.SEVERE);
				break;

			case NONE:
				jLogger.setLevel(java.util.logging.Level.OFF);
				break;
		}
	}

	@Override
	public Level getLogLevel()
	{
		switch(jLogger.getLevel().intValue())
		{
			case Integer.MIN_VALUE:
			case 300:
			case 400:
			case 500:
				return Level.DEBUG;

			case 700:
			case 800:
				return Level.INFO;

			case 900: //warning
				return Level.WARNING;

			case 1000:
				return Level.ERROR;

			default:
				return Level.NONE;
		}
	}

	public java.util.logging.Logger getInternalLogger()
	{
		return jLogger;
	}
}
