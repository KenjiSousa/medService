package org.example.medService.infrastructure;

import static org.junit.platform.commons.util.StringUtils.isBlank;
import io.github.cdimascio.dotenv.Dotenv;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionFactory {
    private static String resourceName;

    public Connection getConnection() throws NamingException, SQLException {
        return getDatasource().getConnection();
    }

    private DataSource getDatasource() throws NamingException {
        if (isBlank(resourceName)) {
            try {
                resourceName = Dotenv.configure().directory(System.getProperty("user.home") + "/.medService").load().get("RESOURCE_NAME");
            } catch (Exception ignored) {} //DotEnvException não faz parte da assinatura da função, mas é levantado caso o arquivo .env não exista

            resourceName = isBlank(resourceName) ? "PostgresDS" : resourceName;
        }

        return (DataSource) new InitialContext().lookup(resourceName);
    }
}
