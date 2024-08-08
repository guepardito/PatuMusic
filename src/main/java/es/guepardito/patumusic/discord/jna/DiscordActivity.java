package es.guepardito.patumusic.discord.jna;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;

public class DiscordActivity extends Structure {
	/**
	 * @see Discord_game_sdkLibrary.EDiscordActivityType <br>
	 * C type : EDiscordActivityType
	 */
	public int type;
	public long application_id;
	/** C type : char[128] */
	public byte[] name = new byte[128];
	/** C type : char[128] */
	public byte[] state = new byte[128];
	/** C type : char[128] */
	public byte[] details = new byte[128];
	/** C type : DiscordActivityTimestamps */
	public DiscordActivityTimestamps timestamps;
	/** C type : DiscordActivityAssets */
	public DiscordActivityAssets assets;
	/** C type : DiscordActivityParty */
	public DiscordActivityParty party;
	/** C type : DiscordActivitySecrets */
	public DiscordActivitySecrets secrets;
	public byte instance;
	public DiscordActivity() {
		super();
	}
	protected List<String > getFieldOrder() {
		return Arrays.asList("type", "application_id", "name", "state", "details", "timestamps", "assets", "party", "secrets", "instance");
	}
	public DiscordActivity(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends DiscordActivity implements Structure.ByReference {
		
	};
	public static class ByValue extends DiscordActivity implements Structure.ByValue {
		
	};
}
