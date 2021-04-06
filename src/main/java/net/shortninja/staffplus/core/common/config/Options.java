package net.shortninja.staffplus.core.common.config;

import be.garagepoort.mcioc.IocBean;
import net.shortninja.staffplus.core.StaffPlus;
import net.shortninja.staffplus.core.authentication.AuthenticationConfiguration;
import net.shortninja.staffplus.core.authentication.AuthenticationConfigurationLoader;
import net.shortninja.staffplus.core.common.JavaUtils;
import net.shortninja.staffplus.core.common.utils.Materials;
import net.shortninja.staffplus.core.domain.chat.blacklist.BlackListConfiguration;
import net.shortninja.staffplus.core.domain.chat.blacklist.BlackListConfigurationLoader;
import net.shortninja.staffplus.core.domain.chat.configuration.ChatConfiguration;
import net.shortninja.staffplus.core.domain.chat.configuration.ChatModuleLoader;
import net.shortninja.staffplus.core.domain.staff.alerts.config.AlertsConfiguration;
import net.shortninja.staffplus.core.domain.staff.alerts.config.AlertsModuleLoader;
import net.shortninja.staffplus.core.domain.staff.altaccountdetect.config.AltDetectConfiguration;
import net.shortninja.staffplus.core.domain.staff.altaccountdetect.config.AltDetectModuleLoader;
import net.shortninja.staffplus.core.domain.staff.ban.config.BanConfiguration;
import net.shortninja.staffplus.core.domain.staff.ban.config.BanModuleLoader;
import net.shortninja.staffplus.core.domain.staff.broadcast.config.BroadcastConfiguration;
import net.shortninja.staffplus.core.domain.staff.broadcast.config.BroadcastConfigurationLoader;
import net.shortninja.staffplus.core.domain.staff.chests.config.EnderchestsConfiguration;
import net.shortninja.staffplus.core.domain.staff.chests.config.EnderchestsModuleLoader;
import net.shortninja.staffplus.core.domain.staff.examine.config.ExamineConfiguration;
import net.shortninja.staffplus.core.domain.staff.examine.config.ExamineModuleLoader;
import net.shortninja.staffplus.core.domain.staff.infractions.config.InfractionsConfiguration;
import net.shortninja.staffplus.core.domain.staff.infractions.config.InfractionsModuleLoader;
import net.shortninja.staffplus.core.domain.staff.investigate.config.InvestigationConfiguration;
import net.shortninja.staffplus.core.domain.staff.investigate.config.InvestigationModuleLoader;
import net.shortninja.staffplus.core.domain.staff.kick.config.KickConfiguration;
import net.shortninja.staffplus.core.domain.staff.kick.config.KickModuleLoader;
import net.shortninja.staffplus.core.domain.staff.mode.config.GeneralModeConfiguration;
import net.shortninja.staffplus.core.domain.staff.mode.config.StaffModeCustomModulesLoader;
import net.shortninja.staffplus.core.domain.staff.mode.config.StaffModeModuleLoader;
import net.shortninja.staffplus.core.domain.staff.mode.item.CustomModuleConfiguration;
import net.shortninja.staffplus.core.domain.staff.mute.config.MuteConfiguration;
import net.shortninja.staffplus.core.domain.staff.mute.config.MuteModuleLoader;
import net.shortninja.staffplus.core.domain.staff.protect.config.ProtectConfiguration;
import net.shortninja.staffplus.core.domain.staff.protect.config.ProtectModuleLoader;
import net.shortninja.staffplus.core.domain.staff.reporting.config.ManageReportConfiguration;
import net.shortninja.staffplus.core.domain.staff.reporting.config.ManageReportingModuleLoader;
import net.shortninja.staffplus.core.domain.staff.reporting.config.ReportConfiguration;
import net.shortninja.staffplus.core.domain.staff.reporting.config.ReportingModuleLoader;
import net.shortninja.staffplus.core.domain.staff.staffchat.config.StaffChatConfiguration;
import net.shortninja.staffplus.core.domain.staff.staffchat.config.StaffChatModuleLoader;
import net.shortninja.staffplus.core.domain.staff.teleport.config.LocationLoader;
import net.shortninja.staffplus.core.domain.staff.tracing.config.TraceConfiguration;
import net.shortninja.staffplus.core.domain.staff.tracing.config.TraceModuleLoader;
import net.shortninja.staffplus.core.domain.staff.warn.appeals.config.AppealConfiguration;
import net.shortninja.staffplus.core.domain.staff.warn.appeals.config.AppealModuleLoader;
import net.shortninja.staffplus.core.domain.staff.warn.warnings.config.ManageWarningsConfiguration;
import net.shortninja.staffplus.core.domain.staff.warn.warnings.config.ManageWarningsModuleLoader;
import net.shortninja.staffplus.core.domain.staff.warn.warnings.config.WarningConfiguration;
import net.shortninja.staffplus.core.domain.staff.warn.warnings.config.WarningModuleLoader;
import net.shortninja.staffplus.core.domain.synchronization.ServerSyncConfiguration;
import net.shortninja.staffplus.core.domain.synchronization.ServerSyncModuleLoader;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;
import java.util.Map;

//TODO: replace this with something that isn't horribly coupled...
@IocBean
public class Options {

    public List<String> blockedCommands;
    public List<String> blockedModeCommands;
    public String glassTitle;

    public String serverName;
    public String mainWorld;
    public String timestampFormat;
    public int autoSave;
    public long clock;
    private List<String> soundNames;
    public boolean offlinePlayersModeEnabled;

    public Map<String, Location> locations;
    public AuthenticationConfiguration authenticationConfiguration;
    public InfractionsConfiguration infractionsConfiguration;
    public InvestigationConfiguration investigationConfiguration;
    public ReportConfiguration reportConfiguration;
    public ManageReportConfiguration manageReportConfiguration;
    public ManageWarningsConfiguration manageWarningsConfiguration;
    public WarningConfiguration warningConfiguration;
    public AppealConfiguration appealConfiguration;
    public BlackListConfiguration blackListConfiguration;
    public TraceConfiguration traceConfiguration;
    public BroadcastConfiguration broadcastConfiguration;
    public ProtectConfiguration protectConfiguration;
    public BanConfiguration banConfiguration;
    public KickConfiguration kickConfiguration;
    public MuteConfiguration muteConfiguration;
    public AltDetectConfiguration altDetectConfiguration;
    public StaffChatConfiguration staffChatConfiguration;
    public ExamineConfiguration examineConfiguration;
    public EnderchestsConfiguration enderchestsConfiguration;
    public GeneralModeConfiguration modeConfiguration;
    public ServerSyncConfiguration serverSyncConfiguration;
    public AlertsConfiguration alertsConfiguration;
    public ChatConfiguration chatConfiguration;

    /*
     * Vanish
     */
    public boolean vanishEnabled;
    public boolean vanishTabList;
    public boolean vanishShowAway;
    public boolean vanishChatEnabled;
    public boolean vanishMessageEnabled;

    /*
     * Staff Mode
     */
    public boolean staffView;

    /*
     * Custom
     */
    public List<CustomModuleConfiguration> customModuleConfigurations;
    /*
     * Permissions
     */
    public String permissionWildcard;
    public String permissionBlock;
    public String permissionReport;
    public String permissionReportBypass;
    public String permissionReportUpdateNotifications;
    public String permissionWarn;
    public String permissionWarnBypass;
    public String permissionVanishCommand;
    public String permissionVanishTotal;
    public String permissionVanishList;
    public String permissionChatClear;
    public String permissionChatToggle;
    public String permissionChatSlow;
    public String permissionBlacklist;
    public String permissionMode;
    public String permissionModeSilentChestInteraction;
    public String permissionFreeze;
    public String permissionFreezeBypass;
    public String permissionTeleportToLocation;
    public String permissionTeleportToPlayer;
    public String permissionTeleportHere;
    public String permissionTeleportBypass;
    public String permissionTrace;
    public String permissionTraceBypass;
    public String permissionCps;
    public String permissionFollow;
    public String permissionRevive;
    public String permissionMember;
    public String ipHidePerm;
    public String permissionClearInv;
    public String permissionClearInvBypass;
    public String permissionBroadcast;
    public String permissionCounterGuiShowVanish;

    /*
     * Commands
     */
    public String commandStaffMode;
    public String commandStaffFly;
    public String commandFreeze;
    public String commandTeleportToLocation;
    public String commandTeleportBack;
    public String commandTeleportToPlayer;
    public String commandTeleportHere;
    public String commandCps;
    public String commandStaffChat;
    public String commandReport;
    public String commandReportPlayer;
    public String commandReports;
    public String commandFindReports;
    public String commandWarn;
    public String commandWarns;
    public String commandVanish;
    public String commandChat;
    public String commandFollow;
    public String commandRevive;
    public String commandStaffList;
    public String commandClearInv;
    public String commandTrace;
    public String commandBroadcast;

    public String permissionStrip;
    public String permissionStaff;
    public String commandNotes;
    public String commandLogin;
    public String commandStrip;

    /*
     * Storage
     */
    public String storageType;
    public String mySqlHost;
    public String mySqlUser;
    public String database;
    public String mySqlPassword;
    public int mySqlPort;

    private final AuthenticationConfigurationLoader authenticationConfigurationLoader;
    private final InfractionsModuleLoader infractionsModuleLoader;
    private final ReportingModuleLoader reportingModuleLoader;
    private final ManageReportingModuleLoader manageReportingModuleLoader;
    private final WarningModuleLoader warningModuleLoader;
    private final AppealModuleLoader appealModuleLoader;
    private final ManageWarningsModuleLoader manageWarningsModuleLoader;
    private final BlackListConfigurationLoader blackListConfigurationLoader;
    private final TraceModuleLoader traceModuleLoader;
    private final BroadcastConfigurationLoader broadcastConfigurationLoader;
    private final ProtectModuleLoader protectModuleLoader;
    private final BanModuleLoader banModuleLoader;
    private final KickModuleLoader kickModuleLoader;
    private final MuteModuleLoader muteModuleLoader;
    private final AltDetectModuleLoader altDetectModuleLoader;
    private final StaffChatModuleLoader staffChatModuleLoader;
    private final ExamineModuleLoader examineModuleLoader;
    private final EnderchestsModuleLoader enderchestsModuleLoader;
    private final StaffModeModuleLoader staffModeModuleLoader;
    private final ServerSyncModuleLoader serverSyncModuleLoader;
    private final AlertsModuleLoader alertsModuleLoader;
    private final ChatModuleLoader chatModuleLoader;
    private final InvestigationModuleLoader investigationModuleLoader;
    private final StaffModeCustomModulesLoader staffModeCustomModulesLoader;

    public Options(AuthenticationConfigurationLoader authenticationConfigurationLoader,
                   InfractionsModuleLoader infractionsModuleLoader,
                   ReportingModuleLoader reportingModuleLoader,
                   ManageReportingModuleLoader manageReportingModuleLoader,
                   WarningModuleLoader warningModuleLoader,
                   AppealModuleLoader appealModuleLoader,
                   ManageWarningsModuleLoader manageWarningsModuleLoader,
                   BlackListConfigurationLoader blackListConfigurationLoader,
                   TraceModuleLoader traceModuleLoader,
                   BroadcastConfigurationLoader broadcastConfigurationLoader,
                   ProtectModuleLoader protectModuleLoader,
                   BanModuleLoader banModuleLoader,
                   KickModuleLoader kickModuleLoader,
                   MuteModuleLoader muteModuleLoader,
                   AltDetectModuleLoader altDetectModuleLoader,
                   StaffChatModuleLoader staffChatModuleLoader,
                   ExamineModuleLoader examineModuleLoader,
                   EnderchestsModuleLoader enderchestsModuleLoader,
                   StaffModeModuleLoader staffModeModuleLoader,
                   ServerSyncModuleLoader serverSyncModuleLoader,
                   AlertsModuleLoader alertsModuleLoader,
                   ChatModuleLoader chatModuleLoader,
                   InvestigationModuleLoader investigationModuleLoader,
                   StaffModeCustomModulesLoader staffModeCustomModulesLoader) {
        this.authenticationConfigurationLoader = authenticationConfigurationLoader;
        this.infractionsModuleLoader = infractionsModuleLoader;
        this.reportingModuleLoader = reportingModuleLoader;
        this.manageReportingModuleLoader = manageReportingModuleLoader;
        this.warningModuleLoader = warningModuleLoader;
        this.appealModuleLoader = appealModuleLoader;
        this.manageWarningsModuleLoader = manageWarningsModuleLoader;
        this.blackListConfigurationLoader = blackListConfigurationLoader;
        this.traceModuleLoader = traceModuleLoader;
        this.broadcastConfigurationLoader = broadcastConfigurationLoader;
        this.protectModuleLoader = protectModuleLoader;
        this.banModuleLoader = banModuleLoader;
        this.kickModuleLoader = kickModuleLoader;
        this.muteModuleLoader = muteModuleLoader;
        this.altDetectModuleLoader = altDetectModuleLoader;
        this.staffChatModuleLoader = staffChatModuleLoader;
        this.examineModuleLoader = examineModuleLoader;
        this.enderchestsModuleLoader = enderchestsModuleLoader;
        this.staffModeModuleLoader = staffModeModuleLoader;
        this.serverSyncModuleLoader = serverSyncModuleLoader;
        this.alertsModuleLoader = alertsModuleLoader;
        this.chatModuleLoader = chatModuleLoader;
        this.investigationModuleLoader = investigationModuleLoader;
        this.staffModeCustomModulesLoader = staffModeCustomModulesLoader;
        reload();
    }

    public void reload() {
        FileConfiguration defaultConfig = StaffPlus.get().getFileConfigurations().get("config");

        blockedCommands = JavaUtils.stringToList(defaultConfig.getString("blocked-commands", ""));
        blockedModeCommands = JavaUtils.stringToList(defaultConfig.getString("blocked-mode-commands", ""));
        glassTitle = defaultConfig.getString("glass-title");

        serverName = defaultConfig.getString("server-name");
        mainWorld = defaultConfig.getString("main-world");
        timestampFormat = defaultConfig.getString("timestamp-format");
        autoSave = defaultConfig.getInt("auto-save");
        clock = defaultConfig.getInt("clock") * 20;
        soundNames = JavaUtils.stringToList(defaultConfig.getString("sound-names"));
        offlinePlayersModeEnabled = defaultConfig.getBoolean("offline-players-mode");

        locations = new LocationLoader().loadConfig();
        authenticationConfiguration = this.authenticationConfigurationLoader.loadConfig();
        infractionsConfiguration = this.infractionsModuleLoader.loadConfig();
        reportConfiguration = this.reportingModuleLoader.loadConfig();
        manageReportConfiguration = this.manageReportingModuleLoader.loadConfig();
        warningConfiguration = this.warningModuleLoader.loadConfig();
        appealConfiguration = this.appealModuleLoader.loadConfig();
        manageWarningsConfiguration = this.manageWarningsModuleLoader.loadConfig();
        blackListConfiguration = this.blackListConfigurationLoader.loadConfig();
        traceConfiguration = this.traceModuleLoader.loadConfig();
        broadcastConfiguration = this.broadcastConfigurationLoader.loadConfig();
        protectConfiguration = this.protectModuleLoader.loadConfig();
        banConfiguration = this.banModuleLoader.loadConfig();
        kickConfiguration = this.kickModuleLoader.loadConfig();
        muteConfiguration = this.muteModuleLoader.loadConfig();
        altDetectConfiguration = this.altDetectModuleLoader.loadConfig();
        staffChatConfiguration = this.staffChatModuleLoader.loadConfig();
        examineConfiguration = this.examineModuleLoader.loadConfig();
        enderchestsConfiguration = this.enderchestsModuleLoader.loadConfig();
        modeConfiguration = this.staffModeModuleLoader.loadConfig();
        serverSyncConfiguration = this.serverSyncModuleLoader.loadConfig();
        alertsConfiguration = this.alertsModuleLoader.loadConfig();
        chatConfiguration = this.chatModuleLoader.loadConfig();
        investigationConfiguration = this.investigationModuleLoader.loadConfig();
        customModuleConfigurations = this.staffModeCustomModulesLoader.loadConfig();

        /*
         * Vanish
         */
        vanishEnabled = defaultConfig.getBoolean("vanish-module.enabled");
        vanishTabList = defaultConfig.getBoolean("vanish-module.tab-list");
        vanishShowAway = defaultConfig.getBoolean("vanish-module.show-away");
        vanishChatEnabled = defaultConfig.getBoolean("vanish-module.chat");
        vanishMessageEnabled = defaultConfig.getBoolean("vanish-module.vanish-message-enabled");

        /*
         * Staff Mode
         */
        staffView = defaultConfig.getBoolean("staff-mode.staff-see-staff-in-mode");

        /*
         * Permissions
         */
        permissionStaff = defaultConfig.getString("permissions.staffplus");
        permissionStrip = defaultConfig.getString("permissions.strip");
        permissionWildcard = defaultConfig.getString("permissions.wild-card");
        permissionBlock = defaultConfig.getString("permissions.block");
        permissionReport = defaultConfig.getString("permissions.report");
        permissionReportBypass = defaultConfig.getString("permissions.report-bypass");
        permissionReportUpdateNotifications = defaultConfig.getString("permissions.report-update-notifications");
        permissionWarn = defaultConfig.getString("permissions.warn");
        permissionWarnBypass = defaultConfig.getString("permissions.warn-bypass");
        permissionVanishCommand = defaultConfig.getString("permissions.vanish");
        permissionVanishTotal = defaultConfig.getString("permissions.vanish-total");
        permissionVanishList = defaultConfig.getString("permissions.vanish-list");
        permissionChatClear = defaultConfig.getString("permissions.chat-clear");
        permissionChatToggle = defaultConfig.getString("permissions.chat-toggle");
        permissionChatSlow = defaultConfig.getString("permissions.chat-slow");
        permissionBlacklist = defaultConfig.getString("permissions.blacklist");
        permissionMode = defaultConfig.getString("permissions.mode");
        permissionModeSilentChestInteraction = defaultConfig.getString("permissions.mode-silent-chest-interaction");
        permissionFreeze = defaultConfig.getString("permissions.freeze");
        permissionFreezeBypass = defaultConfig.getString("permissions.freeze-bypass");
        permissionTeleportToLocation = defaultConfig.getString("permissions.teleport-to-location");
        permissionTeleportToPlayer = defaultConfig.getString("permissions.teleport-to-player");
        permissionTeleportHere = defaultConfig.getString("permissions.teleport-here");
        permissionTeleportBypass = defaultConfig.getString("permissions.teleport-bypass");
        permissionTrace = defaultConfig.getString("permissions.trace");
        permissionTraceBypass = defaultConfig.getString("permissions.trace-bypass");
        permissionCps = defaultConfig.getString("permissions.cps");
        permissionFollow = defaultConfig.getString("permissions.follow");
        permissionRevive = defaultConfig.getString("permissions.revive");
        permissionMember = defaultConfig.getString("permissions.member");
        ipHidePerm = defaultConfig.getString("permissions.ipPerm");
        permissionClearInv = defaultConfig.getString("permissions.invClear");
        permissionClearInvBypass = defaultConfig.getString("permissions.invClear-bypass");
        permissionBroadcast = defaultConfig.getString("permissions.broadcast");
        permissionCounterGuiShowVanish = defaultConfig.getString("permissions.counter-show-vanished");

        /*
         * Commands
         */
        commandStaffMode = defaultConfig.getString("commands.staff-mode");
        commandStaffFly = defaultConfig.getString("commands.staff-mode-fly");
        commandFreeze = defaultConfig.getString("commands.freeze");
        commandTeleportToLocation = defaultConfig.getString("commands.teleport-to-location");
        commandTeleportBack = defaultConfig.getString("commands.teleport-back");
        commandTeleportToPlayer = defaultConfig.getString("commands.teleport-to-player");
        commandTeleportHere = defaultConfig.getString("commands.teleport-here");
        commandCps = defaultConfig.getString("commands.cps");
        commandStaffChat = defaultConfig.getString("commands.staff-chat");
        commandReport = defaultConfig.getString("commands.report");
        commandReportPlayer = defaultConfig.getString("commands.reportPlayer");
        commandReports = defaultConfig.getString("commands.reports.manage.cli");
        commandFindReports = defaultConfig.getString("commands.reports.manage.gui-find-reports");
        commandWarn = defaultConfig.getString("commands.warn");
        commandWarns = defaultConfig.getString("commands.warns");
        commandVanish = defaultConfig.getString("commands.vanish");
        commandChat = defaultConfig.getString("commands.chat");
        commandFollow = defaultConfig.getString("commands.follow");
        commandRevive = defaultConfig.getString("commands.revive");
        commandStaffList = defaultConfig.getString("commands.staff-list");
        commandClearInv = defaultConfig.getString("commands.clearInv");
        commandTrace = defaultConfig.getString("commands.trace");
        commandBroadcast = defaultConfig.getString("commands.broadcast");
        commandNotes = defaultConfig.getString("commands.notes");
        commandLogin = defaultConfig.getString("commands.login");
        commandStrip = defaultConfig.getString("commands.strip");

        /*
         * Storage
         */
        storageType = defaultConfig.getString("storage.type");
        mySqlHost = defaultConfig.getString("storage.mysql.host");
        mySqlUser = defaultConfig.getString("storage.mysql.user");
        database = defaultConfig.getString("storage.mysql.database");
        mySqlPassword = defaultConfig.getString("storage.mysql.password");
        mySqlPort = defaultConfig.getInt("storage.mysql.port");
    }


    public static String getMaterial(String current) {
        switch (current) {
            case "HEAD":
                return Materials.valueOf("HEAD").getName();
            case "SPAWNER":
                return Materials.valueOf("SPAWNER").getName();
            case "ENDEREYE":
                return Materials.valueOf("ENDEREYE").getName();
            case "CLOCK":
                return Materials.valueOf("CLOCK").getName();
            case "LEAD":
                return Materials.valueOf("LEAD").getName();
            case "INK":
                return Materials.valueOf("INK").getName();
            default:
                return current;

        }

    }

    public static Material stringToMaterial(String string) {
        Material sound = Material.STONE;

        boolean isValid = JavaUtils.isValidEnum(Material.class, getMaterial(string));
        if (!isValid) {
            Bukkit.getLogger().severe("Invalid material type '" + string + "'!");
        } else
            sound = Material.valueOf(getMaterial(string));

        return sound;
    }
}