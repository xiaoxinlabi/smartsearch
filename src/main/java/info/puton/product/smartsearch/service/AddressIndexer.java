package info.puton.product.smartsearch.service;

import info.puton.product.smartsearch.model.Address;
import info.puton.product.smartsearch.model.FileFullText;

/**
 * Created by taoyang on 2016/9/21.
 */
public interface AddressIndexer {

    void initAddress();

    void addAddress(Address address);

}
