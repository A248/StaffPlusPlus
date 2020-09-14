package net.shortninja.staffplus;

import net.shortninja.staffplus.player.UserManager;
import net.shortninja.staffplus.reporting.ReportService;
import net.shortninja.staffplus.reporting.database.MysqlReportRepository;
import net.shortninja.staffplus.reporting.database.ReportRepository;
import net.shortninja.staffplus.reporting.database.SqliteReportRepository;
import net.shortninja.staffplus.server.data.config.Messages;
import net.shortninja.staffplus.server.data.config.Options;
import net.shortninja.staffplus.server.data.storage.IStorage;
import net.shortninja.staffplus.server.data.storage.MemoryStorage;
import net.shortninja.staffplus.server.data.storage.MySQLStorage;
import net.shortninja.staffplus.server.data.storage.SqliteStorage;
import net.shortninja.staffplus.util.database.DatabaseType;
import net.shortninja.staffplus.util.database.DatabaseUtil;

public class IocContainer {

    private static ReportService reportService;
    private static ReportRepository reportRepository;
    private static UserManager userManager;
    private static StaffPlus staffPlus;
    private static Messages messages;
    private static Options options;
    private static IStorage storage;

    public static void init(StaffPlus staffPlus) {
        IocContainer.staffPlus = staffPlus;
    }

    public static ReportRepository getReportRepository() {
        if (reportRepository == null) {
            if (DatabaseUtil.database().getType() == DatabaseType.MYSQL) {
                reportRepository = new MysqlReportRepository();
            } else if (DatabaseUtil.database().getType() == DatabaseType.SQLITE) {
                reportRepository = new SqliteReportRepository();
            } else {
                throw new RuntimeException("Unsupported database type");
            }
        }
        return reportRepository;
    }

    public static ReportService getReportService() {
        if (reportService == null) {
            reportService = new ReportService(getReportRepository(), getUserManager(), getMessages());
        }
        return reportService;
    }

    public static IStorage getStorage() {
        if (storage == null) {
            if (DatabaseUtil.database().getType() == DatabaseType.MYSQL) {
                storage = new MySQLStorage();
            } else if (DatabaseUtil.database().getType() == DatabaseType.SQLITE) {
                storage = new SqliteStorage();
            } else {
                storage = new MemoryStorage();
            }
        }
        return storage;
    }

    public static UserManager getUserManager() {
        if (userManager == null) {
            userManager = new UserManager(staffPlus);
        }
        return userManager;
    }

    public static Messages getMessages() {
        if (messages == null) {
            messages = new Messages();
        }
        return messages;
    }
}
