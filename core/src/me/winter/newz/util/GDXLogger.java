package me.winter.newz.util;

/**
 *
 * Created by 1541869 on 2016-10-24.
 */
public class GDXLogger extends com.badlogic.gdx.utils.Logger implements Logger
{
	public GDXLogger(String tag)
	{
		super(tag);
	}

	public GDXLogger(String tag, int level)
	{
		super(tag, level);
	}

	@Override
	public void warn(String message)
	{
		super.info(message);
	}

	@Override
	public void warn(String message, Exception ex)
	{
		super.info(message, ex);
	}

	@Override
	public void error(String message, Exception ex)
	{
		super.error(message, ex);
	}

	@Override
	public void setLogLevel(Level level)
	{
		super.setLevel(level.ordinal() < 3 ? level.ordinal() : level.ordinal() - 1);
	}

	@Override
	public Level getLogLevel()
	{
		return Level.values()[super.getLevel() < 3 ? super.getLevel() : super.getLevel() + 1];
	}
}
