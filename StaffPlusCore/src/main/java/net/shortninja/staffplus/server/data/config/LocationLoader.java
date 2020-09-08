package net.shortninja.staffplus.server.data.config;

import net.shortninja.staffplus.StaffPlus;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Double.parseDouble;

public class LocationLoader {
    private static FileConfiguration config = StaffPlus.get().getConfig();

    public static Map<String, Location> loadLocations() {
        ConfigurationSection locationsConfig = config.getConfigurationSection("locations");
        if (locationsConfig == null) {
            return Collections.emptyMap();
        }

        Map<String, Location> locations = new HashMap<>();
        for (String identifier : locationsConfig.getKeys(false)) {
            String locationString = config.getString("locations." + identifier);
            String[] points = locationString.split(";");
            if (points.length != 3) {
                throw new RuntimeException("Invalid locations configuration. Make sure your location points are in format x;y;z");
            }

            Location location = new Location(null, parseDouble(points[0]), parseDouble(points[1]), parseDouble(points[2]));
            locations.put(identifier, location);
        }
        return locations;
    }
}
