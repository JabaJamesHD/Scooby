package net.minecraft.scooby.mode.modes;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.potion.Potion;
import net.minecraft.scooby.Scooby;
import net.minecraft.scooby.mode.Mode;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * Sprint is a mod that will automatically sprint for the player.  It is currently using the existing keycode for sprint,
 * though this works differently than Minecraft's keybind.  If this is enabled, this will always sprint (if you meet
 * a certain criteria).  With Minecraft's, it will sprint once, and then once you stop sprinting you have to press
 * the button again.
 *
 * @author b
 * @since 1:29 PM on 3/16/2015
 */
public class SprintMode extends Mode {

	public SprintMode(Scooby scooby) {
		super(scooby, "Sprint", scooby.mc.gameSettings.keyBindSprint.getKeyCode());
	}

	@Override
	public void onEvent(Event event) {
		// TODO Auto-generated method stub
		if (event instanceof LivingUpdateEvent && ((LivingUpdateEvent) event).entity.equals(scooby.mc.thePlayer) && shouldSprint(scooby.mc.thePlayer)) {
			scooby.mc.thePlayer.setSprinting(true);
		}
	}

	/**
	 * Checks if the player should sprint.  Basic checks so that you won't be sprinting while standing still,
	 * sneaking, using items (eating), etc.
	 *
	 * @param player	The target player for sprint checking.
	 * @return			<code>true</code> if the player can sprint, else <code>false</code>.
	 */
	private boolean shouldSprint(EntityPlayerSP player) {
		// wtf brudin why cant u check dis shit properly :'C
		return player.onGround && !player.movementInput.sneak && player.movementInput.moveForward >= 0.8F && !player.isSprinting() && (player.getFoodStats().getFoodLevel() > 6.0F || player.capabilities.allowFlying) && !player.isUsingItem() && !player.isPotionActive(Potion.blindness);
	}

}
