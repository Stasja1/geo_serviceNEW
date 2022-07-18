package ru.netology;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;
import ru.netology.sender.MessageSender;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;

public class GeoServiceTest {

    @Test
    void messageForRussiaTest() {
        String expectedText = "Добро пожаловать";
        String ipAdr = "172.64.0.1";

        GeoService geoService = new GeoServiceImpl();
        LocalizationService localizationService = Mockito.mock(LocalizationServiceImpl.class);
        Mockito.when(localizationService.locale(Country.RUSSIA)).thenReturn("Добро пожаловать");

        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);

        Map<String, String> headers = new HashMap<String, String>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ipAdr);
        String actualText = messageSender.send(headers);
        Assertions.assertEquals(expectedText, actualText);
    }

    @Test
    void messageForUSATest() {
        String expectedText = "Welcome";
        String ipAdr = "96.0.0.0";

        GeoService geoService = new GeoServiceImpl();
        LocalizationService localizationService = Mockito.mock(LocalizationServiceImpl.class);
        Mockito.when(localizationService.locale(Country.USA)).thenReturn("Welcome");

        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);

        Map<String, String> headers = new HashMap<String, String>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ipAdr);
        String actualText = messageSender.send(headers);

        Assertions.assertEquals(expectedText, actualText);
    }
    @Test
    void locationRusByIpTest() {
        Location expectedLocation = new Location("Moscow", Country.RUSSIA, null, 0);
        String ipAdr = "172.89.1.5";
        GeoServiceImpl geoService = new GeoServiceImpl();
        Location actualLocation = geoService.byIp(ipAdr);
        Assertions.assertEquals(expectedLocation, actualLocation);
    }

    @Test
    void locationUsaByIpTest() {
        Location expectedLocation = new Location("Washington", Country.USA, null,  0);
        String ipAdr = "96.6.54.2";
        GeoServiceImpl geoService = new GeoServiceImpl();
        Location actualLocation = geoService.byIp(ipAdr);
        Assertions.assertEquals(expectedLocation, actualLocation);
    }

    @Test
    void localizationRusTest() {
        String expectedText = "Добро пожаловать";
        LocalizationServiceImpl localizationService = new LocalizationServiceImpl();
        String actualText = localizationService.locale(Country.RUSSIA);
        Assertions.assertEquals(expectedText, actualText);
    }

    @Test
    void localizationEngTest() {
        String expectedText = "Welcome";
        LocalizationServiceImpl localizationService = new LocalizationServiceImpl();
        String actualText = localizationService.locale(Country.USA);
        Assertions.assertEquals(expectedText, actualText);
    }


}