package es.guepardito.patumusic.discord;

public class DiscordException extends Exception
{
	public DiscordException(final String message)
	{
		super(message);
	}

	public DiscordException(final String message, final DiscordResult result)
	{
		super(message + " - Discord result: " + result.name());
	}

	public DiscordException(final DiscordResult result)
	{
		super(result.name());
	}
}
