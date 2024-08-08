package es.guepardito.patumusic.discord.extensions;

import es.guepardito.patumusic.discord.jna.DiscordUser;
import static es.guepardito.patumusic.discord.utils.DiscordUtils.byteArrToStr;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DiscordUserExtensions
{
	public static long getId(final DiscordUser discordUser)
	{
		return discordUser.id;
	}

	public static String getUsername(final DiscordUser discordUser)
	{
		return byteArrToStr(discordUser.username);
	}

	public static String getDiscriminator(final DiscordUser discordUser)
	{
		return byteArrToStr(discordUser.discriminator);
	}

	public static String getAvatar(final DiscordUser discordUser)
	{
		return byteArrToStr(discordUser.avatar);
	}

	public static String getDisplayName(final DiscordUser discordUser)
	{
		return getUsername(discordUser) + "#" + getDiscriminator(discordUser);
	}
}
