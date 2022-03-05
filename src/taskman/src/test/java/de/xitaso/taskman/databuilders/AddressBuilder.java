package de.xitaso.taskman.databuilders;

import de.xitaso.taskman.entities.Address;

public class AddressBuilder extends DataBuilder<Address, AddressBuilder> {

    public AddressBuilder inMunich() {
        return with(a -> a.setCity("Munich"));
    }

    @Override
    protected Address createInstance() {
        var address = new Address();
        address.setStreet("Some Street");
        address.setStreetNumber("5a");
        address.setZipCode("86167");
        address.setCity("Augsburg");
        return address;
    }
}
