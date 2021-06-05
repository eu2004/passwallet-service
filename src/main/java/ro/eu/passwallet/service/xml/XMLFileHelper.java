package ro.eu.passwallet.service.xml;

import jakarta.xml.bind.*;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.util.Collection;

public class XMLFileHelper<T> {

    protected JAXBContext jaxbContext;

    public XMLFileHelper(Class<T> classObject) throws JAXBException {
        jaxbContext = JAXBContext.newInstance(new Wrapper<T>().getClass(), classObject);
    }

    public byte[] getXMLFileContent(String xmlFilePath) throws IOException {
        ByteArrayOutputStream xmlFileOutput = new ByteArrayOutputStream();
        try (FileInputStream xmlFileInputStream = new FileInputStream(xmlFilePath)) {
            byte[] buffer = new byte[1024];
            int readBytes;
            while ((readBytes = xmlFileInputStream.read(buffer)) != -1) {
                xmlFileOutput.write(buffer, 0, readBytes);
            }
        }
        return xmlFileOutput.toByteArray();
    }

    public void saveXMLFileContent(byte[] content, String xmlFilePath) throws IOException {
        try (FileOutputStream xmlFileOut = new FileOutputStream(xmlFilePath)) {
            xmlFileOut.write(content);
            xmlFileOut.flush();
        }
    }

    public Collection<T> getAllObjectsFromXML(byte[] xmlFileContent) throws JAXBException {
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return unmarshal(unmarshaller, xmlFileContent);
    }

    public byte[] convertObjectsToXML(Collection<T> usersAccounts, String rootName) throws JAXBException {
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        ByteArrayOutputStream xmlOut = new ByteArrayOutputStream();
        marshal(marshaller, usersAccounts, rootName, xmlOut);
        return xmlOut.toByteArray();
    }

    /**
     * Unmarshal XML to Wrapper and return List value.
     */
    protected Collection<T> unmarshal(Unmarshaller unmarshaller, byte[] xmlFileContent) throws JAXBException {
        StreamSource xml = new StreamSource(new ByteArrayInputStream(xmlFileContent));
        Wrapper<T> wrapper = unmarshaller.unmarshal(xml,
                new Wrapper<T>().getClass()).getValue();
        return wrapper.getItems();
    }

    /**
     * Wrap List in Wrapper, then leverage JAXBElement to supply root element
     * information.
     */
    protected void marshal(Marshaller marshaller, Collection<T> collection, String name, ByteArrayOutputStream xmlOut)
            throws JAXBException {
        QName qName = new QName(name);
        Wrapper<T> wrapper = new Wrapper<>(collection);
        JAXBElement<Wrapper<T>> jaxbElement = new JAXBElement<>(qName,
                (Class<Wrapper<T>>) wrapper.getClass(), wrapper);
        StreamResult out = new StreamResult(xmlOut);
        marshaller.marshal(jaxbElement, out);
    }
}
