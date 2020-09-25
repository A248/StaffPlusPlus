package net.shortninja.staffplus.nms;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import net.minecraft.server.v1_16_R2.PacketPlayOutNamedSoundEffect;

import net.shortninja.staffplus.IStaffPlus;
import net.shortninja.staffplus.IocContainer;
import net.shortninja.staffplus.session.PlayerSession;
import net.shortninja.staffplus.server.compatibility.AbstractPacketHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public final class PacketHandler_v1_16 extends AbstractPacketHandler {

    public PacketHandler_v1_16(Player player) {
        super(player);
    }

    @Override
    public boolean onSend(ChannelHandlerContext context, Object o, ChannelPromise promise) throws Exception {
        if (o instanceof PacketPlayOutNamedSoundEffect) {
            RegisteredServiceProvider<IStaffPlus> provider = Bukkit.getServicesManager().getRegistration(IStaffPlus.class);
            if (provider != null) {
                PlayerSession playerSession = IocContainer.getSessionManager().get(player.getUniqueId());
                if (playerSession.isVanished()) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public boolean onReceive(ChannelHandlerContext context, Object o) throws Exception {
        return true;
    }
}
