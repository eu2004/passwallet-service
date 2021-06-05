package ro.eu.passwallet.service.xml;

import java.util.Collection;

public interface IXMLFileService<T> {
    void saveToXMLFile(Collection<T> objects);

    void saveXMLFile(byte[] content);

    Collection<T> getAllObjectsFromXMLFile();
}
