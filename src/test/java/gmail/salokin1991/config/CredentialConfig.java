package gmail.salokin1991.config;

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:config/credentials.properties"})
public interface CredentialConfig extends Config {
    String login();
    String password();
}
