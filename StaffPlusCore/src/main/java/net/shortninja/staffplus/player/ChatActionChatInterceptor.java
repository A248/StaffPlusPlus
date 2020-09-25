package net.shortninja.staffplus.player;

import net.shortninja.staffplus.server.chat.ChatAction;
import net.shortninja.staffplus.server.chat.ChatInterceptor;
import net.shortninja.staffplus.session.PlayerSession;
import net.shortninja.staffplus.session.SessionManager;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import static net.shortninja.staffplus.common.cmd.CommandUtil.playerAction;

public class ChatActionChatInterceptor implements ChatInterceptor {
    private final SessionManager sessionManager;

    public ChatActionChatInterceptor(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }


    @Override
    public boolean intercept(AsyncPlayerChatEvent event) {
        PlayerSession session = sessionManager.get(event.getPlayer().getUniqueId());
        ChatAction chatAction = session.getChatAction();

        if (chatAction != null) {
            playerAction(event.getPlayer(), () -> {
                chatAction.execute(event.getPlayer(), event.getMessage());
                session.setChatAction(null);
            });
            return true;
        }
        return false;
    }
}
