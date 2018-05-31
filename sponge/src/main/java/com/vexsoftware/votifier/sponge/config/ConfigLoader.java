package com.vexsoftware.votifier.sponge.config;

import com.google.common.reflect.TypeToken;
import com.vexsoftware.votifier.sponge.VotifierPlugin;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.yaml.YAMLConfigurationLoader;

import java.io.File;

public class ConfigLoader {

    private final VotifierPlugin plugin;
    private SpongeConfig spongeConfig;

    public ConfigLoader(VotifierPlugin pl) {
        this.plugin = pl;
        if (!plugin.getConfigDir().exists()) {
            plugin.getConfigDir().mkdirs();
        }
    }

    public boolean loadConfig() {
        try {
            File file = new File(plugin.getConfigDir(), "config.yml");
            if (!file.exists()) {
                file.createNewFile();
            }
            ConfigurationLoader loader = YAMLConfigurationLoader.builder().setFile(file).build();
            ConfigurationNode config = loader.load(ConfigurationOptions.defaults().setShouldCopyDefaults(true));
            spongeConfig = config.getValue(TypeToken.of(SpongeConfig.class), new SpongeConfig());
            loader.save(config);
            return true;
        } catch (Exception e) {
            plugin.getLogger().error("Could not load config.", e);
            return false;
        }
    }

    public SpongeConfig getSpongeConfig() {
        return spongeConfig;
    }
}
