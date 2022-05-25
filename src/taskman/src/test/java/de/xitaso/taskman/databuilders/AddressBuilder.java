package de.xitaso.taskman.databuilders;

import de.xitaso.taskman.entities.Address;

public class AddressBuilder extends DataBuilder<Address, AddressBuilder> {

    public AddressBuilder inMunich() {
        return with(a -> a.setCity("Munich"));
    }

    @Override
    protected Address createInstance() {
        var address = new Address();
        // set Street
        address.setStreet("Some Street");
        // set Street Number
        address.setStreetNumber("5a");
        // set Zip Code
        address.setZipCode("86167");
        // set City
        address.setCity("Augsburg");
        return address;
    }
}
