package ro.eu.passwallet.service.xml;

import java.util.Collection;

public interface IXMLFileService<T> {
    public void saveToXMLFile(Collection<T> objects);

    public void saveXMLFile(byte[] content);

    public Collection<T> getAllObjectsFromXMLFile();
}
