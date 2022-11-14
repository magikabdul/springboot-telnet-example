package cloud.cholewa.telnet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.ip.tcp.TcpInboundGateway;
import org.springframework.integration.ip.tcp.connection.TcpNetServerConnectionFactory;
import org.springframework.integration.transformer.ObjectToStringTransformer;
import org.springframework.messaging.MessageChannel;

@Configuration
@IntegrationComponentScan
@EnableIntegration
class TelnetConfiguration {

    @Bean
    public TcpNetServerConnectionFactory createTcpNetServerConnectionFactory() {
        return new TcpNetServerConnectionFactory(21000);
    }

    @Bean
    public TcpInboundGateway createTcpInboundGateway() {
        TcpInboundGateway gateway = new TcpInboundGateway();
        gateway.setConnectionFactory(createTcpNetServerConnectionFactory());
        gateway.setRequestChannel(serverBytes2StringChannel());
        //gateway.setErrorChannel();
        gateway.setLoggingEnabled(true);
        gateway.setShouldTrack(true);

        return gateway;
    }

    @Bean
    public MessageChannel toSA() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel serverBytes2StringChannel() {
        return new DirectChannel();
    }

    @Bean
    @Transformer(inputChannel = "serverBytes2StringChannel", outputChannel = "toSA")
    public ObjectToStringTransformer objectToStringTransformerServer() {
        return new ObjectToStringTransformer();
    }
}
