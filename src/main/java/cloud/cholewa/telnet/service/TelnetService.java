package cloud.cholewa.telnet.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

@Service
@Slf4j
class TelnetService {

    @ServiceActivator(inputChannel = "toSA")
    public String test(String inputText) {
        System.out.println("");
        log.info("1 - New message: {}", inputText);

        int i = inputText.indexOf(":");
        String ble = inputText.substring(i + 2);

        log.info("2 - New message: {}", String.format("%040x", new BigInteger(1, ble.getBytes(StandardCharsets.UTF_8))));

        String wow = Hex.encodeHexString(ble.getBytes());
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < wow.length() / 2; j++) {
            sb.append(wow.charAt(j)).append(wow.charAt(j + 1)).append(", ");
        }

        log.info("3 - New message: {}", sb);
        return inputText;
    }
}
