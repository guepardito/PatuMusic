package es.guepardito.patumusic.discord.extensions;

import es.guepardito.patumusic.discord.DiscordResult;
import java.util.concurrent.CompletableFuture;

import es.guepardito.patumusic.discord.jna.DiscordUser;
import es.guepardito.patumusic.discord.jna.IDiscordUserManager;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DiscordUserManagerExtensions
{
	public static DiscordUser getCurrentUser(final IDiscordUserManager userManager)
	{
		final DiscordUser discordUser = new DiscordUser();
		userManager.get_current_user.apply(userManager, discordUser);
		return discordUser;
	}

	public static CompletableFuture<DiscordUser> getUser(final IDiscordUserManager userManager, final long userId)
	{
		final CompletableFuture<DiscordUser> future = new CompletableFuture<>();
		userManager.get_user.apply(userManager, userId, null, (callback_data, result, user) -> DiscordResult.of(result).completeFuture(future, user));
		return future;
	}
}
