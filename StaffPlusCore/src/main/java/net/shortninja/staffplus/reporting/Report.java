package net.shortninja.staffplus.reporting;

import net.shortninja.staffplus.event.ReportStatus;
import net.shortninja.staffplus.unordered.IReport;

import java.time.*;
import java.util.UUID;

public class Report implements IReport {
    private UUID culpritUuid;
    private String culpritName;
    private String reason;
    private String reporterName;
    private UUID reporterUuid;
    private String staffName;
    private UUID staffUuid;
    private ReportStatus reportStatus;
    private ZonedDateTime timestamp;
    private int id;

    public Report(UUID culpritUuid, String culpritName, int id, String reason, String reporterName, UUID reporterUuid, long time,
                  ReportStatus reportStatus,
                  String staffName,
                  UUID staffUuid) {
        this.culpritUuid = culpritUuid;
        this.culpritName = culpritName;
        this.reason = reason;
        this.reporterName = reporterName;
        this.reporterUuid = reporterUuid;
        this.id = id;
        this.timestamp = ZonedDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault());
        this.reportStatus = reportStatus;
        this.staffName = staffName;
        this.staffUuid = staffUuid;
    }

    public Report(UUID culpritUuid, String culpritName, String reason, String reporterName, UUID reporterUuid, ReportStatus reportStatus, ZonedDateTime timestamp) {
        this.culpritUuid = culpritUuid;
        this.culpritName = culpritName;
        this.reason = reason;
        this.reporterName = reporterName;
        this.reporterUuid = reporterUuid;
        this.reportStatus = reportStatus;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public UUID getUuid() {
        return culpritUuid;
    }

    public UUID getCulpritUuid() {
        return culpritUuid;
    }

    public String getCulpritName() {
        return culpritName;
    }

    public String getReason() {
        return reason;
    }

    public String getReporterName() {
        return reporterName;
    }

    /*
     * This is only required in order to keep report names up to date when the
     * reporter changes his or her name.
     */
    public void setReporterName(String reporterName) {
        this.reporterName = reporterName;
    }

    public UUID getReporterUuid() {
        return reporterUuid;
    }

    public ReportStatus getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(ReportStatus reportStatus) {
        this.reportStatus = reportStatus;
    }


    public UUID getStaffUuid() {
        return staffUuid;
    }

    public void setStaffUuid(UUID staffUuid) {
        this.staffUuid = staffUuid;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    @Override
    public ZonedDateTime getTimestamp() {
        return timestamp;
    }
}