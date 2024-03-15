package ru.tyaga.telegramBotForFun.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;
import ru.tyaga.telegramBotForFun.client.CBRClient;
import ru.tyaga.telegramBotForFun.exception.ServiceException;
import ru.tyaga.telegramBotForFun.service.ExchangeRatesService;

import javax.swing.text.Document;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.StringReader;

@Service
public class ExchangeRatesServiceImpl implements ExchangeRatesService {

    private static final String USD_PATH = "/ValCurs//Valute[@ID = 'R01235']/Value]";
    private static final String EURO_PATH = "/ValCurs//Valute[@ID = 'R01239']/Value]";



    @Autowired
    private CBRClient client;

    @Override
    public String getUSDExchangeRate() throws  ServiceException {
        var xml = client.getCurrencyRatesFXML();

        return extractCurrencyValueFromXML(xml,USD_PATH);
    }

    @Override
    public String getEUROxchangeRate() throws ServiceException {
        var xml = client.getCurrencyRatesFXML();

        return extractCurrencyValueFromXML(xml,EURO_PATH);
    }

    private static  String extractCurrencyValueFromXML(String xml, String xpathExpression) throws ServiceException {
        var source = new InputSource(new StringReader(xml));
        try
        {
            var xpath = XPathFactory.newInstance().newXPath();
            var document = (Document) xpath.evaluate("/", source, XPathConstants.NODE);
            return xpath.evaluate(xpathExpression,document);
        }
        catch (XPathExpressionException e)
        {
            throw new ServiceException("Не удалось распарсить FXML", e);
        }

    }
}
