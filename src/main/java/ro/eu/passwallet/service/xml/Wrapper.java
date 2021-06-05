package ro.eu.passwallet.service.xml;

import jakarta.xml.bind.annotation.XmlAnyElement;
import java.util.ArrayList;
import java.util.Collection;

public class Wrapper<T> {
    private Collection<T> items;

    public Wrapper() {
        items = new ArrayList<>();
    }

    public Wrapper(Collection<T> items) {
        this.items = items;
    }

    @XmlAnyElement(lax = true)
    public Collection<T> getItems() {
        return items;
    }
}
