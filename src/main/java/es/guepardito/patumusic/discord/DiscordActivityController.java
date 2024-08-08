package es.guepardito.patumusic.discord;

import com.sun.jna.Library;
import com.sun.jna.Native;
import es.guepardito.patumusic.discord.jna.*;
import lombok.extern.slf4j.Slf4j;

import static es.guepardito.patumusic.discord.jna.Discord_game_sdkLibrary.*;
import static es.guepardito.patumusic.discord.utils.DiscordUtils.strToByteArr;

@Slf4j
public class DiscordActivityController extends Thread {
    private volatile boolean running = true;
    private volatile DiscordActivity activity;
    volatile IDiscordActivityManager activityManager;

    public interface DiscordLibrary extends Library {
        DiscordLibrary INSTANCE = (DiscordLibrary) Native.load(
                System.getProperty("user.dir") + "\\lib\\discord_game_sdk",
                DiscordLibrary.class
        );
    }

    public void delete() {
        this.running = false;
    }

    public void setDiscordActivity(String songName, String songAuthor, String songCoverName) {
        activity = new DiscordActivity();
        strToByteArr(activity.details, songName);
        strToByteArr(activity.state, songAuthor);
        strToByteArr(activity.assets.large_image, songCoverName);

        applyDiscordActivity();
    }
    public void setDiscordActivity(String songName, String songAuthor) {
        activity = new DiscordActivity();
        strToByteArr(activity.details, songName);
        strToByteArr(activity.state, songAuthor);
        applyDiscordActivity();
    }

    private void applyDiscordActivity() {
        activityManager.update_activity.apply(activityManager, activity, null, (callback_data, result1) -> log.debug("Update activity callback result is {}", result1));
    }

    @Override
    public void run() {
        DiscordLibrary lib = DiscordLibrary.INSTANCE;
        final long APPLICATION_ID = 1270736874230648978L;

        final DiscordCreateParams params = new DiscordCreateParams();
        params.client_id = APPLICATION_ID;
        params.application_version = DISCORD_APPLICATION_MANAGER_VERSION;
        params.user_version = DISCORD_USER_MANAGER_VERSION;
        params.image_version = DISCORD_IMAGE_MANAGER_VERSION;
        params.activity_version = DISCORD_ACTIVITY_MANAGER_VERSION;
        params.relationship_version = DISCORD_RELATIONSHIP_MANAGER_VERSION;
        params.lobby_version = DISCORD_LOBBY_MANAGER_VERSION;
        params.network_version = DISCORD_NETWORK_MANAGER_VERSION;
        params.overlay_version = DISCORD_OVERLAY_MANAGER_VERSION;
        params.storage_version = DISCORD_STORAGE_MANAGER_VERSION;
        params.store_version = DISCORD_STORE_MANAGER_VERSION;
        params.voice_version = DISCORD_VOICE_MANAGER_VERSION;
        params.achievement_version = DISCORD_ACHIEVEMENT_MANAGER_VERSION;

        final IDiscordCore.ByReference[] ptr = (IDiscordCore.ByReference[]) new IDiscordCore.ByReference().toArray(1);
        final int result = Discord_game_sdkLibrary.INSTANCE.DiscordCreate(DISCORD_VERSION, params, ptr);
        final IDiscordCore core = ptr[0];
        log.debug("Discord create output is {} and pointer {}", result, core.getPointer());

        activityManager = core.get_activity_manager.apply(core);
        setDiscordActivity("Cancion", "Prueba");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            core.destroy.apply(core);
        }));

        while (running) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            core.run_callbacks.apply(core);
        }
    }
}
