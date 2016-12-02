package info.puton.product.smartsearch.exception;

import info.puton.product.common.exception.BaseException;

/**
 * Created by taoyang on 2016/12/1.
 */
public class FileException extends BaseException {

    public FileException(String message) {
        super(message);
    }

    public FileException(String message, Throwable cause) {
        super(message, cause);
    }
}
