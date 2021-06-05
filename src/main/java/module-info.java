module passwalletservice {
    requires java.logging;
    requires jakarta.xml.bind;
    opens ro.eu.passwallet.service;
    opens ro.eu.passwallet.service.xml;
    opens ro.eu.passwallet.model;
    opens ro.eu.passwallet.model.dao;

    exports ro.eu.passwallet.model;
    exports ro.eu.passwallet.model.dao;
    exports ro.eu.passwallet.service;
    exports ro.eu.passwallet.service.xml;
}