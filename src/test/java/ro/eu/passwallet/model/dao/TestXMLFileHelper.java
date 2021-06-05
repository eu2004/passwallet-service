package ro.eu.passwallet.model.dao;

import ro.eu.passwallet.model.UserAccount;
import ro.eu.passwallet.service.LoggerService;
import ro.eu.passwallet.service.xml.XMLFileHelper;

import jakarta.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

//TODO migrate to Junit 5
public class TestXMLFileHelper extends CommonTest {
    private static final Logger logger = LoggerService.getInstance().getLogger();

    public static void main(String[] args) {
        try {
            XMLFileHelper<UserAccount> xmlFileHelper = new XMLFileHelper<>(UserAccount.class);
            testConversion(xmlFileHelper);
            testXMLFileOperations(xmlFileHelper);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void testXMLFileOperations(XMLFileHelper<UserAccount> xmlFileHelper) throws JAXBException, IOException {
        Collection<UserAccount> usersAccounts = new ArrayList<>();
        usersAccounts.add(createUserAccount(1));
        usersAccounts.add(createUserAccount(2));
        usersAccounts.add(createUserAccount(3));

        byte[] xmlContent = xmlFileHelper.convertObjectsToXML(usersAccounts, "USERSACCOUNTS");
        File xmlFile = new File("test_users_accounts.xml");
        xmlFile.deleteOnExit();
        xmlFileHelper.saveXMLFileContent(xmlContent, xmlFile.getAbsolutePath());

        xmlContent = xmlFileHelper.getXMLFileContent(xmlFile.getAbsolutePath());
        Collection<UserAccount> usersAccountsFromXml = xmlFileHelper.getAllObjectsFromXML(xmlContent);
        boolean equals = usersAccounts.size() == usersAccountsFromXml.size() && usersAccounts.equals(usersAccountsFromXml);
        if (!equals) {
            logger.info("Test testXMLFileOperations FAILED!");
        } else {
            logger.info("Test testXMLFileOperations OK!");
            logger.info(new String(xmlContent));
        }
    }

    public static void testConversion(XMLFileHelper<UserAccount> xmlFileHelper) throws JAXBException {
        Collection<UserAccount> usersAccounts = new ArrayList<>();
        usersAccounts.add(createUserAccount(1));
        usersAccounts.add(createUserAccount(2));
        usersAccounts.add(createUserAccount(3));

        byte[] xmlContent = xmlFileHelper.convertObjectsToXML(usersAccounts, "USERSACCOUNTS");
        Collection<UserAccount> usersAccountsFromXml = xmlFileHelper.getAllObjectsFromXML(xmlContent);
        boolean equals = usersAccounts.size() == usersAccountsFromXml.size() && usersAccounts.equals(usersAccountsFromXml);
        if (!equals) {
            logger.info("Test testConversion FAILED!");
        } else {
            logger.info("Test testConversion OK!");
        }
    }

}
