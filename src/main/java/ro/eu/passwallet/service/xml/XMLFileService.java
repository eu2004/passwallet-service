package ro.eu.passwallet.service.xml;

import ro.eu.passwallet.service.crypt.CryptographyService;

import jakarta.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Collection;

public class XMLFileService<T> implements IXMLFileService<T> {
    private XMLFileHelper<T> xmlFileHelper;
    private CryptographyService cryptographyService;
    private String xmlFilePath;


    public XMLFileService(String password, String xmlFilePath, Class<T> objectClass) {
        try {
            xmlFileHelper = new XMLFileHelper<>(objectClass);
        } catch (JAXBException e) {
            throw new XMLFileServiceException(e);
        }
        this.xmlFilePath = xmlFilePath;
        cryptographyService = new CryptographyService(password);
    }

    public void saveToXMLFile(Collection<T> usersAccounts) {
        try {
            xmlFileHelper.saveXMLFileContent(cryptographyService.encrypt(xmlFileHelper.convertObjectsToXML(usersAccounts,
                    "USERSACCOUNTS")), xmlFilePath);
        } catch (IOException | JAXBException e) {
            throw new XMLFileServiceException(e);
        }
    }

    public void saveXMLFile(byte[] content) {
        try {
            xmlFileHelper.saveXMLFileContent(cryptographyService.encrypt(content), xmlFilePath);
        } catch (IOException e) {
            throw new XMLFileServiceException(e);
        }
    }

    private byte[] loadXMLFIle() throws IOException {
        return cryptographyService.decrypt(xmlFileHelper.getXMLFileContent(xmlFilePath));
    }

    public Collection<T> getAllObjectsFromXMLFile() {
        try {
            return xmlFileHelper.getAllObjectsFromXML(loadXMLFIle());
        } catch (JAXBException | IOException e) {
            throw new XMLFileServiceException(e);
        }
    }

}
