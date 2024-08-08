package es.guepardito.patumusic.discord.extensions;

import es.guepardito.patumusic.discord.jna.*;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DiscordCoreExtensions
{
	public static IDiscordApplicationManager getApplicationManager(final IDiscordCore core)
	{
		return core.get_application_manager.apply(core);
	}

	public static IDiscordUserManager getUserManager(final IDiscordCore core)
	{
		return core.get_user_manager.apply(core);
	}

	public static IDiscordImageManager getImageManager(final IDiscordCore core)
	{
		return core.get_image_manager.apply(core);
	}

	public static IDiscordActivityManager getActivityManager(final IDiscordCore core)
	{
		return core.get_activity_manager.apply(core);
	}

	public static IDiscordRelationshipManager getRelationshipManager(final IDiscordCore core)
	{
		return core.get_relationship_manager.apply(core);
	}

	public static IDiscordLobbyManager getLobbyManager(final IDiscordCore core)
	{
		return core.get_lobby_manager.apply(core);
	}

	public static IDiscordNetworkManager getNetworkManager(final IDiscordCore core)
	{
		return core.get_network_manager.apply(core);
	}

	public static IDiscordOverlayManager getOverlayManager(final IDiscordCore core)
	{
		return core.get_overlay_manager.apply(core);
	}

	public static IDiscordStorageManager getStorageManager(final IDiscordCore core)
	{
		return core.get_storage_manager.apply(core);
	}

	public static IDiscordStoreManager getStoreManager(final IDiscordCore core)
	{
		return core.get_store_manager.apply(core);
	}

	public static IDiscordVoiceManager getVoiceManager(final IDiscordCore core)
	{
		return core.get_voice_manager.apply(core);
	}

	public static IDiscordAchievementManager getAchievementManager(final IDiscordCore core)
	{
		return core.get_achievement_manager.apply(core);
	}
}
