package info.puton.product.smartsearch.service;

import info.puton.product.smartsearch.model.FileFullText;

import java.io.File;

/**
 * Created by taoyang on 2016/9/21.
 */
public interface FileExtractor {

    FileFullText extract(File file);

}
