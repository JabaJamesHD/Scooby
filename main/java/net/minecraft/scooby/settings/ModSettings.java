package net.minecraft.scooby.settings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

import net.minecraft.client.Minecraft;
import net.minecraft.scooby.Scooby;
import net.minecraft.scooby.mode.Mode;

import org.lwjgl.input.Keyboard;

public class ModSettings {

	private final File settingsDirectory = new File(Minecraft.getMinecraft().mcDataDir, Scooby.MOD_NAME.toLowerCase()), keyBindsFile = new File(settingsDirectory, "key_binds.cfg");
	private final Scooby scooby;
	public ModSettings(Scooby scooby) {
		this.scooby = scooby;
	}
	public void loadKeyBinds() {
		if (!keyBindsFile.exists()) {
			return;
		}
		for (Mode mode : scooby.modeHandler.getModes()) {
			mode.setToggleKey(-1);
		}
		try {
			BufferedReader reader = new BufferedReader(new FileReader(keyBindsFile));
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.startsWith("#")) {
					continue;
				}
				String[] keyBindSetting = line.split(":");
				try {
					for (Mode mode : scooby.modeHandler.getModes()) {
						if (mode.getConfigName().equals(keyBindSetting[0])) {
							int toggleKey = Keyboard.getKeyIndex(keyBindSetting[1]);
							mode.setToggleKey(toggleKey != Keyboard.KEY_NONE ? toggleKey : -1);
							break;
						}
					}
				}
				catch (Exception e) {
					//e.printStackTrace();
				}
			}
			reader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	public void saveKeyBinds() {
		try {
			settingsDirectory.mkdir();
			PrintWriter writer = new PrintWriter(new FileWriter(keyBindsFile));
			writer.println("# This file contains the key binds");
			for (Mode mode : scooby.modeHandler.getModes()) {
				int toggleKey = mode.getToggleKey();
				String keyName = Keyboard.getKeyName(toggleKey != -1 ? toggleKey : Keyboard.KEY_NONE);
				writer.println(mode.getConfigName() + ":" + keyName);
			}
			writer.close();
		}
		catch (Exception e) {
			//e.printStackTrace();
		}
	}
}
